let video, classifier, mobilenet, label, started, m;

let imgs = {};

function setup() {
    var cnv = createCanvas(480, 360);
    cnv.parent('login');
    video = createCapture(VIDEO);
    video.hide();
    mobilenet = ml5.featureExtractor("MobileNet", () => {
        console.log("|Model Ready|");
        //loadM();
    });
    classifier = mobilenet.classification(video);


    m = width * (3/5);
}

function loadM() {
    setTimeout(classifier.load("model/model.json", () => {
        console.log("its nerding time");
        classifier.classify(gotResults);
    }), 5000);
}

function windowResized() {
    var a, b = 0;
    if (windowWidth/4 < windowHeight/3) {
        a = windowWidth/4;
        b = (a/4) * 3
    } else {
        b = windowHeight/3;
        a = b/3 * 4;
    }
    resizeCanvas(a, b);
}

var i = 0;

function draw() {
    background(0);

    push();
    translate(width, 0);
    scale(-1, 1);
    image(video, 0, 0)
    pop();

    if (started) {
        stroke(255);
        strokeWeight(3);
        noFill()
        rect(width * (1/5) - 3, height/2 - 23, width * 3/5 + 6, 46);
        noStroke();
        fill(80, 200, 120);
        rect(width * (1/5), height/2 - 20, width * (3/5) - m , 40);
    }

    i++;
    if (i == 20) {
        console.log(label);
        i = 0;
    }
}

function addImg(name, n) {
    console.log("image added")
    classifier.addImage(name.value);
    imgs["img" + n] = video.get(0, 0, video.width, video.height);
}

function train() {
    console.log("Training");
    classifier.train(loss => {
        if (loss != null) {
            console.log(loss);
        } else {
            console.log("Training Complete!")
            classifier.classify(gotResults);
            classifier.save(() => {
                console.log("saved")
            }, "itsnerdingtime");
        }
    });
}

function gotResults(error, result) {
    if (error) {
        console.error(error);
    } else {
        label = (result[0].label + " " + nf(result[0].confidence * 100, 2, 2) + "%") + ", " + (result[1].label + " " + nf(result[1].confidence * 100, 2, 2) + "%");
    }
    // } else if (result[0].confidence * 100 > 80) {
    //     label = result[0].label + " " + nf(result[0].confidence * 100, 2, 2) + "%";
    //     classifier.classify(gotResults);
    // } else {
    //     label = "";
    // }
}

const sleepNow = (delay) => new Promise((resolve) => setTimeout(resolve, delay))

async function start(name) {
    started = true;
    for (var i = 0; i < 20; i++) {
        await sleepNow(1000);
        addImg(name, i+1);
        m -= (width * (3/5) / 20)
        if (m < 5) {setTimeout(() => {started = false; m = width * (3/5)}, 1000)}
    }
    await sleepNow(10 * 1000);
    setTimeout(train(), 25 * 1000)
}

function checkVerified() {
    return label !== "";

}

function login() {
    const XHR = new XMLHttpRequest();
    const FD = new FormData();

    var data = {name: document.getElementById("name").value,
                password: document.getElementById("password").value,
                verified: checkVerified()};

    // Push our data into our FormData object
    for (const [name, value] of Object.entries(data)) {
        FD.append(name, value);
    }

    // Define what happens on successful data submission
    XHR.addEventListener('load', (event) => {
        alert('Yeah! Data sent and response loaded.');
    });

    // Define what happens in case of error
    XHR.addEventListener('error', (event) => {
        alert('Oops! Something went wrong.');
    });

    // Set up our request
    XHR.open('POST', 'https://tasker-nushhack.herokuapp.com/login');

    // Send our FormData object; HTTP headers are set automatically
    XHR.send(FD);
}

function reg() {
    const XHR = new XMLHttpRequest();
    const FD = new FormData();

    const data = {name: document.getElementById("nam").value,
                  password: document.getElementById("pasw"),
                  images: imgs};

    // Push our data into our FormData object
    for (const [name, value] of Object.entries(data)) {
        FD.append(name, value);
    }

    // Define what happens on successful data submission
    XHR.addEventListener('load', (event) => {
        alert('Yeah! Data sent and response loaded.');
    });

    // Define what happens in case of error
    XHR.addEventListener('error', (event) => {
        alert('Oops! Something went wrong.');
    });

    // Set up our request
    XHR.open('POST', 'https://tasker-nushhack.herokuapp.com/register');

    // Send our FormData object; HTTP headers are set automatically
    XHR.send(FD);

    // Add the required HTTP header to handle a multipart form data POST request
    XHR.setRequestHeader('Content-Type', `multipart/form-data; boundary=${boundary}`);

    // Send the data
    XHR.send(data);
}

function sendData() {
    // If there is a selected file, wait it is read
    // If there is not, delay the execution of the function
    if (!file.binary && file.dom.files.length > 0) {
        setTimeout(sendData, 10);
        return;
    }

    // To construct our multipart form data request,
    // We need an XMLHttpRequest instance
    const XHR = new XMLHttpRequest();

    // We need a separator to define each part of the request
    const boundary = "blob";

    // Store our body request in a string.
    let data = "";

    // So, if the user has selected a file
    if (file.dom.files[0]) {
        // Start a new part in our body's request
        data += `--${boundary}\r\n`;

        // Describe it as form data
        data += 'content-disposition: form-data; '
            // Define the name of the form data
            + `name="${file.dom.name}"; `
            // Provide the real name of the file
            + `filename="${file.dom.files[0].name}"\r\n`;
        // And the MIME type of the file
        data += `Content-Type: ${file.dom.files[0].type}\r\n`;

        // There's a blank line between the metadata and the data
        data += '\r\n';

        // Append the binary data to our body's request
        data += file.binary + '\r\n';
    }

    // Text data is simpler
    // Start a new part in our body's request
    data += `--${boundary}\r\n`;

    // Say it's form data, and name it
    data += `content-disposition: form-data; name="${text.name}"\r\n`;
    // There's a blank line between the metadata and the data
    data += '\r\n';

    // Append the text data to our body's request
    data += text.value + "\r\n";

    // Once we are done, "close" the body's request
    data += `--${boundary}--`;

    // Define what happens on successful data submission
    XHR.addEventListener('load', (event) => {
        alert('Yeah! Data sent and response loaded.');
    });

    // Define what happens in case of error
    XHR.addEventListener('error', (event) => {
        alert('Oops! Something went wrong.');
    });

    // Set up our request
    XHR.open('POST', 'https://example.com/cors.php');

    // Add the required HTTP header to handle a multipart form data POST request
    XHR.setRequestHeader('Content-Type', `multipart/form-data; boundary=${boundary}`);

    // Send the data
    XHR.send(data);
}