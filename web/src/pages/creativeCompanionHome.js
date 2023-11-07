import CreativeCompanionClient from '../api/creativeCompanionClient';
import Header from '../components/header';
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";

/*
The code below this comment is equivalent to...
const EMPTY_DATASTORE_STATE = {
    'search-criteria': '',
    'search-results': [],
};

...but uses the "KEY" constants instead of "magic strings".
The "KEY" constants will be reused a few times below.
*/

const SEARCH_CRITERIA_KEY = 'search-criteria';
const SEARCH_RESULTS_KEY = 'search-results';
const EMPTY_DATASTORE_STATE = {
    [SEARCH_CRITERIA_KEY]: '',
    [SEARCH_RESULTS_KEY]: [],
};


/**
 * Logic needed for the search playlist page of the website.
 */
class CreativeCompanionHome extends BindingClass {
    constructor() {
        super();

        this.bindClassMethods(['mount', 'clientLoaded', 'displaySearchResults', 'getHTMLForSearchResults'], this);

        // Create a enw datastore with an initial "empty" state.
        this.dataStore = new DataStore(EMPTY_DATASTORE_STATE);
        this.header = new Header(this.dataStore);
        this.dataStore.addChangeListener(this.displaySearchResults);
        console.log("creativeCompanionHome constructor");
    }

    /**
     * Add the header to the page and loads the CreativeCompanionClient.
     */
    mount() {
        // Wire up the form's 'submit' event and the button's 'click' event to the search method.
        // document.getElementById('create-digital-pantry-form').addEventListener('submit', this.search);

//        document.getElementById('create-btn').addEventListener('click', this.search);
//        document.getElementById('recipes-btn').addEventListener('click', this.search);
//        document.getElementById('meal-plan-btn').addEventListener('click', this.search);


        this.header.addHeaderToPage();
        this.client = new CreativeCompanionClient();

        this.clientLoaded();
    }

    async clientLoaded() {
        const userName = await this.client.getUserName();

        if (userName == undefined){
            document.getElementById("home-page").innerText = 'Welcome! Please sign in before continuing.';
        } else {
            document.getElementById("home-page").innerText = 'Welcome, ' + userName + '!';
        }

    }

    /**
     * Pulls search results from the datastore and displays them on the html page.
     */
    displaySearchResults() {
        const searchCriteria = this.dataStore.get(SEARCH_CRITERIA_KEY);
        const searchResults = this.dataStore.get(SEARCH_RESULTS_KEY);

        const searchResultsContainer = document.getElementById('search-results-container');
        const searchCriteriaDisplay = document.getElementById('search-criteria-display');
        const searchResultsDisplay = document.getElementById('search-results-display');

        if (searchCriteria === '') {
            searchResultsContainer.classList.add('hidden');
            searchCriteriaDisplay.innerHTML = '';
            searchResultsDisplay.innerHTML = '';
        } else {
            searchResultsContainer.classList.remove('hidden');
            searchCriteriaDisplay.innerHTML = `"${searchCriteria}"`;
            searchResultsDisplay.innerHTML = this.getHTMLForSearchResults(searchResults);
        }


    }
//      DO I NEED THIS FOR THIS PAGE? I PROBABLY DO NEED TO COME BACK TO THIS TO FOR MY GetProjectList vertical
//    /**
//     * Create appropriate HTML for displaying searchResults on the page.
//     * @param searchResults An array of playlists objects to be displayed on the page.
//     * @returns A string of HTML suitable for being dropped on the page.
//     */
//    getHTMLForSearchResults(searchResults) {
//        if (searchResults.length === 0) {
//            return '<h4>No results found</h4>';
//        }
//
//        let html = '<table><tr><th>Name</th><th>Song Count</th><th>Tags</th></tr>';
//        for (const res of searchResults) {
//            html += `
//            <tr>
//                <td>
//                    <a href="playlist.html?id=${res.id}">${res.name}</a>
//                </td>
//                <td>${res.songCount}</td>
//                <td>${res.tags?.join(', ')}</td>
//            </tr>`;
//        }
//        html += '</table>';
//
//        return html;
//    }

}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const creativeCompanionHome = new CreativeCompanionHome();
    creativeCompanionHome.mount();
};

window.addEventListener('DOMContentLoaded', main);
