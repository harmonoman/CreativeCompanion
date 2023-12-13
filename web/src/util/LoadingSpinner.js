export default class LoadingSpinner {

    // Function to show the loading spinner
    showLoadingSpinner(message) {
        console.log("inside LoadingSpinner");
        document.getElementById("loading-message").innerText = message;
        document.getElementById("loading-message-sub").innerText = "I'm much faster once I've warmed up!";
        document.getElementById('loading-spinner').style.display = 'flex';
    }

    // Function to hide the loading spinner
    hideLoadingSpinner() {
       document.getElementById('loading-spinner').style.display = 'none';
    }
}