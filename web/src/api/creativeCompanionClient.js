import axios from "axios";
import BindingClass from "../util/bindingClass";
import Authenticator from "./authenticator";

/**
 * Client to call the CreativeCompanionService.
 *
 * This could be a great place to explore Mixins. Currently the client is being loaded multiple times on each page,
 * which we could avoid using inheritance or Mixins.
 * https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Classes#Mix-ins
 * https://javascript.info/mixins
  */
export default class CreativeCompanionClient extends BindingClass {

    constructor(props = {}) {
        super();

        const methodsToBind = ['clientLoaded', 'getIdentity', 'login', 'logout', 'createProject', 'getProject',
        'getProjectList', 'updateProject', 'deleteProject', 'createWordPool', 'getWordPool', 'getWordPoolList',
        'updateWordPool', 'deleteWordPool'];

        this.bindClassMethods(methodsToBind, this);

        this.authenticator = new Authenticator();;
        this.props = props;

        axios.defaults.baseURL = process.env.API_BASE_URL;
        this.axiosClient = axios;
        this.clientLoaded();
    }

    /**
     * Run any functions that are supposed to be called once the client has loaded successfully.
     */
    clientLoaded() {
        if (this.props.hasOwnProperty("onReady")) {
            this.props.onReady(this);
        }
    }

    /**
     * Get the identity of the current user
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns The user information for the current user.
     */
    async getIdentity(errorCallback) {
        try {
            const isLoggedIn = await this.authenticator.isUserLoggedIn();

            if (!isLoggedIn) {
                return undefined;
            }

            return await this.authenticator.getCurrentUserInfo();
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

    async getUserName(errorCallback) {
        try {
            const isLoggedIn = await this.authenticator.isUserLoggedIn();
            console.log("isLoggedIn: " + isLoggedIn);

            if (!isLoggedIn) {
                return undefined;
            }

            const userName = await this.authenticator.getCurrentUserName();
            console.log("userName: " + userName);
            return userName;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

    async login() {
        this.authenticator.login();
    }

    async logout() {
        this.authenticator.logout();
    }

    async getTokenOrThrow(unauthenticatedErrorMessage) {
        const isLoggedIn = await this.authenticator.isUserLoggedIn();
        console.log("isLoggedIn: " + isLoggedIn);
        if (!isLoggedIn) {
            throw new Error(unauthenticatedErrorMessage);
        }

        return await this.authenticator.getUserToken();
    }

    ///// PROJECT /////
    /**
     * Create a new project owned by the current user.
     * @param projectName The name of the project to create.
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns The project that has been created.
     */
    async createProject(projectName, errorCallback) {
        try {
            const token = await this.getTokenOrThrow("Only authenticated users can create projects.");
            const response = await this.axiosClient.post(`projects`, {
                projectName: projectName
            }, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });

            return response.data.project;
        } catch (error) {
            this.handleError(error, errorCallback);
        }
    }

    /**
     * Gets the project for the given ID.
     * @param id Unique identifier for a project
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns The project's metadata.
     */
    async getProject(projectId, errorCallback) {
        try {
            const token = await this.getTokenOrThrow("Only authenticated users can get projects.");
            const response = await this.axiosClient.get(`projects/${projectId}`, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            return response.data.project;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

    /**
     * Gets the project for the given ID.
     * @param id Unique identifier for a project
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns The project's metadata.
     */
    async getProject(projectId, errorCallback) {
        try {
            const token = await this.getTokenOrThrow("Only authenticated users can create projects.");
            const response = await this.axiosClient.get(`projects/${projectId}`, {
                 headers: {
                     Authorization: `Bearer ${token}`
                     }
            });
            return response.data.project;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }


    /**
     * Gets the projects for the given ID.
     * @returns The project's metadata.
     */
    async getProjectList() {
        try {
            const token = await this.getTokenOrThrow("Only authenticated users can get projects.");
            const response = await this.axiosClient.get(`projects`, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            return response.data.projects;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }


    /**
     * Updates the project for the given projectID.
     * @returns The project's metadata.
     */
    async updateProject(projectId, projectData, errorCallback) {
        try {
           const token = await this.getTokenOrThrow("Only authenticated users can update projects.");
           const response = await this.axiosClient.put(`projects/${projectId}`, projectData, {
               headers: {
                   Authorization: `Bearer ${token}`
               }
           });
           return response.data.project;
       } catch (error) {
           this.handleError(error, errorCallback)
       }
    }

    /**
     * Deletes the project for the given projectID.
     * @returns The project's metadata.
     */
    async deleteProject(projectId, errorCallback) {
        try {
           const token = await this.getTokenOrThrow("Only authenticated users can update projects.");
           const response = await this.axiosClient.delete(`projects/${projectId}`, {
               headers: {
                   Authorization: `Bearer ${token}`
               }
           });
           return response.data.project;
       } catch (error) {
           this.handleError(error, errorCallback)
       }
    }

    ///// WORD POOL /////

    /**
     * Create a new word pool owned by the current user.
     * @param wordPoolName The name of the word pool to create.
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns The word pool that has been created.
     */
    async createWordPool(wordPoolName, errorCallback) {
        try {
            const token = await this.getTokenOrThrow("Only authenticated users can create word pools.");
            const response = await this.axiosClient.post(`word-pools`, {
                wordPoolName: wordPoolName
            }, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });

            return response.data.wordPool;
        } catch (error) {
            this.handleError(error, errorCallback);
        }
    }

    /**
     * Gets the word pool for the given ID.
     * @param id Unique identifier for a word pool
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns The word pool's metadata.
     */
    async getWordPool(wordPoolId, errorCallback) {
        try {
            const token = await this.getTokenOrThrow("Only authenticated users can get word pool.");
            const response = await this.axiosClient.get(`word-pools/${wordPoolId}`, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            return response.data.wordPool;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

    /**
     * Gets the word pools for the given ID.
     * @returns The word pool's metadata.
     */
    async getWordPoolList() {
        try {
            const token = await this.getTokenOrThrow("Only authenticated users can get word pools.");
            const response = await this.axiosClient.get(`word-pools`, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            return response.data.wordPools;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

    /**
     * Updates the word pool for the given wordPoolID.
     * @returns The word pool's metadata.
     */
    async updateWordPool(wordPoolId, wordPoolData, errorCallback) {
        try {
           const token = await this.getTokenOrThrow("Only authenticated users can update word pools.");
           const response = await this.axiosClient.put(`word-pools/${wordPoolId}`, wordPoolData, {
               headers: {
                   Authorization: `Bearer ${token}`
               }
           });
           return response.data.wordPool;
       } catch (error) {
           this.handleError(error, errorCallback)
       }
    }

    /**
     * Deletes the word pool for the given wordPoolID.
     * @returns The word pool's metadata.
     */
    async deleteWordPool(wordPoolId, errorCallback) {
        try {
           const token = await this.getTokenOrThrow("Only authenticated users can update word pools.");
           const response = await this.axiosClient.delete(`word-pools/${wordPoolId}`, {
               headers: {
                   Authorization: `Bearer ${token}`
               }
           });
           return response.data.wordPool;
       } catch (error) {
           this.handleError(error, errorCallback)
       }
    }

    /**
     * Helper method to log the error and run any error functions.
     * @param error The error received from the server.
     * @param errorCallback (Optional) A function to execute if the call fails.
     */
    handleError(error, errorCallback) {
        console.error(error);

        const errorFromApi = error?.response?.data?.error_message;
        if (errorFromApi) {
            console.error(errorFromApi)
            error.message = errorFromApi;
        }

        if (errorCallback) {
            errorCallback(error);
        }
    }
}