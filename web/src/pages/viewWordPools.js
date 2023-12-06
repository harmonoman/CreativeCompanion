import CreativeCompanionClient from '../api/creativeCompanionClient';
import Header from '../components/header';
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";

/**
 * Logic needed for the view word pool page of the website.
 */
class ViewWordPools extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['clientLoaded', 'mount', 'addWordPoolsToPage', 'redirectToViewWordPool', 'submit'], this);
        this.dataStore = new DataStore();
        this.dataStore.addChangeListener(this.addWordPoolsToPage);
        this.header = new Header(this.dataStore);
        console.log("viewWordPools constructor");
    }

    /**
     * Once the client is loaded, get the project metadata and project list.
     */
    async clientLoaded() {
        document.getElementById('wordPoolsSelect').innerText = "(Loading Word Pools...)";
        const wordPools = await this.client.getWordPoolList();
        console.log("(clientLoaded) wordPool should be here: " + wordPool);
        this.dataStore.set('wordPool', wordPool);
        console.log("(clientLoaded) wordPools have been set")
        const userName = await this.client.getUserName();
        document.getElementById('user-name').innerText = userName + "'s WordPools";

    }








}