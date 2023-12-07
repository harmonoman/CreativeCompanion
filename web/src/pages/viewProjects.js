import CreativeCompanionClient from '../api/creativeCompanionClient';
import Header from '../components/header';
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";

/**
 * Logic needed for the view project page of the website.
 */
class ViewProjects extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['clientLoaded', 'mount', 'addProjectsToPage', 'redirectToViewProject', 'submit'], this);
        this.dataStore = new DataStore();
        this.dataStore.addChangeListener(this.addProjectsToPage);
        this.header = new Header(this.dataStore);
        console.log("viewProjects constructor");
    }

    /**
         * Once the client is loaded, get the project metadata and project list.
         */
    async clientLoaded() {
        document.getElementById('projectsSelect').innerText = "(Loading Projects...)";
        const projects = await this.client.getProjectList();
        console.log("(clientLoaded) projects should be here: " + projects);
        this.dataStore.set('projects', projects);
        console.log("(clientLoaded) projects have been set")
        const userName = await this.client.getUserName();
        document.getElementById('user-name').innerText = userName + "'s Projects";

    }

    /**
     * Add the header to the page and load the MusicPlaylistClient.
     */
    mount() {
        document.getElementById('projectsSelect').addEventListener('click', this.submit);

        this.header.addHeaderToPage();

        this.client = new CreativeCompanionClient();
        this.clientLoaded();
    }

    /**
     * When the project is updated in the datastore, update the project metadata on the page.
     */
    addProjectsToPage() {

        const projects = this.dataStore.get('projects');

        if (!projects) {
            return;
        }

        const projectsSelect = document.getElementById('projectsSelect');
        projectsSelect.size = projects.length;

        const optionList = projectsSelect.options;

        // Clear existing options
        optionList.length = 0;

        projects.forEach(project => {
            const option = new Option(project.projectName, project.projectId);

            // Add margin or padding to create spacing
            option.style.marginBottom = '20px';

            optionList.add(option);
        });
    }

    redirectToViewProject(projectId) {
        console.log("(redirectToViewProject) here is the supposed projectId: " + projectId)
        if (projectId != null) {
            window.location.href = `/project.html?projectId=${projectId}`;
        }
    }

    async submit(evt) {
        evt.preventDefault();

        const errorMessageDisplay = document.getElementById('error-message');
        errorMessageDisplay.innerText = ``;
        errorMessageDisplay.classList.add('hidden');

        const projectId = document.getElementById('projectsSelect').value;
        console.log("(submit) selected project's id: " + projectId);

        this.redirectToViewProject(projectId);
    }

}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const viewProjects = new ViewProjects();
    viewProjects.mount();
};

window.addEventListener('DOMContentLoaded', main);
