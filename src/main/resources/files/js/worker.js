function newTask() {
    document.getElementById('popup').style.display = 'grid';
}

var tasks = [];

function submit() {

    document.getElementById('popup').style.display = 'none';

    tasks.push({
        name: document.getElementById('name').value,
        description: document.getElementById('description').value,
        date: document.getElementById('date').value
    });

    const XHR = new XMLHttpRequest();
    const FD = new FormData();

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
    XHR.open('POST', 'https://tasker-nushhack.herokuapp.com/tasks/add');

    // Send our FormData object; HTTP headers are set automatically
    XHR.send(FD);
}



