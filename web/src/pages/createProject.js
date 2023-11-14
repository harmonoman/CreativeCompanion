import CreativeCompanionClient from '../api/creativeCompanionClient';
import Header from '../components/header';
import BindingClass from '../util/bindingClass';
import DataStore from '../util/DataStore';

/**
 * Logic needed for the create project page of the website.
 */
class CreateProject extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['mount', 'submit', 'redirectToViewProject'], this);
        this.dataStore = new DataStore();
        this.dataStore.addChangeListener(this.redirectToViewProject);
        this.header = new Header(this.dataStore);
    }

    /**
     * Add the header to the page and load the CreativeCompanionClient.
     */
    mount() {
        document.getElementById('create').addEventListener('click', this.submit);

        this.header.addHeaderToPage();

        this.client = new CreativeCompanionClient();
    }

    /**
     * Method to run when the create project submit button is pressed. Call the CreativeCompanionService to create the
     * project.
     */
    async submit(evt) {
        evt.preventDefault();

        const errorMessageDisplay = document.getElementById('error-message');
        errorMessageDisplay.innerText = ``;
        errorMessageDisplay.classList.add('hidden');

        const createButton = document.getElementById('create');
        const origButtonText = createButton.innerText;
        createButton.innerText = 'Loading...';

        const projectName = document.getElementById('project-name').value; // <<<<< is 'project-name' correct or should it be 'projectName'?
        // const tagsText = document.getElementById('tags').value; // do I even need tags?

//        let tags;
//        if (tagsText.length < 1) {
//            tags = null;
//        } else {
//            tags = tagsText.split(/\s*,\s*/);
//        }

        const playlist = await this.client.createProject(projectName, /* tags */, (error) => {
            createButton.innerText = origButtonText;
            errorMessageDisplay.innerText = `Error: ${error.message}`;
            errorMessageDisplay.classList.remove('hidden');
        });
        this.dataStore.set('project', project);
    }

    /**
     * When the project is updated in the datastore, redirect to the view project page.
     */
    redirectToViewProject() {
        const project = this.dataStore.get('project');
        if (project != null) {
            window.location.href = `/project.html?id=${project.id}`; // <<<<< is project.id correct? or should it be projectId?
        }
    }
}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const createProject = new CreateProject();
    createProject.mount();
};

window.addEventListener('DOMContentLoaded', main);