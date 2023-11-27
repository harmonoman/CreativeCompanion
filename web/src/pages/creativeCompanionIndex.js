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

        this.bindClassMethods(['mount', 'clientLoaded', /*'displaySearchResults',*/ /*'getHTMLForSearchResults'*/], this);

        this.dataStore = new DataStore();
        this.header = new Header(this.dataStore);
        console.log("creativeCompanionIndex constructor");
    }

    /**
     * Add the header to the page and loads the CreativeCompanionClient.
     */
    mount() {
        this.header.addHeaderToPage();
        this.client = new CreativeCompanionClient();

        this.clientLoaded();
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
}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const creativeCompanionIndex = new CreativeCompanionIndex();
    creativeCompanionIndex.mount();
};


window.addEventListener('DOMContentLoaded', main);
