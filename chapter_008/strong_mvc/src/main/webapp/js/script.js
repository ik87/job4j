var filename = "";

//send form to sent_image with use asynchronously (AJAX)
function send(form) {

    fetch(form.action, {
        method: 'POST',
        body: new FormData(form)
    }).then(function (data) {
        data.text().then(function (filename) {
            clean();
            load(filename);
        });
    }).catch(function (error) {
        console.log('sent request failed', error);
    });
    console.log('We submit form asynchronously (AJAX)');
}

//send form to sent_image with use asynchronously (AJAX)
function load(file) {
    this.filename = file;
    var div = document.getElementById('image');
    div
        .style
        .backgroundImage = "url('get_image?name=" + file + "')";

    div.style.backgroundSize = "cover";
    document.getElementById("filename").value = file;
}


async function del(file) {
    fetch('delete_image', {
        method: 'POST',
        headers: {'Content-Type':'application/x-www-form-urlencoded'}, // this line is important, if this content-type is not set it wont work
        body: 'name=' + file
    }).then(function (data) {
        data.text().then(function (result) {

        });
    }).catch(function (error) {
        console.log('clean request failed', error);
    });
}

function clean() {
    if (this.filename != "") {
        fetch('delete_image', {
            method: 'POST',
            headers: {'Content-Type':'application/x-www-form-urlencoded'}, // this line is important, if this content-type is not set it wont work
            body: 'name=' + filename
        }).then(function (data) {
            data.text().then(function (result) {
                if(result == "true") {
                    document.getElementById('image').style
                        .backgroundImage = null;
                    document.getElementById("filename").value = "";
                    this.filename = "";
                }
            });
        }).catch(function (error) {
            console.log('clean request failed', error);
        });
    }
}
