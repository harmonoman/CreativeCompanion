import CreativeCompanionClient from '../api/creativeCompanionClient';
import Header from '../components/header';
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";

/**
 * Logic needed for the view word pool page of the website.
 */
class ViewWordPools extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['clientLoaded', 'mount', 'addWordPoolsToPage', 'redirectToViewWordPool', 'submit'], this);
        this.dataStore = new DataStore();
        this.dataStore.addChangeListener(this.addWordPoolsToPage);
        this.header = new Header(this.dataStore);
        console.log("viewWordPools constructor");
    }

    /**
     * Once the client is loaded, get the project metadata and project list.
     */
    async clientLoaded() {
        const wordPoolsSelect = document.getElementById('wordPoolsSelect');
            wordPoolsSelect.classList.add('shadow-wrapper');
            document.getElementById('wordPoolsSelect').innerText = "(Loading Word Pools...)";

        const wordPools = await this.client.getWordPoolList();
        console.log("(clientLoaded) wordPools should be here: " + wordPools);

        this.dataStore.set('wordPools', wordPools);
        console.log("(clientLoaded) wordPools have been set")

        const userName = await this.client.getUserName();
        const user = document.getElementById('user-name');
            user.classList.add('shadow-wrapper');
            document.getElementById('user-name').innerText = userName + "'s WordPools";

    }

    /**
     * Add the header to the page and load the creativeCompanionClient.
     */
    mount() {
        document.getElementById('wordPoolsSelect').addEventListener('click', this.submit);

        this.header.addHeaderToPage();

        this.client = new CreativeCompanionClient();
        this.clientLoaded();
    }

    /**
     * When the project is updated in the datastore, update the project metadata on the page.
     */
    addWordPoolsToPage() {

        const wordPools = this.dataStore.get('wordPools');

        if (!wordPools) {
            return;
        }

        const wordPoolsSelect = document.getElementById('wordPoolsSelect');
        wordPoolsSelect.size = wordPools.length;

        const optionList = wordPoolsSelect.options;

        // Clear existing options
        optionList.length = 0;

        wordPools.forEach(wordPool => {
            const option = new Option(wordPool.wordPoolName, wordPool.wordPoolId);

            // Add margin or padding to create spacing
            option.style.marginBottom = '20px';

            optionList.add(option);
        });
    }

    redirectToViewWordPool(wordPoolId) {
        console.log("(redirectToViewWordPool) here is the wordPoolId: " + wordPoolId)
        if (wordPoolId != null) {
            window.location.href = `/wordPool.html?wordPoolId=${wordPoolId}`;
        }
    }

    async submit(evt) {
        evt.preventDefault();

        const errorMessageDisplay = document.getElementById('error-message');
        errorMessageDisplay.innerText = ``;
        errorMessageDisplay.classList.add('hidden');

        const wordPoolSelect = document.getElementById('wordPoolsSelect');
        const wordPoolId = wordPoolSelect.value;

        // Check if the selected word pool is not empty
        if (wordPoolId) {
            console.log("(submit) selected word pool's id: " + wordPoolId);
            this.redirectToViewWordPool(wordPoolId);
        } else {
            // Handle the case where no word pool is selected (e.g., display an error message)
            errorMessageDisplay.innerText = "Please create a new word pool.";
            errorMessageDisplay.classList.remove('hidden');
        }
    }

}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const viewWordPools = new ViewWordPools();
    viewWordPools.mount();
};

window.addEventListener('DOMContentLoaded', main);
