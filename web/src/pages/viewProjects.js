import CreativeCompanionClient from '../api/creativeCompanionClient';
import Header from '../components/header';
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";
//import LoadingSpinner from '../util/LoadingSpinner';

/**
 * Logic needed for the view project page of the website.
 */
class ViewProjects extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['clientLoaded', 'mount', 'addProjectsToPage', 'redirectToViewProject', 'submit1', 'submit2'], this);
        this.dataStore = new DataStore();
        this.dataStore.addChangeListener(this.addProjectsToPage);
        this.header = new Header(this.dataStore);
        console.log("viewProjects constructor");
    }

    /**
     * Once the client is loaded, get the project metadata and project list.
     */
    async clientLoaded() {
        const projectSelect = document.getElementById('projectsSelect');
            projectSelect.classList.add('shadow-wrapper');
            document.getElementById('projectsSelect').innerText = "(Loading Projects...)";

        const projects = await this.client.getProjectList();
        console.log("(clientLoaded) projects should be here: " + projects);

        this.dataStore.set('projects', projects);
        console.log("(clientLoaded) projects have been set")

        const userName = await this.client.getUserName();
        const userNameElement = document.getElementById('user-name');
            userNameElement.classList.add('shadow-wrapper');
            document.getElementById('user-name').innerText = userName + "'s Projects";

    }

    /**
     * Add the header to the page and load the CreativeCompanionClient.
     */
    mount() {

        document.getElementById('projectsSelect').addEventListener('click', this.submit1);
        document.getElementById('searchProjectBtn').addEventListener('click', this.submit2);

        this.header.addHeaderToPage();

        this.client = new CreativeCompanionClient();
//        this.spinner = new LoadingSpinner();
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

    async submit1(evt) {
        evt.preventDefault();

        const errorMessageDisplay = document.getElementById('error-message');
        errorMessageDisplay.innerText = ``;
        errorMessageDisplay.classList.add('hidden');

        const projectSelect = document.getElementById('projectsSelect');
        const projectId = projectSelect.value;

//        // Get projectName from dataStore for LoadingSpinner
//        const projects = await this.dataStore.get('projects');
//        projects.forEach(project => {
//            if (projectId === project.projectId) {
//                const projectName  = project.projectName;
//                console.log("projectName: " + projectName);
//                // Message to LoadingSpinner
//                const message = `Saving ${projectName}. `;
//                this.spinner.showLoadingSpinner(message);
//            }
//        });



        // Check if the selected project is not empty
        if (projectId) {
            console.log("(submit1) selected project's id: " + projectId);
            this.redirectToViewProject(projectId);
        } else {
            // Handle the case where no project is selected (e.g., display an error message)
            errorMessageDisplay.innerText = "Please create a new project.";
            errorMessageDisplay.classList.remove('hidden');
        }
    }

    async submit2(evt) {
        evt.preventDefault();

        const errorMessageDisplay = document.getElementById('error-message');
        errorMessageDisplay.innerText = ``;
        errorMessageDisplay.classList.add('hidden');

        const projectName = document.getElementById('projectNameInput').value.trim();
        console.log("(submit2) selected project's name: " + projectName);

        const project = await this.client.getProjectByName(projectName);
        console.log("(submit) here is the supposed projectId based on the name: " + JSON.stringify(project));

        if (project && project.projectId != null) {
            this.redirectToViewProject(project.projectId);
        } else {
            // Handle the case where no word pool is selected (e.g., display an error message)
            errorMessageDisplay.innerText = "Please create a new project.";
            errorMessageDisplay.classList.remove('hidden');
        }
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
