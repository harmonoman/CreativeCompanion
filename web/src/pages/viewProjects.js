import CreativeCompanionClient from '../api/creativeCompanionClient';
import Header from '../components/header';
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";
//import Project from "../pages/project";

/**
 * Logic needed for the view playlist page of the website.
 */
class ViewProjects extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['clientLoaded', 'mount', 'addProjectsToPage', 'redirectToViewProject', 'submit'], this);
        this.dataStore = new DataStore();
        this.dataStore.addChangeListener(this.addProjectsToPage);
        this.dataStore.addChangeListener(this.redirectToViewProject);
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
        console.log("(addProjectsToPage) are the projects here?: " + JSON.stringify(projects));
        if (projects == null) {
            return;
        }

        document.getElementById("projectsSelect").size = projects.length;
        let optionList = document.getElementById('projectsSelect').options;
        let options = [
          {
            text: 'Option 1',
            value: 'Value 1'
          },
          {
            text: 'Option 2',
            value: 'Value 2',
            selected: true
          },
          {
            text: 'Option 3',
            value: 'Value 3'
          }
        ];

        projects.forEach(projects =>
          optionList.add(
            new Option(projects.projectName, projects.projectId)
          ));
        }


        redirectToViewProject() {
//            console.log("inside redirectToViewProject");
            const projectId = this.dataStore.get('projectId');
            console.log("(redirectToViewProject) here is the supposed projectId: " + projectId)
            if (projectId != null) {
                window.location.href = `/project.html?projectId=${projectId}`;
            }
            //this.project.addProjectsToPage();
        }

        async submit(evt) {
            evt.preventDefault();

            const errorMessageDisplay = document.getElementById('error-message');
            errorMessageDisplay.innerText = ``;
            errorMessageDisplay.classList.add('hidden');

            const projectId = document.getElementById('projectsSelect').value;
            console.log("(submit) selected project's id: " + projectId);

            this.dataStore.set('projectId', projectId);
            console.log("(submit) dataStore: " + JSON.stringify(this.dataStore.getState()));
        }

        // Function to render projects with checkboxes
        renderProjects() {
            const projectList = document.getElementById('projectList');

            projects.forEach(project => {
                const listItem = document.createElement('li');
                listItem.innerHTML = `
                    <input type="checkbox" id="project${project.id}" name="projects[]" value="${project.id}">
                    <label for="project${project.id}">${project.name}</label>
                `;
                projectList.appendChild(listItem);
            });
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
