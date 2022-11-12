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


