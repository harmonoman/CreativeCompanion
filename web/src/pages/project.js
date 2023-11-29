import CreativeCompanionClient from '../api/creativeCompanionClient';
import Header from '../components/header';
import BindingClass from '../util/bindingClass';
import DataStore from '../util/DataStore';

class Project extends BindingClass {
    constructor() {
        super();

        this.bindClassMethods(['mount', 'initDraggableElements', 'dropText', 'openModal', 'closeModal', 'performAction'], this);

        this.dataStore = new DataStore();
        this.header = new Header(this.dataStore);
        this.workspaceField = null; // Initialize Workspace Field

        console.log("project constructor");
    }

    /**
     * Add the header to the page and loads the CreativeCompanionClient.
     */
    mount() {
        this.header.addHeaderToPage();
        this.client = new CreativeCompanionClient();

        this.initDraggableElements(); // Initialize draggable elements
        window.dropText = this.dropText.bind(this);
        window.dropText = this.dropText;

        window.openModal = this.openModal.bind(this);
        window.closeModal = this.closeModal.bind(this);
        window.performAction = this.performAction.bind(this);
    }

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

    openModal() {
        const modal = document.getElementById('myModal');
        modal.style.display = 'block';
    }

    closeModal() {
        const modal = document.getElementById('myModal');
        modal.style.display = 'none';
    }

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

//                console.log(`Results for ${action}:`, data);

                // Populate Word Pool field with results
                this.populateWordPoolField(data);
            } catch (error) {
                console.error(`Error fetching data from Datamuse API for ${action}:`, error);
            } finally {
                this.closeModal();
        }
    }

    /**
     * Populates Word Pool field with results from a Datamuse query.
     * Makes each word 'draggable'
     * Adds a dragstart event listener for dragging text
     * Appends the text to Word Pool field.
     * @param results - Array of objects containing result data.
     */
    populateWordPoolField(results) {
        const wordPoolField = document.getElementById('wordPool-field');

        // Iterate through results and append to Word Pool field
        results.forEach(result => {
            const wordElement = document.createElement('p');
            wordElement.textContent = result.word;
            wordElement.draggable = true;

            // Dragstart event listener
            wordElement.addEventListener('dragstart', (event) => {
                event.dataTransfer.setData('text/plain', event.target.textContent);
            });

            // Append text to Word Pool
            wordPoolField.appendChild(wordElement);
        });
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




