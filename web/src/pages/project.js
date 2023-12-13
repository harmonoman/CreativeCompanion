import CreativeCompanionClient from '../api/creativeCompanionClient';
import Header from '../components/header';
import BindingClass from '../util/bindingClass';
import DataStore from '../util/DataStore';
import LoadingSpinner from '../util/LoadingSpinner';

class Project extends BindingClass {
    constructor() {
        super();

        this.bindClassMethods(['clientLoaded', 'mount', 'initDraggableElements', 'dropText', 'openProjectModal',
        'closeProjectModal', 'performAction','collectAndUpdateProject', 'addWordsToPage', 'clearWordPool',
        'clearWorkspace','deleteProject', 'openImportWordPoolModal', 'closeImportWordPoolModal', 'importWordPool'], this);

        this.workspaceField = null; // Initialize Workspace Field

        this.dataStore = new DataStore();
        this.dataStore.addChangeListener(this.addWordsToPage);

        this.header = new Header(this.dataStore);
        this.loadingSpinner = new LoadingSpinner();

        console.log("Project constructor");
    }

    /**
     * Once the client is loaded, get the project metadata
     */
    async clientLoaded() {
        const urlParams = new URLSearchParams(window.location.search);
        const projectId = urlParams.get('projectId');

        const projectNameElement = document.getElementById('projectNameElement');
        projectNameElement.classList.add('shadow-wrapper');
        document.getElementById('projectNameElement').innerText = "Loading Project ...";
        const project = await this.client.getProject(projectId);
        console.log("inside clientLoaded");

        console.log("project: " + project.projectId);
        this.dataStore.set('project', project);
        document.getElementById('projectNameElement').innerText = project.projectName;

        // Get Word Pool list for Import Word Pools Modal
        const wordPools = await this.client.getWordPoolList();
        this.dataStore.set('wordPools', wordPools);
    }

    /**
     * Add the header to the page and loads the CreativeCompanionClient.
     */
    mount() {
        this.header.addHeaderToPage();
        this.client = new CreativeCompanionClient();

        // Initialize draggable elements
        this.initDraggableElements();
        window.dropText = this.dropText.bind(this);
        window.dropText = this.dropText;

        window.openProjectModal = this.openProjectModal.bind(this);
        window.closeProjectModal = this.closeProjectModal.bind(this);
        window.performAction = this.performAction.bind(this);

        window.openImportWordPoolModal = this.openImportWordPoolModal.bind(this);
        window.closeImportWordPoolModal = this.closeImportWordPoolModal.bind(this);
        window.importWordPool = this.importWordPool.bind(this);

        // Save Project button
        const saveProjectButton = document.getElementById('save-project');
        saveProjectButton.addEventListener('click', this.collectAndUpdateProject);
        // Clear Word Pool button
        const clearWordPoolButton = document.getElementById('clear-wordPool');
        clearWordPoolButton.addEventListener('click', this.clearWordPool);
        // Clear Workspace button
        const clearWorkspaceButton = document.getElementById('clear-workspace');
        clearWorkspaceButton.addEventListener('click', this.clearWorkspace);
        // Delete Project button
        const deleteProjectButton = document.getElementById('delete-project');
        deleteProjectButton.addEventListener('click', this.deleteProject);
        // Open Import Word Pool Modal button
        const importWordPoolButton = document.getElementById('import-wordPool');
        importWordPoolButton.addEventListener('click', this.openImportWordPoolModal);

        this.clientLoaded();
    }


    ///// initDraggableElements /////
    /**
    * Initialize draggable elements and event listeners.
    */
    initDraggableElements() {
       const wordPoolField = document.getElementById('wordPool-field');
       this.workspaceField = document.getElementById('workspace-field');

       // Add event listeners to all draggable elements
       const draggableElements = document.querySelectorAll('.draggable');
       draggableElements.forEach(element => {
           element.addEventListener('dragstart', (event) => {
               event.dataTransfer.setData('text/plain', event.target.textContent);
           });
       });
    }


    ///// DROP TEXT /////
    /**
     * Event handler for dropping text.
     * @param event - Drop event object.
     */
     dropText(event) {
        event.preventDefault();
        const text = event.dataTransfer.getData('text/plain');
        const paragraph = document.createElement('p');
        paragraph.textContent = text;

        // Make the paragraph draggable in workspace field
        paragraph.draggable = true;

        // Add a listener for the dragover event to allow dropping
        paragraph.addEventListener('dragover', (e) => e.preventDefault());

        // Add listener for drop event to handle moving paragraph
        paragraph.addEventListener('drop', (e) => {
            e.preventDefault();
            // Get current paragraph being dragged
            const draggedParagraph = e.dataTransfer.getData('text/plain');
            // Insert paragraph before current paragraph
            this.workspaceField.insertBefore(paragraph, e.target);
        });

        // Append paragraph to workspace field
        this.workspaceField.appendChild(paragraph);

    }

    openProjectModal() {
        const modal = document.getElementById('myProjectModal');
        modal.style.display = 'block';
    }

    closeProjectModal() {
        const modal = document.getElementById('myProjectModal');
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
        if (inputTexts.length === 1) {
            apiUrl = `https://api.datamuse.com/words?ml=${inputTexts[0]}`;
        } else if (inputTexts.length === 2) {
            apiUrl = `https://api.datamuse.com/words?ml=${inputTexts[0]}&sp=${inputTexts[1]}*`;
        }

        try {
            const response = await fetch(apiUrl);
            const data = await response.json();

            // Populate Word Pool field with results
            this.populateWordPoolField(data);
        } catch (error) {
            console.error(`Error fetching data from Datamuse API for ${action}:`, error);
        } finally {
            this.closeProjectModal();
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


    ///// POPULATE WORKSPACE /////
    /**
     * Populates Word Pool field with results from a Datamuse query.
     * Makes each word 'draggable'
     * Adds a dragstart event listener for dragging text
     * Appends the text to Word Pool field.
     * @param results - Array of objects containing result data.
     */
    populateWorkspaceField(results) {
        const workspaceField = document.getElementById('workspace-field');
        //workspaceField.innerHTML = ''; // Clear existing content

        // Iterate through results and append to Workspace field
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
                    return; // Skip to the next iteration
                }

                wordElement.textContent = wordText;
                wordElement.draggable = true;

                // Dragstart event listener
                wordElement.addEventListener('dragstart', (event) => {
                    event.dataTransfer.setData('text/plain', wordText);
                });

                // Append text to Word Pool
                workspaceField.appendChild(wordElement);
            });

        } else {
            console.error('Invalid results format. Expected an array.');
        }
    }

    clearWordPool() {
        const wordPoolField = document.getElementById('wordPool-field');
        wordPoolField.innerHTML = '';
    }

    clearWorkspace() {
        const workspaceField = document.getElementById('workspace-field');
        workspaceField.innerHTML = '';
    }

    ///// COLLECT and UPDATE PROJECT /////
    /**
     * Collects data from fields and updates project.
     */
    async collectAndUpdateProject() {
        try {
            // Collect data from fields
            const wordPoolData = Array.from(document.getElementById('wordPool-field').children)
                .map(element => element.textContent.trim());

            const workspaceData = Array.from(document.getElementById('workspace-field').children)
                .map(element => element.textContent.trim());

            // Get projectId from URL
            const urlParams = new URLSearchParams(window.location.search);
            const projectIdToUpdate = urlParams.get('projectId');

            const project = this.dataStore.get('project');

            // Create the update data object
            const updateData = {
                projectId: projectIdToUpdate,
                projectName: project.projectName,
                wordPool: wordPoolData,
                workspace: workspaceData,
            };

            // Update project
            const updatedProject = await this.client.updateProject(projectIdToUpdate, updateData);

            this.dataStore.set('project', updatedProject);

        } catch (error) {
            console.error('Error collecting and updating project:', error);
        }
    }


    ///// addWordsToPage /////
    /**
     * When the project is updated in the datastore, repopulate the wordPool and workspace fields.
     */
    addWordsToPage() {
        const wordPoolField = document.getElementById('wordPool-field');
        wordPoolField.innerHTML = '';
        const workspaceField = document.getElementById('workspace-field');
        workspaceField.innerHTML = '';
        const project = this.dataStore.get('project');

        if (project == null) {
            console.log("project is null")
            return;
        }

        project.wordPool.forEach(wordPoolText => {
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

        project.workspace.forEach(workspaceText => {
            const wordElement = document.createElement('p');

            wordElement.textContent = workspaceText;
            wordElement.draggable = true;

            // Dragstart event listener
            wordElement.addEventListener('dragstart', (event) => {
                event.dataTransfer.setData('text/plain', workspaceText);
            });

            // Append text to Word Pool
            workspaceField.appendChild(wordElement);
        });

        document.getElementById('projectNameElement').innerText = project.projectName;

    }

    ///// DELETE PROJECT /////
    /**
     * Deletes a project from the database.
     */
    async deleteProject() {
//        const message = "Deleting Project";
//        this.showLoadingSpinner(message);
        const project = this.dataStore.get('project');

        document.getElementById('projectNameElement').innerText = "Deleting...";
        document.getElementById('delete-project').innerText = "Deleting...";

        // Delete project
        const response = this.client.deleteProject(project.projectId);

        if (response) {
            console.log(project.projectName + " has been deleted.");
            // Redirect to the projects page

            setTimeout(() => {
            window.location.href = "/viewProjects.html";
//            this.hideLoadingSpinner();
            }, 2500);

        } else {
            console.error("Failed to delete: " + project.projectName);
        }


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

        this.closeImportWordPoolModal();
    }





}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const project = new Project();
    project.mount();
};

window.addEventListener('DOMContentLoaded', main);




