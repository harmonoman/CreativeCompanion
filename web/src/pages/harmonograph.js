// harmonograph.js

window.onload = function () {
    const canvas = document.createElement('canvas');
    document.body.appendChild(canvas);
    const ctx = canvas.getContext('2d');

    // Set canvas size to match window size
    canvas.width = window.innerWidth;
    canvas.height = window.innerHeight;

    // Define the desired range for amplitude
    const minAmplitude = 350;
    const maxAmplitude = 350;

    let amplitude1, frequency1, phase1;
    let amplitude2, frequency2, phase2;
    let damping;

    // Initialize harmonograph parameters
    loadHarmonographParameters();



    // Simulation parameters
    let time = 50;

    drawHarmonograph();

    ///// DRAW the HARMONOGRAPH /////
    function drawHarmonograph() {

        const x = amplitude1 * Math.sin(frequency1 * time + phase1) * Math.exp(-damping * time);
        const y = amplitude2 * Math.sin(frequency2 * time + phase2) * Math.exp(-damping * time);


        // Calculate the color based on the evolving time
        const red = Math.sin(0.01 * time) * 127 + 128;
        const green = Math.sin(0.02 * time) * 127 + 128;
        const blue = Math.sin(0.03 * time) * 127 + 128;

        // Draw point with evolving color
        ctx.fillStyle = `rgb(${red}, ${green}, ${blue})`;
        ctx.beginPath();
        ctx.arc(canvas.width / 2 + x, canvas.height / 2 + y, 2, 0, 2 * Math.PI);  // Larger point
        ctx.fill();

        // Update time for animation
        time += 0.0145; // Double the speed

        // Request the next animation frame
        requestAnimationFrame(drawHarmonograph);
    }

    // Redraw the harmonograph on window resize
    window.addEventListener('resize', () => {
        canvas.width = window.innerWidth;
        canvas.height = window.innerHeight;

        // Adjust amplitude values based on the new canvas width
        amplitude1 = getRandomValue(50, canvas.width / 2);
        amplitude2 = getRandomValue(50, canvas.width / 2);
    });


    ///// RESET HARMONONGRAPH /////
    // Function to reset harmonograph parameters
    function resetHarmonograph() {
        ctx.clearRect(0, 0, canvas.width, canvas.height);
        amplitude1 = getRandomValue(minAmplitude, maxAmplitude);
        console.log("(resetHarmonograph) amplitude1: " + amplitude1);
        frequency1 = getRandomValue(0.1, 1);
        console.log("(resetHarmonograph) frequency1: " + frequency1);
        phase1 = 0;

        amplitude2 = getRandomValue(minAmplitude, maxAmplitude);
        console.log("(resetHarmonograph) amplitude2: " + amplitude2);
        frequency2 = getRandomValue(0.1, 1);
        console.log("(resetHarmonograph) frequency1: " + frequency1);

        phase2 = Math.PI / 2;

        damping = getRandomValue(0.0003, 0.0004);
        console.log("(resetHarmonograph) damping: " + damping);

        // Save harmonograph parameters to localStorage
        saveHarmonographParameters();
    }

    ///// INITIALIZE HARMONOGRAPH /////
    // Function to initialize the harmonograph
    function initializeHarmonograph() {
        // Initial drawing
        drawHarmonograph();
    }

    ///// LOAD HARMONOGRAPH PARAMETERS /////
    // Function to load harmonograph parameters from localStorage
    function loadHarmonographParameters() {
        const storedAmplitude1 = sessionStorage.getItem('amplitude1');
        amplitude1 = storedAmplitude1 ? parseFloat(storedAmplitude1) : getRandomValue(minAmplitude, maxAmplitude);
        const storedFrequency1 = sessionStorage.getItem('frequency1');
        frequency1 = storedFrequency1 ? parseFloat(storedFrequency1) : getRandomValue(0.1, 1);
        phase1 = 0;

        const storedAmplitude2 = sessionStorage.getItem('amplitude2');
        amplitude2 = storedAmplitude2 ? parseFloat(storedAmplitude2) : getRandomValue(minAmplitude, maxAmplitude);
        const storedFrequency2 = sessionStorage.getItem('frequency2');
        frequency2 = storedFrequency2 ? parseFloat(storedFrequency2) : getRandomValue(0.1, 1);
        phase2 = Math.PI / 2;

        const storedDamping = sessionStorage.getItem('damping');
        damping = storedDamping ? parseFloat(storedDamping) : getRandomValue(0.0001, 0.001);

        // Load time value from sessionStorage
//            const storedTime = sessionStorage.getItem('time');
//            time = storedTime ? parseFloat(storedTime) : 50;

        // If any parameter is not present in localStorage, use a default value
    }

    ///// SAVE HARMONOGRAPH PARAMETERS /////
    // Function to save harmonograph parameters to localStorage
    function saveHarmonographParameters() {
        sessionStorage.setItem('amplitude1', amplitude1.toString());
        sessionStorage.setItem('frequency1', frequency1.toString());
        sessionStorage.setItem('amplitude2', amplitude2.toString());
        sessionStorage.setItem('frequency2', frequency2.toString());

        sessionStorage.setItem('damping', damping.toString());

        // Save the time value to sessionStorage
//            sessionStorage.setItem('time', time.toString());
        // Repeat for other parameters...
    }

    // Function to generate a random value within a specified range
    function getRandomValue(min, max) {
        return Math.random() * (max - min) + min;
    }

    // Reset button event listener
    const buttonGroup = document.querySelector('.button-group');
    // Reset button event listener
    const resetButton = document.getElementById('resetButton');

    // Add a listener to the button
    resetButton.addEventListener('click', () => {
        // Reset harmonograph parameters and restart rendering
        resetHarmonograph();
        time = 50; // Reset time
        initializeHarmonograph();
    });

    // Append button to .button-group
    buttonGroup.appendChild(resetButton);
    // Append the container to the document body
    document.body.appendChild(buttonGroup);

    // Call initialize Harmonograph when the page loads
    initializeHarmonograph();
};