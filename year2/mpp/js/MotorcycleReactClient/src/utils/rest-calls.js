function status(response) {
    console.log('response status ' + response.status);
    if (response.status >= 200 && response.status < 300) {
        return Promise.resolve(response)
    } else {
        return Promise.reject(new Error(response.statusText))
    }
}

function json(response) {
    return response.json()
}

export function getRaces() {
    var headers = new Headers();
    headers.append('Accept', 'application/json');
    var myInit = { 
        method: 'GET',
        headers: headers,
        mode: 'cors'};
    var request = new Request("http://localhost:8080/races", myInit);

    console.log('Fetch for ' + "http://localhost:8080/races")

    return fetch(request)
        .then(status)
        .then(json)
        .then(data => {
            console.log('Request succeeded with JSON response', data);
            return data;
        }).catch(error => {
            console.log('Request failed', error);
            return error;
        });
}

export function addRace(race) {
    console.log('Before fetch post ' + JSON.stringify(race));

    var myHeaders = new Headers();
    myHeaders.append("Accept", "application/json");
    myHeaders.append("Content-Type","application/json");

    var myInit = { 
        method: 'POST',
        headers: myHeaders,
        mode: 'cors',
        body:JSON.stringify(race)
    };

    return fetch("http://localhost:8080/races", myInit)
        .then(status)
        .then(response => {
            return response.text();
        }).catch(error => {
            console.log('Request failed', error);
            return Promise.reject(error);
        });
}

export function updateRace(race) {
    console.log('Before fetch put ' + JSON.stringify(race));

    var myHeaders = new Headers();
    myHeaders.append("Accept", "application/json");
    myHeaders.append("Content-Type","application/json");

    var myInit = { 
        method: 'PUT',
        headers: myHeaders,
        mode: 'cors',
        body:JSON.stringify(race)
    };

    var id = race.id;

    return fetch("http://localhost:8080/races/" + id, myInit)
        .then(status)
        .then(response => {
            return response.text();
        }).catch(error => {
            console.log('Request failed', error);
            return Promise.reject(error);
        });
}

export function deleteRace(id) {
    console.log('Before delete fetch')
    var myHeaders = new Headers();
    myHeaders.append("Accept", "application/json");

    var myInit = { method: 'DELETE',
        headers: myHeaders,
        mode: 'cors'};

    return fetch("http://localhost:8080/races/" + id, myInit)
        .then(status)
        .then(response => {
            console.log('Delete status '+response.status);
            return response.text();
        }).catch(error => {
            console.log('Request failed', error);
            return Promise.reject(error);
        });
}
