var video, classifier, mobilenet, label;

function setup() {
    var cnv = createCanvas(480, 360);
    cnv.parent('login');
    video = createCapture(VIDEO);
    video.hide();
    mobilenet = ml5.featureExtractor("MobileNet", () => {
        console.log("|Model Ready|");
    });
    classifier = mobilenet.classification(video);
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

    i++;
    if (i == 20) {
        console.log(label);
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
    } else {
        label = result[0].label + " " + nf(result[0].confidence * 100, 2, 2) + "%";
        classifier.classify(gotResults);
    }
}


const sleepNow = (delay) => new Promise((resolve) => setTimeout(resolve, delay))

async function start(name) {
    alert("Page will stop responding for a while. Do not worry");
    rect(width * (1/5), height/2 - 10, width * (3/5), 20)
    for (var i = 0; i < 20; i++) {
        await sleepNow(1000);
        addImg(name);
    }
    await sleepNow(10 * 1000);
    setTimeout(train(), 25 * 1000)
}