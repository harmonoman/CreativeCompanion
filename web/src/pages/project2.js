import CreativeCompanionClient from '../api/creativeCompanionClient';
import Header from '../components/header';
import BindingClass from '../util/bindingClass';
import DataStore from '../util/DataStore';

class Project extends BindingClass {

    constructor() {
            super();

            this.bindClassMethods(['mount', 'submit', 'redirectToViewPlaylist'], this);

            this.dataStore = new DataStore();
            this.dataStore.addChangeListener(this.dropText);
            this.dataStore.addChangeListener(this.populateWordPoolField);
            this.dataStore.addChangeListener(this.populateWorkspaceField);
            this.dataStore.addChangeListener(this.redirectToViewPlaylist);
            this.header = new Header(this.dataStore);
    }

    /**
     * Once the client is loaded, get the playlist metadata and song list.
     */
    async clientLoaded() {
        const urlParams = new URLSearchParams(window.location.search);
        const projectId = urlParams.get('projectId');
//        document.getElementById('playlist-name').innerText = "Loading Playlist ...";
        const project = await this.client.getProject(projectId);
        this.dataStore.set('project', project);
//        document.getElementById('songs').innerText = "(loading songs...)";
//        const songs = await this.client.getPlaylistSongs(playlistId);
//        this.dataStore.set('songs', songs);
        }












}
