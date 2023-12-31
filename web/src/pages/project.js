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
        'clearWorkspace','deleteProject', 'openImportWordPoolModal', 'closeImportWordPoolModal', 'importWordPool',
        'changeProjectName', 'openChangeProjectNameModal', 'closeChangeProjectNameModal'], this);

        // Initialize Workspace Field
        this.workspaceField = null;

        this.dataStore = new DataStore();
        this.dataStore.addChangeListener(this.addWordsToPage);

        this.header = new Header(this.dataStore);

        // Initialize variables to track the initial state
        this.initialWordPoolState = [];
        this.initialWorkspaceState = [];
    }

    /**
     * Once the client is loaded, get the project metadata
     */
    async clientLoaded() {

        // Message to LoadingSpinner
        const message = `Loading project... `;
        this.spinner.showLoadingSpinner(message);

        const urlParams = new URLSearchParams(window.location.search);
        const projectId = urlParams.get('projectId');

        const projectNameElement = document.getElementById('projectNameElement');
        projectNameElement.classList.add('shadow-wrapper');
        document.getElementById('projectNameElement').innerText = "- - -";

        // Get Project
        const project = await this.client.getProject(projectId);
        this.dataStore.set('project', project);
        document.getElementById('projectNameElement').innerText = project.projectName;
        projectNameElement.innerText = project.projectName.replace(/_/g, ' '); // Replace hyphens with spaces


        // Get Word Pool list for Import Word Pools Modal
        const wordPools = await this.client.getWordPoolList();
        this.dataStore.set('wordPools', wordPools);

        // Get Project list for Change Project Name Modal
        const projects = await this.client.getProjectList();
        this.dataStore.set('projects', projects);

        // Set the initial state when the client is loaded
        this.initialWordPoolState = this.getWordPoolState();
        this.initialWorkspaceState = this.getWorkspaceState();

        this.spinner.hideLoadingSpinner();
    }

    /**
     * Add the header to the page and loads the CreativeCompanionClient.
     */
    mount() {
        this.header.addHeaderToPage();
        this.client = new CreativeCompanionClient();
        this.spinner = new LoadingSpinner();

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

        window.openChangeProjectNameModal = this.openChangeProjectNameModal.bind(this);
        window.closeChangeProjectNameModal = this.closeChangeProjectNameModal.bind(this);
        window.changeProjectName = this.changeProjectName.bind(this);

        // beforeunload Event Listener
        window.addEventListener('beforeunload', this.handleBeforeUnload.bind(this));

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
        deleteProjectButton.addEventListener('click', () => {
            // Prompt user for confirmation before deleting the project
            const confirmation = confirm("Are you sure you want to delete this Project?");
            if (confirmation) {
                this.deleteProject();
            }
            // If the user clicks "Cancel" in the confirmation, do nothing.
        });
        // Open Import Word Pool Modal button
        const importWordPoolButton = document.getElementById('import-wordPool');
        importWordPoolButton.addEventListener('click', this.openImportWordPoolModal);
        // Open Change Project Name Modal button
        const changeProjectNameButton = document.getElementById('changeProjectNameBtn');
        changeProjectNameButton.addEventListener('click', this.changeProjectName);

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
            document.getElementById('save-project').innerText = "Saving...";

            // Collect data from fields
            const wordPoolData = Array.from(document.getElementById('wordPool-field').children)
                .map(element => element.textContent.trim());

            const workspaceData = Array.from(document.getElementById('workspace-field').children)
                .map(element => element.textContent.trim());

            // Get projectId from URL
            const urlParams = new URLSearchParams(window.location.search);
            const projectIdToUpdate = urlParams.get('projectId');

            const project = this.dataStore.get('project');
            const projectNameWithoutHyphens = project.projectName.replace(/_/g, ' ');

            // Message to LoadingSpinner
            const message = `Saving ${projectNameWithoutHyphens}... `;
            this.spinner.showLoadingSpinner(message);

            // Create the update data object
            const updateData = {
                projectId: projectIdToUpdate,
                projectName: project.projectName,
                wordPool: wordPoolData,
                workspace: workspaceData,
            };

            // Update project
            const updatedProject = await this.client.updateProject(projectIdToUpdate, updateData);

            // Update the initial state after saving
            this.initialWordPoolState = wordPoolData.slice();
            this.initialWorkspaceState = workspaceData.slice();

            this.dataStore.set('project', updatedProject);

        } catch (error) {
            console.error('Error collecting and updating project:', error);
        }

        this.spinner.hideLoadingSpinner();

        document.getElementById('save-project').innerText = "Save Project";

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

        const projectNameElement = document.getElementById('projectNameElement');
        projectNameElement.innerText = project.projectName.replace(/_/g, ' ');
    }

    ///// DELETE PROJECT /////
    /**
     * Deletes a project from the database.
     */
    async deleteProject() {

        const project = this.dataStore.get('project');
        const projectNameWithoutHyphens = project.projectName.replace(/_/g, ' ');

        // Message to LoadingSpinner
        const message = `Deleting ${projectNameWithoutHyphens}... `;
        this.spinner.showLoadingSpinner(message);

        document.getElementById('projectNameElement').innerText = "Deleting...";
        document.getElementById('delete-project').innerText = "Deleting...";

        // Delete project
        const response = await this.client.deleteProject(project.projectId);

        if (response) {
            console.log(project.projectName + " has been deleted.");
        } else {
            console.error("Failed to delete: " + project.projectName);
        }

        this.spinner.hideLoadingSpinner();

        // Redirect to the projects page
        window.location.href = "/viewProjects.html";
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

    openChangeProjectNameModal() {
        const projectModal = document.getElementById('changeProjectNameModal');
        projectModal.style.display = 'block';
    }

    closeChangeProjectNameModal() {
        const projectModal = document.getElementById('changeProjectNameModal');
        projectModal.style.display = 'none';
    }

    ///// CHANGE PROJECT NAME /////

    /**
     * Changes a project name using user-provided project name.
     * Retrieves the project name from the modal, validates it, and initiates the name change process.
     * Upon successful name change, navigates to the 'project.html' page, passing project details in the URL.
     * Finally, closes the modal.
     */
    async changeProjectName() {
        try {

            //Get current project from dataStore
            const currentProject = await this.dataStore.get('project');

            // Get existing projects from dataStore
            const projects = await this.dataStore.get('projects') || [];

            // Get new project name input
            const changeProjectNameInput = document.getElementById('changeProjectNameInput');
            // Check if element is found
            if (!changeProjectNameInput) {
                console.error("Element with ID 'changeProjectNameInput' not found");
                return;
            }

            const projectName = changeProjectNameInput.value.trim();

            // Replace spaces with underscores in the project name
            const projectNameWithUnderscores = projectName.replace(/ /g, '_');

            // Check if input is valid
            if (projectNameWithUnderscores === '') {
                window.alert('Please enter a valid project name.');
                return;
            }

            // Check if project already exists
            if (projects.some(project => project.projectName === projectNameWithUnderscores)) {
                window.alert(`A project with the name "${projectName}" already exists. Please choose a different name.`);
                return;
            }

            // Close modal
            this.closeChangeProjectNameModal();

            const currentProjectWithUnderscores = currentProject.projectName;
            const currentProjectWithNoUnderscores = currentProjectWithUnderscores.replace(/_/g, ' ');
            const projectNameWithNoUnderscores = projectNameWithUnderscores.replace(/_/g, ' ');
            // Message to LoadingSpinner
            const message = `Changing ${currentProjectWithNoUnderscores} to ${projectNameWithNoUnderscores}... `;
            this.spinner.showLoadingSpinner(message);

            // Collect data from fields
            const wordPoolData = Array.from(document.getElementById('wordPool-field').children)
                .map(element => element.textContent.trim());

            const workspaceData = Array.from(document.getElementById('workspace-field').children)
                .map(element => element.textContent.trim());

            // Get projectId from URL
            const urlParams = new URLSearchParams(window.location.search);
            const projectIdToUpdate = urlParams.get('projectId');

            // Create the update data object
            const updateData = {
                projectId: projectIdToUpdate,
                projectName: projectNameWithUnderscores,
                wordPool: wordPoolData,
                workspace: workspaceData,
            };

            // Update project
            const updatedProject = await this.client.updateProject(projectIdToUpdate, updateData);

            this.dataStore.set('project', updatedProject);

            // Find the project that needs to be updated
            const projectToUpdate = projects.find(project => project.projectId === projectIdToUpdate);

            // Update the project name in the array
            if (projectToUpdate) {
                projectToUpdate.projectName = projectName;
            }

            // Set the updated array back to the dataStore
            await this.dataStore.set('projects', projects);

            } catch (error) {
                console.error('Error collecting and updating project:', error);
            }

            this.spinner.hideLoadingSpinner();
        }


    ///// UNSAVED CHANGES /////
    handleBeforeUnload(event) {
        // Check if there are unsaved changes
        if (this.hasUnsavedChanges()) {
            // Display a confirmation message
            const confirmationMessage = "You have unsaved changes. Are you sure you want to leave?";
            event.returnValue = confirmationMessage; // Standard for most browsers
            return confirmationMessage; // For some older browsers
        }
    }

    // Helper function to get the current state of the wordPool
    getWordPoolState() {
        return Array.from(document.getElementById('wordPool-field').children)
            .map(element => element.textContent.trim());
    }

    // Helper function to get the current state of the workspace
    getWorkspaceState() {
        return Array.from(document.getElementById('workspace-field').children)
            .map(element => element.textContent.trim());
    }

    hasUnsavedChanges() {
        // Get the current state
        const currentWordPoolState = this.getWordPoolState();
        const currentWorkspaceState = this.getWorkspaceState();

        // Compare with the initial state
        const wordPoolChanged = !this.arraysEqual(currentWordPoolState, this.initialWordPoolState);
        const workspaceChanged = !this.arraysEqual(currentWorkspaceState, this.initialWorkspaceState);

        // Return true if there are unsaved changes, false otherwise
        return wordPoolChanged || workspaceChanged;
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
    const project = new Project();
    project.mount();
};

window.addEventListener('DOMContentLoaded', main);




