var video, classifier, mobilenet;

function setup() {
    var cnv = createCanvas(480, 360);
    cnv.position(950, 320);
    video = createCapture(VIDEO);
    video.hide();
    mobilenet = ml5.featureExtractor("MobileNet", () => {
        console.log("|Model Ready|");
    });
    classifier = mobilenet.classification(video);
}

function draw() {
    background(0);

    push();
    translate(width, 0);
    scale(-1, 1);
    image(video, 0, 0)
    pop();
}