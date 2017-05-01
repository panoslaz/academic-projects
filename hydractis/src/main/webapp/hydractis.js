var host = window.location.protocol + "//" + window.location.host + "/hydractis";
/**
 * selects the dom element with the given id and
 * updates its content with the input
 * @param elementId
 * @param value
 */
function updateElementWithValue(elementId, value) {
    var element = document.getElementById(elementId);
    if(element) {
        element.innerHTML = value;
    }
}

/**
 * hides the given element by setting its display value to none
 * @param el: The element to hide
 */
function hideElement(el) {
    if(el) {
        el.style.display = "none";
    }
}

/**
 * shows the given element by setting its display value to block
 * @param el: The element to show
 */
function showElement(el) {
    if(el) {
        el.style.display = "block";
    }
}

/**
 * closes the pop up window
 */
function closePopUp() {
    hideElement(document.getElementById('confModal'));
}

/**
 * makes a rest call to the back end in order to compute the diameter,
 * and displays the response to the user.
 * @param d
 */

function computeDiameter(qin, tin, angle, roughness, din) {
    var xmlhttp = new XMLHttpRequest();

    xmlhttp.onreadystatechange = function () {
        if (xmlhttp.readyState == XMLHttpRequest.DONE) {
            if (xmlhttp.status == 200) {
                //get the object from the response
                var obj = JSON.parse(xmlhttp.responseText);

                // create modal content and display it
                var value = '<ul><li>ideal diameter in market = ' + obj['idealDiameter'] +
                    '</li><li> smaller diameter = ' + obj['idealPrevious'] + '' +
                    '</li><li> larger diameter = ' + obj['idealNext'] + '</li></ul>';
                updateElementWithValue('content', value);

                document.forms["calculationForm"].elements["diameter"].value = obj['idealDiameter'];

                showElement(document.getElementById('confModal'));

                // When the user clicks on (x), close the modal
                var span = document.getElementsByClassName("close")[0];
                if(span) {
                    span.onclick = function () {
                        closePopUp();
                    }
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

    xmlhttp.open("GET", host + "/rest/calculator/diameter?angle=" + angle + "&roughness=" + roughness + "&qin=" +qin +"&tin=" +tin +"&din=" +din, true);
    xmlhttp.send();
}

// When the user clicks anywhere outside of the modal, close it
window.onclick = function (event) {
    var confirmationModal = document.getElementById('confModal', 'btn', 'b1');
    if (event.target == confirmationModal) {
        hideElement(confirmationModal);
    }
}

/**
 * the function hides the results table and the print button
 */
function hideTable() {
    hideElement(document.getElementById("printBtn"));
    hideElement(document.getElementById("resultsTable"));
}

/**
 * displays the results table filled with the results
 * @param resultsObj: The results object
 */
function displayResults(resultsObj) {

    showElement(document.getElementById("resultsTable"));
    showElement(document.getElementById("printBtn"));

    // set the corresponding value to each cell of the table
    updateElementWithValue('angle', resultsObj['angle']);
    updateElementWithValue('roughness', resultsObj['roughness']);
    updateElementWithValue('tin', resultsObj['tin']);
    updateElementWithValue('qin', resultsObj['qin']);
    updateElementWithValue('idealT', resultsObj['idealT']);
    updateElementWithValue('speed', resultsObj['speed']);
    updateElementWithValue('surface', resultsObj['surface']);
    updateElementWithValue('width', resultsObj['width']);
    updateElementWithValue('energy', resultsObj['energy']);
    updateElementWithValue('froud', resultsObj['froud']);
}

/**
 * makes a rest call to the back end in order to compute the required values,
 * and displays the response to results table.
 */

function computeAll(qin, tin, angle, roughness) {
    var xmlhttp = new XMLHttpRequest();

    xmlhttp.onreadystatechange = function () {
        if (xmlhttp.readyState == XMLHttpRequest.DONE) {
            if (xmlhttp.status == 200) {
                displayResults(JSON.parse(xmlhttp.responseText));
            }
            else if (xmlhttp.status == 400) {
                alert('There was an error 400');
            }
            else {
                alert('something else other than 200 was returned');
            }
        }
    };

    var diameter = document.forms["calculationForm"].elements["diameter"].value;
    xmlhttp.open("GET", host + "/rest/calculator/allValues?qin="
        + qin + "&tin=" + tin + "&angle=" + angle + "&roughness=" + roughness + "&diameter=" + diameter, true);
    xmlhttp.send();
}
/**
 *
 * validates that all required fields are filled.
 * If they are valis it calls computeAll to compute the final results
 *  else it returns and shows a warning message next to the empty fields
 */
function validateAndComputeAll(qin, tin, angle, roughness, din) {
    var isValid = true;

    var qinWarning = document.getElementsByClassName('qin-warning')[0];
    var dinWarning = document.getElementsByClassName('din-warning')[0];
    var tinWarning = document.getElementsByClassName('tin-warning')[0];

    // either din & qin should be filled or just tin
    if (din.length == 0 && qin.length == 0 && tin.length == 0) {
        showElement(dinWarning);
        showElement(qinWarning);
        showElement(tinWarning);
        var msg = 'Obligatory fields, diameter and inflow, or depth'
        dinWarning.innerHTML = msg;
        qinWarning.innerHTML = msg;
        tinWarning.innerHTML = msg;
        isValid = false;
    } else if ((din.length != 0 && qin.length == 0) || (din.length == 0 && qin.length != 0)) {
        showElement(dinWarning);
        showElement(qinWarning);
        hideElement(tinWarning);
        var msg = 'Obligatory fields, diameter and inflow';
        dinWarning.innerHTML = msg;
        qinWarning.innerHTML = msg;
        isValid = false;
    } else {
        hideElement(dinWarning);
        hideElement(qinWarning);
        hideElement(tinWarning);
    }

    var warning = document.getElementsByClassName('angle-warning')[0];
    if (angle.length == 0) {
        showElement(warning);
        isValid = false;
    } else {
        hideElement(warning);
    }

    warning = document.getElementsByClassName('roughness-warning')[0];
    if (roughness.length == 0) {
        showElement(warning);
        isValid = false;
    } else {
        hideElement(warning);
    }

    closePopUp();
    if (!isValid) {
        return;
    }
    computeAll(qin, tin, angle, roughness, din);
}

/*--This JavaScript method for Print command--*/

function printDoc() {
    var toPrint = document.getElementById('resultsTable');
    var popupWin = window.open('', '_blank', 'width=1200,height=400,location=no,left=200px');
    popupWin.document.open();
    popupWin.document.write('<html><title>::Preview::</title><link rel="stylesheet" type="text/css" href="print.css" /></head><body onload="window.print()">')
    popupWin.document.write(toPrint.outerHTML);
    popupWin.document.write('</html>');
    popupWin.document.close();

}

