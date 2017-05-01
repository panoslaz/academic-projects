var host = 'http://localhost:8080';
/**
 * selects the dom element with the given id and
 * updates its content with the input
 * @param elementId
 * @param value
 */
function updateElementWithValue(elementId, value) {
    var element = document.getElementById(elementId);
    element.innerHTML = value;
}

/**
 * closes the pop up window
 */

function closePopUp() {
    var confirmationModal = document.getElementById('confModal');
    confirmationModal.style.display = "none";
}

/**
 * makes a rest call to the back end in order to compute the diameter,
 * and displays the response to the user.
 * @param din
 */

function computeDiameter(din) {
    var xmlhttp = new XMLHttpRequest();

    xmlhttp.onreadystatechange = function () {
        if (xmlhttp.readyState == XMLHttpRequest.DONE) {
            if (xmlhttp.status == 200) {
                //get the object from the response
                var obj = JSON.parse(xmlhttp.responseText);

                // create modal content and display it
                var confirmationModal = document.getElementById('confModal');
                var value = '<ul><li>ιδανική διάμετρος εμπορίου = ' + obj['ideald'] +
                    '</li><li> αμέσως μικρότερη = ' + obj['idealPrevious'] + '' +
                    '</li><li> αμέσως μεγαλύτερη = ' + obj['idealNext'] + '</li></ul>';
                updateElementWithValue('content', value)

                confirmationModal.style.display = "block";

                // When the user clicks on (x), close the modal
                var span = document.getElementsByClassName("close")[0];
                span.onclick = function () {
                    closePopUp();
                }
            }
            else if (xmlhttp.status == 400) {
                alert('There was an error 400');
            }
            else {
                alert('Error returning response from server;');
            }
        }
    };

    xmlhttp.open("GET", host + "/hydractis/rest/calculator/diameter?din=" + din, true);
    xmlhttp.send();
}

/**
 * validates if diameter is filled,
 * if not it displays a warning message to the user.
 * if it is filled, the method that computes the diameter is called.
 * @param din
 */

function validateAndComputeDiameter(din) {
    var warning = document.getElementsByClassName('din-warning')[0];
    if (din.length == 0) {
        warning.style.display = 'block';
        return;
    } else {
        warning.style.display = 'none';
        computeDiameter(din)
    }
}

// When the user clicks anywhere outside of the modal, close it

window.onclick = function (event) {
    var confirmationModal = document.getElementById('confModal', 'btn', 'b1');
    if (event.target == confirmationModal) {
        confirmationModal.style.display = "none";
    }
}

function hideTable() {
    var resultsTable = document.getElementById("resultsTable");
    var button1 = document.getElementById("btn1");
    button1.style.display = "none";
    resultsTable.style.display = "none";
}

/**
 * makes a rest call to the back end in order to compute the required values,
 * and displays the response to results table.
 */

function computeAll(din, angle, roughness) {
    var xmlhttp = new XMLHttpRequest();

    xmlhttp.onreadystatechange = function () {
        if (xmlhttp.readyState == XMLHttpRequest.DONE) {
            if (xmlhttp.status == 200) {


                var obj = JSON.parse(xmlhttp.responseText);
                var resultsTable = document.getElementById("resultsTable");
                var button1 = document.getElementById("btn1");
                button1.style.display = "block";
                resultsTable.style.display = "block";


                // set the corresponding value to each cell of the table
                updateElementWithValue('angle', obj['angle']);
                updateElementWithValue('roughness', obj['roughness']);
                updateElementWithValue('t', obj['t']);
                updateElementWithValue('qin', obj['qin']);
                updateElementWithValue('idealt', obj['idealt']);
                updateElementWithValue('speed', obj['speed']);
                updateElementWithValue('surface', obj['surface']);
                updateElementWithValue('width', obj['width']);
                updateElementWithValue('energy', obj['energy']);
                updateElementWithValue('froud', obj['froud']);
            }
            else if (xmlhttp.status == 400) {
                alert('There was an error 400');
            }
            else {
                alert('something else other than 200 was returned');
            }
        }
    };

    xmlhttp.open("GET", host + "/hydractis/rest/calculator/allValues?din="
        + din + "&angle=" + angle + "&roughness=" + roughness, true);
    xmlhttp.send();
}
/**
 * validates that all required fields are not empty.
 * If they are not it calls computeAll to copute the final results
 *  else it returns and shows a warning message next to the empty gields
 */

function validateAndComputeAll(din, angle, roughness) {
    var warning = document.getElementsByClassName('din-warning')[0];
    var isValid = true;
    if (din.length == 0) {
        warning.style.display = 'block';
        isValid = false;
    } else {
        warning.style.display = 'none';
    }

    warning = document.getElementsByClassName('angle-warning')[0];
    if (angle.length == 0) {
        warning.style.display = 'block';
        isValid = false;
    } else {
        warning.style.display = 'none';
    }

    warning = document.getElementsByClassName('roughness-warning')[0];
    if (roughness.length == 0) {
        warning.style.display = 'block';
        isValid = false;
    } else {
        warning.style.display = 'none';
    }

    closePopUp();
    if (!isValid) {
        return;
    }

    computeAll(din, angle, roughness);
}

/*--This JavaScript method for Print command--*/

function printDoc() {
    var toPrint = document.getElementById('resultsTable');
    var popupWin = window.open('', '_blank', 'width=500,height=400,location=no,left=200px');
    popupWin.document.open();
    popupWin.document.write('<html><title>::Preview::</title><link rel="stylesheet" type="text/css" href="print.css" /></head><body onload="window.print()">')
    popupWin.document.write(toPrint.outerHTML);
    popupWin.document.write('</html>');
    popupWin.document.close();

}

