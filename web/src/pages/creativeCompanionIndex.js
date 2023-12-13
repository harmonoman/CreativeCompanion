import CreativeCompanionClient from '../api/creativeCompanionClient';
import Header from '../components/header';
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";
import './harmonograph'; // Importing the harmonograph code
import LoadingSpinner from '../util/LoadingSpinner';

/**
 * Logic needed for the home page of the website.
 */
class CreativeCompanionIndex extends BindingClass {
    constructor() {
        super();

        this.bindClassMethods(['mount', 'clientLoaded', 'openProjectModal', 'closeProjectModal', 'createProject',
        'openWordPoolModal', 'closeWordPoolModal', 'createWordPool'], this);

        this.dataStore = new DataStore();
        this.header = new Header(this.dataStore);
        this.loadingSpinner = new LoadingSpinner();

        window.openProjectModal = this.openProjectModal.bind(this);
        window.closeProjectModal = this.closeProjectModal.bind(this);
        window.openWordPoolModal = this.openWordPoolModal.bind(this);
        window.closeWordPoolModal = this.closeWordPoolModal.bind(this);

        console.log("creativeCompanionIndex constructor");
    }

    /**
     * Add the header to the page and loads the CreativeCompanionClient.
     */
    mount() {
        this.header.addHeaderToPage();
        this.client = new CreativeCompanionClient();
        this.spinner = new LoadingSpinner();

        this.clientLoaded();

        const createProjectBtn = document.getElementById('createProjectBtn');
        createProjectBtn.addEventListener('click', () => {this.createProject()});

        const createWordPoolBtn = document.getElementById('createWordPoolBtn');
        createWordPoolBtn.addEventListener('click', () => {this.createWordPool()});
    }

    async clientLoaded() {
        const userName = await this.client.getUserName();
        const welcomeMessage = document.getElementById("welcome-message");
        const buttonGroup = document.querySelector(".button-group");

        if (userName == undefined){
            document.getElementById("welcome-message").innerText = 'Welcome! Please sign in before continuing.';
            buttonGroup.style.display = 'none'; // Hide the button group
        } else {
            document.getElementById("welcome-message").innerText = 'Welcome, ' + userName + '!';
            buttonGroup.style.display = 'flex'; // Show the button group
        }

        // Always add the shadow-wrapper class
        welcomeMessage.classList.add('shadow-wrapper');

        // Populate dataStore with existing projects
        const projects = await this.client.getProjectList();
        this.dataStore.set('projects', projects);

        // Populate dataStore with existing projects
        const wordPools = await this.client.getWordPoolList();
        this.dataStore.set('wordPools', wordPools);
    }

    createLoginButton() {
            return this.createButton('Login', this.client.login);
        }

    ///// PROJECT MODAL /////

    openProjectModal() {
            const projectModal = document.getElementById('projectModal');
            projectModal.style.display = 'block';
        }

    closeProjectModal() {
        const projectModal = document.getElementById('projectModal');
        projectModal.style.display = 'none';
    }

    ///// CREATE PROJECT /////

    /**
     * Creates a new project using user-provided project name.
     * Retrieves the project name from the modal, validates it, and initiates the project creation process.
     * Upon successful project creation, navigates to the 'project.html' page, passing project details in the URL.
     * Finally, closes the modal.
     */
    async createProject() {

        // Get existing projects from dataStore
        const projects = await this.dataStore.get('projects') || [];
        console.log("projects in createProject(): " + JSON.stringify(projects));

        // Get project name
        const projectNameInput = document.getElementById('projectNameInput');
        const projectName = projectNameInput.value.trim();
        console.log("projectName: " + projectName);

        // Check to see if project already exists
        let projectExists = false;

        for (const project of projects) {
            if (project.projectName === projectName) {
                window.alert(`A project with the name "${projectName}" already exists. Please choose a different name.`);
                return;
            }
        }

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

        // Message to LoadingSpinner
        const message = `Creating ${projectName}. `;
        this.spinner.showLoadingSpinner(message);

        // Call createProject
        const project = await this.client.createProject(projectName);



        // Navigate to project.html with project ID
        window.location.href = `project.html?projectId=${project.projectId}`;

        // Close modal
        this.closeProjectModal();
    }

    ///// WORD POOL MODAL /////

    openWordPoolModal() {
        const wordPoolModal = document.getElementById('wordPoolModal');
        wordPoolModal.style.display = 'block';
    }

    closeWordPoolModal() {
        const wordPoolModal = document.getElementById('wordPoolModal');
        wordPoolModal.style.display = 'none';
    }

    ///// CREATE WORD POOL /////

    /**
     * Creates a new word pool using user-provided word pool name.
     * Retrieves the word pool name from the modal, validates it, and initiates the word pool creation process.
     * Upon successful word pool creation, navigates to the 'wordPool.html' page, passing word pool details in the URL.
     * Finally, closes the modal.
     */
    async createWordPool() {

        // Get existing word pools from dataStore
        const wordPools = await this.dataStore.get('wordPools') || [];
        console.log("wordPools in createWordPool(): " + JSON.stringify(wordPools));

        // Get word pool name
        const wordPoolNameInput = document.getElementById('wordPoolNameInput');
        const wordPoolName = wordPoolNameInput.value.trim();
        console.log("wordPoolName: " + wordPoolName);

        // Check to see if project already exists
        let wordPoolExists = false;

        for (const wordPool of wordPools) {
            if (wordPool.wordPoolName === wordPoolName) {
                window.alert(`A word pool with the name "${wordPoolName}" already exists. Please choose a different name.`);
                return;
            }
        }

        // Check if element is found
        if (wordPoolNameInput) {
            const wordPoolName = wordPoolNameInput.value.trim();
        } else {
            console.error("Element with ID 'wordPoolNameInput' not found");
        }

        // Check if input is valid
        if (wordPoolName === '') {
            alert('Please enter a valid word pool name.');
            return;
        }

        // Message to LoadingSpinner
        const message = `Creating ${wordPoolName}. `;
        this.spinner.showLoadingSpinner(message);

        // Call createWordPool
        const wordPool = await this.client.createWordPool(wordPoolName);

        // Close modal
        this.closeWordPoolModal();

        // Navigate to project.html with project ID
        window.location.href = `wordPool.html?wordPoolId=${wordPool.wordPoolId}`;
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