window.addEventListener("DOMContentLoaded", function() {
    // Grab elements, create settings, etc.
    var video = document.getElementById('video');
    var mediaConfig =  { video: true };
    var errBack = function(e) {
        console.log('An error has occurred!', e)
    };

    // Put video listeners into place
    if(navigator.mediaDevices && navigator.mediaDevices.getUserMedia) {
        navigator.mediaDevices.getUserMedia(mediaConfig).then(function(stream) {
            video.srcObject = stream;
            video.play();
        });
    }
}, false);
