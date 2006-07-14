function createXMLHttpRequest() {
    try {return new ActiveXObject("Msxml2.XMLHTTP");} catch (e) {}
    try {return new ActiveXObject("Microsoft.XMLHTTP");} catch (e) {}
    try {return new XMLHttpRequest();} catch(e) {}
    return null;
}

function load(url, id) {
    var xmlhttp = createXMLHttpRequest();
    xmlhttp.onreadystatechange = function() {
        if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
            document.getElementById(id).innerHTML = xmlhttp.responseText;
        }
    };
    xmlhttp.open('GET', url);
    xmlhttp.send(null);
}
