import CreativeCompanionClient from '../api/creativeCompanionClient';
import BindingClass from "../util/bindingClass";

/**
 * The header component for the website.
 */
export default class Header extends BindingClass {
    constructor() {
        super();

        const methodsToBind = [
            'addHeaderToPage', 'createSiteTitle', 'createUserInfoForHeader',
            'createLoginButton', 'createLoginButton', 'createLogoutButton'
        ];
        this.bindClassMethods(methodsToBind, this);

        this.client = new CreativeCompanionClient();
    }

    /**
     * Add the header to the page.
     */
    async addHeaderToPage() {
        const currentUser = await this.client.getIdentity();

        const siteTitle = this.createSiteTitle();
        const userInfo = this.createUserInfoForHeader(currentUser);

        const header = document.getElementById('header');
        header.appendChild(siteTitle);
        header.appendChild(userInfo);
    }

    createSiteTitle() {
        const homeButton = document.createElement('a');
        homeButton.classList.add('header_home');
        homeButton.href = 'index.html';
        homeButton.innerText = 'Creative Companion';

        const siteTitle = document.createElement('div');
        siteTitle.classList.add('site-title');
        siteTitle.appendChild(homeButton);

        return siteTitle;
    }

    createUserInfoForHeader(currentUser) {
        const userInfo = document.createElement('div');
        userInfo.classList.add('user');

        const childContent = currentUser
            ? this.createLogoutButton(currentUser)
            : this.createLoginButton();

        userInfo.appendChild(childContent);

        return userInfo;
    }

    createLoginButton() {
         return this.createButton('Login', this.client.login);
//        const loginButton = document.createElement('button');
//                loginButton.classList.add('login-button');
//                loginButton.innerText = 'Login';
//
//                return loginButton;
    }

    createLogoutButton(currentUser) {
         return this.createButton(`Logout: ${currentUser.name}`, this.client.logout);
//        const logoutButton = document.createElement('button');
//                logoutButton.classList.add('logout-button');
//                logoutButton.innerText = 'Logout';
//
//                // Add event listener for logout functionality if the user is logged in
//                if (currentUser) {
//                    logoutButton.addEventListener('click', () => {
//                        // Implement your logout logic here
//                    });
//                }
//
//                return logoutButton;
    }

    createButton(text, clickHandler) {
        const button = document.createElement('a');
        button.classList.add('button');
        button.href = '#';
        button.innerText = text;

        button.addEventListener('click', async () => {
            await clickHandler();
        });

        return button;
    }
}