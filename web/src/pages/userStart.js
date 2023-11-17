import CreativeCompanionClient from '../api/creativeCompanionClient';
import Header from '../components/header';
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";



const SEARCH_CRITERIA_KEY = 'search-criteria';
const SEARCH_RESULTS_KEY = 'search-results';
const EMPTY_DATASTORE_STATE = {
    [SEARCH_CRITERIA_KEY]: '',
    [SEARCH_RESULTS_KEY]: [],
};

class UserStart extends BindingClass {
    constructor() {
        super();

        this.bindClassMethods(['mount', 'clientLoaded', /*'displaySearchResults',*/ /*'getHTMLForSearchResults'*/], this);

        // Create a new datastore with an initial "empty" state.
        this.dataStore = new DataStore(EMPTY_DATASTORE_STATE);
        this.header = new Header(this.dataStore);
        this.dataStore.addChangeListener(this.displaySearchResults);
        console.log("userStart constructor");
    }


    mount() {
            // Wire up the form's 'submit' event and the button's 'click' event to the search method.
            // document.getElementById('create-digital-pantry-form').addEventListener('submit', this.search);

    //        document.getElementById('create-btn').addEventListener('click', this.search);
    //        document.getElementById('recipes-btn').addEventListener('click', this.search);
    //        document.getElementById('meal-plan-btn').addEventListener('click', this.search);


            this.header.addHeaderToPage();
            this.client = new UserStart();

            this.clientLoaded();
        }

        async clientLoaded() {
                const userName = await this.client.getUserName();

//                if (userName == undefined){
//                    document.getElementById("welcome-message").innerText = 'Welcome! Please sign in before continuing.';
//                } else {
//                    document.getElementById("welcome-message").innerText = 'Welcome, ' + userName + '!';
//                }

            }



        const main = async () => {
            const userStart = new UserStart();
            userStart.mount();
        };

        window.addEventListener('DOMContentLoaded', main);