import CreativeCompanionClient from '../api/creativeCompanionClient';
import Header from '../components/header';
import BindingClass from '../util/bindingClass';
import DataStore from '../util/DataStore';
import LoadingSpinner from '../util/LoadingSpinner';

class WordPool extends BindingClass {
    constructor() {
            super();

            this.bindClassMethods(['clientLoaded', 'mount', 'openWordPoolModal', 'closeWordPoolModal', 'performAction',
                    'collectAndUpdateWordPool', 'addWordsToPage', 'clearWordPool', 'deleteWordPool',
                    'openImportWordPoolModal', 'closeImportWordPoolModal', 'importWordPool', 'changeWordPoolName',
                    'openChangeWordPoolNameModal', 'closeChangeWordPoolNameModal'], this);

            this.dataStore = new DataStore();
            this.dataStore.addChangeListener(this.addWordsToPage);

            this.header = new Header(this.dataStore);
            this.loadingSpinner = new LoadingSpinner();

            // Initialize variables to track the initial state
            this.initialWordPoolState = [];
    }

    /**
     * Once the client is loaded, get the project metadata
     */
    async clientLoaded() {

        // Message to LoadingSpinner
        const message = `Loading project... `;
        this.spinner.showLoadingSpinner(message);

        const urlParams = new URLSearchParams(window.location.search);
        const wordPoolId = urlParams.get('wordPoolId');

        const wordPoolNameElement = document.getElementById('wordPoolNameElement');
            wordPoolNameElement.classList.add('shadow-wrapper');
            document.getElementById('wordPoolNameElement').innerText = "Loading Word Pool ...";

        const wordPool = await this.client.getWordPool(wordPoolId);
        document.getElementById('wordPoolNameElement').innerText = wordPool.wordPoolName;

        this.dataStore.set('wordPool', wordPool);

        // Get Word Pool list for Import Word Pools Modal
        const wordPools = await this.client.getWordPoolList();
        this.dataStore.set('wordPools', wordPools);

        // Set the initial state when the client is loaded
        this.initialWordPoolState = this.getWordPoolState();

        this.spinner.hideLoadingSpinner();
    }

    /**
     * Add the header to the page and loads the CreativeCompanionClient.
     */
    mount() {
        this.header.addHeaderToPage();
        this.client = new CreativeCompanionClient();
        this.spinner = new LoadingSpinner();

        window.openWordPoolModal = this.openWordPoolModal.bind(this);
        window.closeWordPoolModal = this.closeWordPoolModal.bind(this);
        window.performAction = this.performAction.bind(this);

        window.openImportWordPoolModal = this.openImportWordPoolModal.bind(this);
        window.closeImportWordPoolModal = this.closeImportWordPoolModal.bind(this);
        window.importWordPool = this.importWordPool.bind(this);

        window.openChangeWordPoolNameModal = this.openChangeWordPoolNameModal.bind(this);
        window.closeChangeWordPoolNameModal = this.closeChangeWordPoolNameModal.bind(this);
        window.changeWordPoolName = this.changeWordPoolName.bind(this);

        // beforeunload Event Listener
        window.addEventListener('beforeunload', this.handleBeforeUnload.bind(this));

        // Save Word Pool button
        const saveWordPoolButton = document.getElementById('save-wordPool');
        saveWordPoolButton.addEventListener('click', this.collectAndUpdateWordPool);
        // Clear Word Pool button
        const clearWordPoolButton = document.getElementById('clear-wordPool');
        clearWordPoolButton.addEventListener('click', this.clearWordPool);
        // Delete Word Pool button
        const deleteWordPoolButton = document.getElementById('delete-wordPool');
        deleteWordPoolButton.addEventListener('click', () => {
        // Prompt user for confirmation before deleting the project
        const confirmation = confirm("Are you sure you want to delete this Word Pool?");
            if (confirmation) {
                this.deleteWordPool();
            }
            // If the user clicks "Cancel" in the confirmation, do nothing.
        });
        // Open Import Word Pool Modal button
        const importWordPoolButton = document.getElementById('import-wordPool');
        importWordPoolButton.addEventListener('click', this.openImportWordPoolModal);

        // Open Change Word Pool Name Modal button
        const changeWordPoolNameButton = document.getElementById('changeWordPoolNameBtn');
        changeWordPoolNameButton.addEventListener('click', this.changeWordPoolName);

        this.clientLoaded();
    }

    openWordPoolModal() {
        const modal = document.getElementById('myWordPoolModal');
        modal.style.display = 'block';
    }

    closeWordPoolModal() {
        const modal = document.getElementById('myWordPoolModal');
        modal.style.display = 'none';
    }

    ///// DATAMUSE API QUERY /////
    /**
     * Gets text values from specified input elements
     * Constructs a Datamuse API URL and fetches data.
     * Populates the Word Pool field with results.
     * @param action - action Id for type of Datamuse query
     * @param inputIds - Array containing user input.
     */
    async performAction(action, inputIds) {
        const inputTexts = inputIds.map((inputId) => {
            const inputElement = document.getElementById(inputId);
            return inputElement.value.trim();
        });

        // Check if there's at least one input
        if (inputTexts.some((text) => text === '')) {
            alert('Please enter text for the input(s).');
            return;
        }

        // Construct the API URL based on number of inputs
        let apiUrl;
        switch (action) {
            case 'Action 1':
                apiUrl = `https://api.datamuse.com/words?ml=${inputTexts[0]}`;
                break;
            case 'Action 2':
                apiUrl = `https://api.datamuse.com/words?ml=${inputTexts[0]}&sp=${inputTexts[1]}*`;
                break;
            case 'Action 3':
                apiUrl = `https://api.datamuse.com/words?ml=${inputTexts[0]}&sp=*${inputTexts[1]}`;
                break;
            case 'Action 4':
                apiUrl = `https://api.datamuse.com/words?sl=${inputTexts[0]}`;
                break;
            case 'Action 5':
                apiUrl = `https://api.datamuse.com/words?sp=${inputTexts[0]}??${inputTexts[1]}`;
                break;
            case 'Action 6':
                apiUrl = `https://api.datamuse.com/words?sp=${inputTexts[0]}`;
                break;
            case 'Action 7':
                apiUrl = `https://api.datamuse.com/words?rel_jjb=${inputTexts[0]}`;
                break;
            case 'Action 8':
                apiUrl = `https://api.datamuse.com/words?rel_jjb=${inputTexts[0]}&topics=${inputTexts[1]}`;
                break;
            case 'Action 9':
                apiUrl = `https://api.datamuse.com/words?rel_jja=${inputTexts[0]}`;
                break;
            case 'Action 10':
                apiUrl = `https://api.datamuse.com/words?lc=${inputTexts[0]}&sp=${inputTexts[1]}*`;
                break;
            case 'Action 11':
                apiUrl = `https://api.datamuse.com/words?rel_trg=${inputTexts[0]}`;
                break;
            case 'Action 12':
                apiUrl = `https://api.datamuse.com/sug?s=${inputTexts[0]}`;
                break;
        }

        try {
            const response = await fetch(apiUrl);
            const data = await response.json();

            // Populate Word Pool field with results
            this.populateWordPoolField(data);
        } catch (error) {
            console.error(`Error fetching data from Datamuse API for ${action}:`, error);
        } finally {
            this.closeWordPoolModal();
        }
    }

    ///// POPULATE WORD POOL /////
    /**
     * Populates Word Pool field with results from a Datamuse query.
     * Makes each word 'draggable'
     * Adds a dragstart event listener for dragging text
     * Appends the text to Word Pool field.
     * @param results - Array of objects containing result data.
     */
    populateWordPoolField(results) {
        const wordPoolField = document.getElementById('wordPool-field');
        //wordPoolField.innerHTML = ''; // Clear existing content

        // Iterate through results and append to Word Pool field
        if (Array.isArray(results)) {
            results.forEach(result => {
                const wordElement = document.createElement('p');
                let wordText;

                if (typeof result === 'object') {
                    // If result is an object, assume it has a "word" property
                    wordText = result.word;
                } else if (typeof result === 'string') {
                    // If result is a string, use it directly
                    wordText = result;
                } else {
                    console.error('Invalid result format. Expected an object or a string.');
                    return; // Skip to next iteration
                }

                wordElement.textContent = wordText;
                wordElement.draggable = true;

                // Dragstart event listener
                wordElement.addEventListener('dragstart', (event) => {
                    event.dataTransfer.setData('text/plain', wordText);
                });

                // Append text to Word Pool
                wordPoolField.appendChild(wordElement);
            });

        } else {
            console.error('Invalid results format. Expected an array.');
        }
    }

    clearWordPool() {
        const wordPoolField = document.getElementById('wordPool-field');
        wordPoolField.innerHTML = '';
    }

    ///// COLLECT and UPDATE WORD POOL /////
    /**
     * Collects data from fields and updates word pool.
     */
    async collectAndUpdateWordPool() {
        try {

            document.getElementById('save-wordPool').innerText = "Saving...";

            // Collect data from fields
            const wordPoolData = Array.from(document.getElementById('wordPool-field').children)
                .map(element => element.textContent.trim());

            // Get wordPoolId from URL
            const urlParams = new URLSearchParams(window.location.search);
            const wordPoolIdToUpdate = urlParams.get('wordPoolId');

            const wordPool = this.dataStore.get('wordPool');

            // Create the update data object
            const updateData = {
                wordPoolId: wordPoolIdToUpdate,
                wordPoolName: wordPool.wordPoolName,
                wordPool: wordPoolData,
            };

            // Update word pool
            const updatedWordPool = await this.client.updateWordPool(wordPoolIdToUpdate, updateData);

            // Update the initial state after saving
            this.initialWordPoolState = wordPoolData.slice();

            this.dataStore.set('wordPool', updatedWordPool);

        } catch (error) {
            console.error('Error collecting and updating word pool:', error);
        }

        document.getElementById('save-wordPool').innerText = "Save WordPool";

    }

    ///// addWordsToPage /////
    /**
     * When the project is updated in the datastore, repopulate the wordPool and workspace fields.
     */
    async addWordsToPage() {
        const wordPoolField = document.getElementById('wordPool-field');
        wordPoolField.innerHTML = '';

        const wordPool = await this.dataStore.get('wordPool');

        if (wordPool == null) {
            console.log("wordPool is null")
            return;
        }

        wordPool.wordPool.forEach(wordPoolText => {
            const wordElement = document.createElement('p');

            wordElement.textContent = wordPoolText;
            wordElement.draggable = true;

            // Dragstart event listener
            wordElement.addEventListener('dragstart', (event) => {
                event.dataTransfer.setData('text/plain', wordPoolText);
            });

            // Append text to Word Pool
            wordPoolField.appendChild(wordElement);
        });

        // Update initialWordPoolState after setting the word pool
        this.initialWordPoolState = wordPool.wordPool.slice();

        document.getElementById('wordPoolNameElement').innerText = wordPool.wordPoolName;
    }

    ///// DELETE WORD POOL /////
    /**
     * Deletes a word pool from the database.
     */
    async deleteWordPool() {
        const wordPool = this.dataStore.get('wordPool');

        // Message to LoadingSpinner
        const message = `Deleting ${wordPool.wordPoolName}. `;
        this.spinner.showLoadingSpinner(message);

        document.getElementById('wordPoolNameElement').innerText = "Deleting...";
        document.getElementById('delete-wordPool').innerText = "Deleting...";

        // Delete word pool
        const response = await this.client.deleteWordPool(wordPool.wordPoolId);

        if (response) {
            console.log(wordPool.wordPoolName + " has been deleted.");
        } else {
            console.error("Failed to delete: " + wordPool.wordPoolName);
        }

        this.spinner.hideLoadingSpinner();

        // Redirect to the wordPools page
        window.location.href = "/viewWordPools.html";
    }

    ///// IMPORT WORD POOL /////
    /**
     * Opens a modal and displays wordPools to be selected
     */
    openImportWordPoolModal() {
        const modal = document.getElementById('importModal');
        modal.style.display = 'block';

        // Get the select element by Id
        const wordPoolSelectModal = document.getElementById('wordPoolSelectModal');
        wordPoolSelectModal.innerHTML = '';

        // Get Word Pools from dataStore
        const userWordPools = this.dataStore.get('wordPools');

        // Populate the select element with user's Word Pools
        userWordPools.forEach(wordPool => {
            const option = document.createElement('option');
            option.value = wordPool.wordPoolName;
            option.textContent = wordPool.wordPoolName;
            wordPoolSelectModal.appendChild(option);
        });
    }

    closeImportWordPoolModal() {
        const modal = document.getElementById('importModal');
        modal.style.display = 'none';
    }

    /**
     * Imports selected wordPool into project wordPool
     */
    importWordPool() {
        const wordPoolField = document.getElementById('wordPool-field');
        const wordPoolSelectModal = document.getElementById('wordPoolSelectModal');
        // Get the selected Word Pool name
        const selectedWordPoolName = wordPoolSelectModal.value;

        // Get wordPools from dataStore
        const userWordPools = this.dataStore.get('wordPools');

        // Find the selected Word Pool object
        const selectedWordPool = userWordPools.find(wordPool => wordPool.wordPoolName === selectedWordPoolName);

        if (selectedWordPool == null) {
            console.log("selectedWordPool is null")
            return;
        }

        selectedWordPool.wordPool.forEach(wordPoolText => {
            const wordElement = document.createElement('p');

            wordElement.textContent = wordPoolText;
            wordElement.draggable = true;

            // Dragstart event listener
            wordElement.addEventListener('dragstart', (event) => {
                event.dataTransfer.setData('text/plain', wordPoolText);
            });

            // Append text to Word Pool
            wordPoolField.appendChild(wordElement);
        });

        // Update initialWordPoolState after importing the word pool
        this.initialWordPoolState = selectedWordPool.wordPool.slice();

        this.closeImportWordPoolModal();
    }

    openChangeWordPoolNameModal() {
        const wordPoolModal = document.getElementById('changeWordPoolNameModal');
        wordPoolModal.style.display = 'block';
        console.log("openChangeWordPoolNameModal");
    }

    closeChangeWordPoolNameModal() {
        const wordPoolModal = document.getElementById('changeWordPoolNameModal');
        wordPoolModal.style.display = 'none';
        console.log("closeChangeWordPoolNameModal");

    }

    ///// CHANGE WORD POOL NAME /////

    /**
     * Changes a wordPool name using user-provided wordPool name.
     * Retrieves the wordPool name from the modal, validates it, and initiates the name change process.
     * Upon successful name change, navigates to the 'wordPool.html' page, passing project details in the URL.
     * Finally, closes the modal.
     */
    async changeWordPoolName() {
        try {

            //Get current wordPool from dataStore
            const currentWordPool = await this.dataStore.get('wordPool');

            // Get existing wordPools from dataStore
            const wordPools = await this.dataStore.get('wordPools') || [];

            // Get new wordPool name input
            const changeWordPoolNameInput = document.getElementById('changeWordPoolNameInput');
            // Check if element is found
            if (!changeWordPoolNameInput) {
                console.error("Element with ID 'changeWordPoolNameInput' not found");
                return;
            }

            const wordPoolName = changeWordPoolNameInput.value.trim();

            // Check if input is valid
            if (wordPoolName === '') {
                window.alert('Please enter a valid word pool name.');
                return;
            }

            // Check if word pool already exists
            if (wordPools.some(wordPool => wordPool.wordPoolName === wordPoolName)) {
                window.alert(`A wordPool with the name "${wordPoolName}" already exists. Please choose a different name.`);
                return;
            }

            // Close modal
            this.closeChangeWordPoolNameModal();

            // Message to LoadingSpinner
            const message = `Changing ${currentWordPool.wordPoolName} to ${wordPoolName}. `;
            this.spinner.showLoadingSpinner(message);

            // Collect data from fields
            const wordPoolData = Array.from(document.getElementById('wordPool-field').children)
                .map(element => element.textContent.trim());

//            const workspaceData = Array.from(document.getElementById('workspace-field').children)
//                .map(element => element.textContent.trim());

            // Get wordPoolId from URL
            const urlParams = new URLSearchParams(window.location.search);
            const wordPoolIdToUpdate = urlParams.get('wordPoolId');

            // Create the update data object
            const updateData = {
                wordPoolId: wordPoolIdToUpdate,
                wordPoolName: wordPoolName,
                wordPool: wordPoolData,
//                workspace: workspaceData,
            };

            // Update wordPool
            const updatedWordPool = await this.client.updateWordPool(wordPoolIdToUpdate, updateData);

            this.dataStore.set('wordPool', updatedWordPool);

            // Find the wordPool that needs to be updated
            const wordPoolToUpdate = wordPools.find(wordPool => wordPool.wordPoolId === wordPoolIdToUpdate);

            // Update the wordPool name in the array
            if (wordPoolToUpdate) {
                wordPoolToUpdate.wordPoolName = wordPoolName;
            }

            // Set the updated array back to the dataStore
            await this.dataStore.set('wordPools', wordPools);

            } catch (error) {
                console.error('Error collecting and updating wordPool:', error);
            }

            this.spinner.hideLoadingSpinner();
        }

    ///// UNSAVED CHANGES /////
    handleBeforeUnload(event) {

        console.log('(handleBeforeUnload) Handling beforeunload event...');

        // Check if there are unsaved changes
        if (this.hasUnsavedChanges()) {
            // Display a confirmation message
            const confirmationMessage = "You have unsaved changes. Are you sure you want to leave?";

            if (event) {
                event.returnValue = confirmationMessage; // Standard for most browsers
            }

            return confirmationMessage; // For some older browsers
        }
    }

    // Helper function to get the current state of the wordPool
    getWordPoolState() {
        return Array.from(document.getElementById('wordPool-field').children)
                    .map(element => element.textContent.trim());
    }

    hasUnsavedChanges() {
        // Get the current state
        const currentWordPoolState = this.getWordPoolState();

        // Compare with the initial state
        const wordPoolChanged = !this.arraysEqual(currentWordPoolState, this.initialWordPoolState);

        // Return true if there are unsaved changes, false otherwise
        return wordPoolChanged;
    }

    // Helper function to compare arrays for equality
    arraysEqual(array1, array2) {
        if (array1.length !== array2.length) {
            return false;
        }

        for (let i = 0; i < array1.length; i++) {
            if (array1[i] !== array2[i]) {
                return false;
            }
        }

        return true;
    }

}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const wordPool = new WordPool();
    wordPool.mount();
};

window.addEventListener('DOMContentLoaded', main);


