import CreativeCompanionClient from '../api/creativeCompanionClient';
import Header from '../components/header';
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";
import './harmonograph'; // Importing the harmonograph code

/**
 * Logic needed for the home page of the website.
 */
class CreativeCompanionIndex extends BindingClass {
    constructor() {
        super();

        this.bindClassMethods(['mount', 'clientLoaded', 'openModal', 'closeModal', 'createProject'], this);

        this.dataStore = new DataStore();
        this.header = new Header(this.dataStore);
        console.log("creativeCompanionIndex constructor");

        window.openModal = this.openModal.bind(this);
        window.closeModal = this.closeModal.bind(this);
    }

    /**
     * Add the header to the page and loads the CreativeCompanionClient.
     */
    mount() {
        this.header.addHeaderToPage();
        this.client = new CreativeCompanionClient();

        this.clientLoaded();

        const createProjectBtn = document.getElementById('createProjectBtn');
        createProjectBtn.addEventListener('click', () => {this.createProject()});
    }

    async clientLoaded() {
        const userName = await this.client.getUserName();
        const buttonGroup = document.querySelector(".button-group");

        if (userName == undefined){
            document.getElementById("welcome-message").innerText = 'Welcome! Please sign in before continuing.';
            buttonGroup.style.display = 'none'; // Hide the button group
        } else {
            document.getElementById("welcome-message").innerText = 'Welcome, ' + userName + '!';
            buttonGroup.style.display = 'flex'; // Show the button group
        }
    }

    createLoginButton() {
            return this.createButton('Login', this.client.login);
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
     * Creates a new project using user-provided project name.
     * Retrieves the project name from the modal, validates it, and initiates the project creation process.
     * Upon successful project creation, navigates to the 'project.html' page, passing project details in the URL.
     * Finally, closes the modal.
     */
    async createProject() {

            // Get project name
            const projectNameInput = document.getElementById('projectNameInput');
            const projectName = projectNameInput.value.trim();
            console.log("projectName: " + projectName);

            // Check if element is found
            if (projectNameInput) {
                const projectName = projectNameInput.value.trim();
            } else {
                console.error("Element with ID 'projectNameInput' not found");
            }

            // Check if input is valid
            if (projectName === '') {
                alert('Please enter a valid project name.');
                return;
            }

            // Call createProject
            const project = await this.client.createProject(projectName);

            // Navigate to project.html with project ID
            window.location.href = `project.html?projectId=${project.projectId}`;


            // Close modal
            this.closeModal();
    }

}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const creativeCompanionIndex = new CreativeCompanionIndex();
    creativeCompanionIndex.mount();
};

window.addEventListener('DOMContentLoaded', main);