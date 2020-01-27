var fileName = "";

function loadFile(fileName) {
    this.fileName = fileName;
    var div = document.getElementById('image');
    div
        .style
        .backgroundImage = "url('get_image?name=" + fileName + "')";
    div.style.backgroundSize = "cover";
    document.getElementById("fileName").value = fileName;
}

function send(form) {
    if(fileName != null) {
        clean();
    }
    fetch(form.action, {
        method: 'POST',
        body: new FormData(form)
    }).then(function (data) {
        data.text().then(function (fileName) {
            loadFile(fileName);
        });
    }).catch(function (error) {
        console.log('Request failed', error);
    });
    console.log('We submit form asynchronously (AJAX)');
}

function clean() {
    if (fileName != "") {
        fetch('delete_image', {
            method: 'POST',
            headers: {'Content-Type':'application/x-www-form-urlencoded'}, // this line is important, if this content-type is not set it wont work
            body: 'name=' + fileName
        }).then(function (data) {
            data.text().then(function (result) {
                if(result == "true") {
                    document.getElementById('image').style
                        .backgroundImage = null;
                    document.getElementById("fileName").value = "";
                    fileName = "";
                }
            });
        }).catch(function (error) {
            console.log('Request failed', error);
        });
    }
}
