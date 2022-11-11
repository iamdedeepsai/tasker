let video, classifier, mobilenet, label, started, m;

function setup() {
    var cnv = createCanvas(480, 360);
    cnv.parent('login');
    video = createCapture(VIDEO);
    video.hide();
    mobilenet = ml5.featureExtractor("MobileNet", () => {
        console.log("|Model Ready|");
    });
    classifier = mobilenet.classification(video);

    m = width * (3/5);
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

function addImg(name) {
    console.log("image added")
    classifier.addImage(name.value);
}

function train() {
    console.log("Training");
    classifier.train(loss => {
        if (loss != null) {
            console.log(loss);
        } else {
            console.log("Training Complete!")
            classifier.classify(gotResults);
        }
    });
}

function gotResults(error, result) {
    if (error) {
        console.error(error);
    } else if (result[0].confidence * 100 > 90) {
        label = result[0].label + " " + nf(result[0].confidence * 100, 2, 2) + "%";
        classifier.classify(gotResults);
    } else {
        label = "";
    }
}

const sleepNow = (delay) => new Promise((resolve) => setTimeout(resolve, delay))

async function start(name) {
    started = true;
    for (var i = 0; i < 20; i++) {
        await sleepNow(1000);
        addImg(name);
        m -= (width * (3/5) / 20)
        if (m < 1) {started = false;}
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

    const data = {UUID: crypto.randomUUID(),
                  name: document.getElementById("nam").value,
                  password: document.getElementById("pasw")};

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
}