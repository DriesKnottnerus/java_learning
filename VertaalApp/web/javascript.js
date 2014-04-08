/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var req;
var isIE;
var completeField;
var completeTable;
var autoRow;

function init() {
    completeField = document.getElementById("complete-field");
    completeTable = document.getElementById("complete-table");
    autoRow = document.getElementById("auto-row");
    completeTable.style.top = getElementY(autoRow) + "px";
}

function doCompletion() {
        var url = "autocomplete?action=complete&id=" + escape(completeField.value);
        req = initRequest();
        req.open("GET", url, true);
        req.onreadystatechange = callback;
        req.send(null);
}

function initRequest() {
    if (window.XMLHttpRequest) {
        if (navigator.userAgent.indexOf('MSIE') !== -1) {
            isIE = true;
        }
        return new XMLHttpRequest();
    } else if (window.ActiveXObject) {
        isIE = true;
        return new ActiveXObject("Microsoft.XMLHTTP");
    }
}

function callback() {
    clearTable();
    
    if (req.readyState === 4) {
        if (req.status === 200) {
            parseMessages(req.responseXML);
        }
    }
    
    
}

function appendLine(dutchLine, englishLine) {

    var row;
    var cell1;
    var cell2;
    var linkElement1;
    var linkElement2;

    if (isIE) {
        completeTable.style.display = 'block';
        row = completeTable.insertRow(completeTable.rows.length);
        cell1 = row.insertCell(0);
        cell2 = row.insertCell(1);
    } else {
        completeTable.style.display = 'table';
        row = document.createElement("tr");
        cell1 = document.createElement("td");
        cell2 = document.createElement("td");
        row.appendChild(cell1);
        row.appendChild(cell2);
        completeTable.appendChild(row);
    }

    cell1.className = "popupCell";
    cell2.className = "popupCell";

    linkElement1 = document.createElement("p");
    linkElement1.className = "popupItem";
    linkElement1.appendChild(document.createTextNode(dutchLine));
    cell1.appendChild(linkElement1);
    
    linkElement2 = document.createElement("p");
    linkElement2.className = "popupItem";
    linkElement2.appendChild(document.createTextNode(englishLine));
    cell2.appendChild(linkElement2);
}

function getElementY(element){

    var targetTop = 0;

    if (element.offsetParent) {
        while (element.offsetParent) {
            targetTop += element.offsetTop;
            element = element.offsetParent;
        }
    } else if (element.y) {
        targetTop += element.y;
    }
    return targetTop;
}

function clearTable() {
    if (completeTable.getElementsByTagName("tr").length > 0) {
        completeTable.style.display = 'none';
        for (loop = completeTable.childNodes.length -1; loop >= 0 ; loop--) {
            completeTable.removeChild(completeTable.childNodes[loop]);
        }
    }
}

function parseMessages(responseXML) {

    // no matches returned
    if (responseXML === null) {
        return false;
    } else {

        var vertaalregels = responseXML.getElementsByTagName("vertaalregels")[0];

        if (vertaalregels.childNodes.length > 0) {
            completeTable.setAttribute("bordercolor", "black");
            completeTable.setAttribute("border", "1");

            for (loop = 0; loop < vertaalregels.childNodes.length; loop++) {
                var vertaalregel = vertaalregels.childNodes[loop];
                var dutchLine = vertaalregel.getElementsByTagName("dutchLine")[0];
                var englishLine = vertaalregel.getElementsByTagName("englishLine")[0];
            
                appendLine(dutchLine.childNodes[0].nodeValue,
                    englishLine.childNodes[0].nodeValue);
            }
        }
    }
}
