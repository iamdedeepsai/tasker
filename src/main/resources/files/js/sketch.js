var video, classifier, mobilenet;

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

function onresize() {
    //todo: make size of video resize
}

function draw() {
    background(0);

    push();
    translate(width, 0);
    scale(-1, 1);
    image(video, 0, 0)
    pop();
}

function addImg(name) {
    classifier.addImage(name.value);
}

function train() {
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