import CreativeCompanionClient from '../api/creativeCompanionClient';
import Header from '../components/header';
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";
import LoadingSpinner from '../util/LoadingSpinner';

/**
 * Logic needed for the view word pool page of the website.
 */
class ViewWordPools extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['clientLoaded', 'mount', 'addWordPoolsToPage', 'redirectToViewWordPool', 'submit1', 'submit2'], this);
        this.dataStore = new DataStore();
        this.dataStore.addChangeListener(this.addWordPoolsToPage);
        this.header = new Header(this.dataStore);
    }

    /**
     * Once the client is loaded, get the project metadata and project list.
     */
    async clientLoaded() {
        const wordPoolsSelect = document.getElementById('wordPoolsSelect');
            wordPoolsSelect.classList.add('shadow-wrapper');
            document.getElementById('wordPoolsSelect').innerText = "(Loading Word Pools...)";

        const wordPools = await this.client.getWordPoolList();

        this.dataStore.set('wordPools', wordPools);

        const userName = await this.client.getUserName();
        const user = document.getElementById('user-name');
            user.classList.add('shadow-wrapper');
            document.getElementById('user-name').innerText = userName + "'s WordPools";

    }

    /**
     * Add the header to the page and load the creativeCompanionClient.
     */
    mount() {
        document.getElementById('wordPoolsSelect').addEventListener('click', this.submit1);
        document.getElementById('searchWordPoolBtn').addEventListener('click', this.submit2);
        // Add an event listener to the button for sorting
        document.getElementById('sortWordPoolsBtn').addEventListener('click', () => this.sortWordPoolsAlphabetically());
        document.getElementById('sortWordPoolsReverseBtn').addEventListener('click', () => this.sortWordPoolsReverseAlphabetically());

        this.header.addHeaderToPage();

        this.client = new CreativeCompanionClient();
        this.spinner = new LoadingSpinner();
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
            // Replace hyphens with spaces in the project name
            const wordPoolNameWithoutHyphens = wordPool.wordPoolName.replace(/_/g, ' ');

            const option = new Option(wordPoolNameWithoutHyphens, wordPool.wordPoolId);

            // Add margin or padding to create spacing
            option.style.marginBottom = '20px';

            optionList.add(option);
        });
    }

    redirectToViewWordPool(wordPoolId) {
        if (wordPoolId != null) {
            window.location.href = `/wordPool.html?wordPoolId=${wordPoolId}`;
        }
    }

    async submit1(evt) {
        evt.preventDefault();

        const errorMessageDisplay = document.getElementById('error-message');
        errorMessageDisplay.innerText = ``;
        errorMessageDisplay.classList.add('hidden');

        const wordPoolSelect = document.getElementById('wordPoolsSelect');
        const wordPoolId = wordPoolSelect.value;

        // Check if the selected word pool is not empty
        if (wordPoolId) {
            this.redirectToViewWordPool(wordPoolId);
        } else {
            // Handle the case where no word pool is selected (e.g., display an error message)
            errorMessageDisplay.innerText = "Please create a new word pool.";
            errorMessageDisplay.classList.remove('hidden');
        }
    }

    async submit2(evt) {
        evt.preventDefault();

        const errorMessageDisplay = document.getElementById('error-message');
        errorMessageDisplay.innerText = ``;
        errorMessageDisplay.classList.add('hidden');

        const wordPoolName = document.getElementById('wordPoolNameInput').value.trim();

        // Message to LoadingSpinner
        const message = `Searching for ${wordPoolName}... `;
        this.spinner.showLoadingSpinner(message);

        const wordPool = await this.client.getWordPoolByName(wordPoolName);

        if (wordPool && wordPool.wordPoolId != null) {
            this.redirectToViewWordPool(wordPool.wordPoolId);
        } else {
            // Handle the case where no word pool is selected (e.g., display an error message)
            errorMessageDisplay.innerText = "Please create a new word pool.";
            errorMessageDisplay.classList.remove('hidden');
        }

        this.spinner.hideLoadingSpinner();

    }

    sortWordPoolsAlphabetically() {
        const wordPools = this.dataStore.get('wordPools');

        if (!wordPools) {
            return;
        }

        // Sort projects alphabetically by projectName
        wordPools.sort((a, b) => a.wordPoolName.localeCompare(b.wordPoolName));

        // Update the project metadata on the page
        this.addWordPoolsToPage();
    }

    sortWordPoolsReverseAlphabetically() {
        const wordPools = this.dataStore.get('wordPools');

        if (!wordPools) {
            return;
        }

        // Sort projects alphabetically by projectName
        wordPools.sort((a, b) => b.wordPoolName.localeCompare(a.wordPoolName));

        // Update the project metadata on the page
        this.addWordPoolsToPage();
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
