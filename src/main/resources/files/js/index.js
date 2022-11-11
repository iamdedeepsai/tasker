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

function toReg() {
    var cols = document.getElementsByClassName('log');
    for(i = 0; i < cols.length; i++) {
        cols[i].style.display = 'none';
    }

    var cols = document.getElementsByClassName('reg');
    for(i = 0; i < cols.length; i++) {
        cols[i].style.display = 'block';
    }
}

function toLog() {
    var cols = document.getElementsByClassName('reg');
    for(i = 0; i < cols.length; i++) {
        cols[i].style.display = 'none';
    }

    var cols = document.getElementsByClassName('log');
    for(i = 0; i < cols.length; i++) {
        cols[i].style.display = 'grid';
    }
}
