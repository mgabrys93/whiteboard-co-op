function createAElement(cellNumber, row, id, classString, href, text) {

    var a = document.createElement("a");
    a.setAttribute("id", id);
    a.setAttribute("class", classString);
    a.setAttribute("href", href);
    var span = document.createElement("span");
    span.textContent = text;
    a.appendChild(span);
    row.insertCell(cellNumber).appendChild(a);
}

function changeButtonColor(isActive) {
    if(isActive){
        document.getElementById("activeButton").setAttribute("class", "btn btn-success");
        document.getElementById("inactiveButton").setAttribute("class", "btn");
    }else {
        document.getElementById("activeButton").setAttribute("class", "btn");
        document.getElementById("inactiveButton").setAttribute("class", "btn btn-success");
    }
}


function createPaginationLiElement(parentElement, baseUrl, getWhiteboardUrl, spanText, activeString, isLiActive, page, principalUsername, isActive, searchedUsername) {
    var li = document.createElement("li");
    if(isLiActive) li.setAttribute("class", "active");
    var a = document.createElement("a");
    a.onclick = function () {
        loadWhiteboards(principalUsername, baseUrl, getWhiteboardUrl, page, isActive, searchedUsername);
    }

    var span = document.createElement("span");
    if(spanText === "next"){
        span.setAttribute("class", "glyphicon glyphicon-menu-right");
    }else if(spanText === "prev"){
        span.setAttribute("class", "glyphicon glyphicon-menu-left");
    }else {
        span.textContent = spanText;
    }

    a.appendChild(span);
    li.appendChild(a);
    parentElement.appendChild(li);
}

function createPagination(parentElement, principalUsername, baseUrl, pagesIntervalUrl, getWhiteboardUrl, page, activeString, isActive, searchedUsername) {
    $.get(baseUrl + pagesIntervalUrl, function (data) {
        if(page > 0){
            createPaginationLiElement(parentElement, baseUrl, getWhiteboardUrl, 'prev', activeString, false, page - 1, principalUsername, isActive, searchedUsername);
        }
        var maxPage = 0;
        for(var i in data){
            var updatedPage = parseInt(i) + parseInt(1);
            if(i === page){
                createPaginationLiElement(parentElement, baseUrl, getWhiteboardUrl,
                    updatedPage, activeString, true, updatedPage - 1, principalUsername, isActive, searchedUsername);
            }else {
                createPaginationLiElement(parentElement, baseUrl, getWhiteboardUrl,
                    updatedPage, activeString, false, updatedPage - 1, principalUsername, isActive, searchedUsername);
            }
            maxPage = i;
        }
        if(page < maxPage){
            createPaginationLiElement(parentElement, baseUrl, getWhiteboardUrl, 'next', activeString, false, page + 1, principalUsername, isActive, searchedUsername);
        }
    });
}

function updatePagination(principalUsername, baseUrl, searchedUsername, page, isActive, activeString) {
    var ulElement = document.getElementById("pagination");
    $("#pagination").empty();
    if(searchedUsername != null && searchedUsername.length === 0){
        createPagination(ulElement, principalUsername, baseUrl, "/whiteboard/getPagesInterval?page=" + page + "&active=" + isActive.toString(),
            "/whiteboard/list/", page, activeString, isActive, searchedUsername);
    }else {
        createPagination(ulElement, principalUsername, baseUrl,  "/user/" + searchedUsername + "/getPagesInterval?page=" + page + "&active=" + isActive.toString(),
            "/user/" + searchedUsername + "/whiteboard/list/", page, activeString, isActive, searchedUsername);
    }
}

function loadWhiteboards(principalUsername, baseUrl, getUrl, page, isActive, searchedUsername) {
    changeButtonColor(isActive);
    var activeString = isActive ? "active" : "inactive";
    updateWhiteboards(baseUrl, getUrl, activeString, page, principalUsername, isActive);
    updatePagination(principalUsername, baseUrl, searchedUsername, page, isActive, activeString)
}

function updateWhiteboards(baseUrl, getUrl, activeString, page, principalUsername, isActive) {
    $.get(baseUrl + getUrl + activeString + "?page=" + page, function (whiteboards) {
        $("#tbody").empty();
        for(var i=0;i<whiteboards.length;i++){
            var w = whiteboards[i];
            var tableRef = document.getElementById("mainTable").getElementsByTagName("tbody")[0];
            var newRow = tableRef.insertRow(tableRef.rows.length);
            newRow.setAttribute("id", i);
            newRow.insertCell(0).appendChild(document.createTextNode(w["creationDateTime"]));
            newRow.insertCell(1).appendChild(document.createTextNode(w["ownerUsername"]));

            createAElement(2, newRow, "showButton" + i, "btn btn-default btn-success", baseUrl + "/whiteboard/" + w["id"] + "/show", "Show");
            createAElement(3, newRow, "joinButton" + i, "btn btn-default btn-success", baseUrl + "/whiteboard/" + w["id"] + "/join", "Join");
            createAElement(4, newRow, "editButton" + i, "btn btn-default btn-success", baseUrl + "/whiteboard/" + w["id"] + "/edit", "Edit");
            updateButtonsVisibility(principalUsername, i, w['ownerUsername'], isActive);
        }

    });
}

function updateButtonsVisibility(principalUsername, id, ownerUsername, isActive) {
    if(principalUsername === ownerUsername){
        if(isActive){
            document.getElementById("showButton" + id).style = "display:none";
            document.getElementById("joinButton" + id).style = "display:none";
            document.getElementById("editButton" + id).style = "display:none";
        }else {
            document.getElementById("joinButton" + id).style = "display:none";
        }
    }else {
        if(isActive){
            document.getElementById("showButton" + id).style = "display:none";
            document.getElementById("editButton" + id).style = "display:none";
        }else {
            document.getElementById("joinButton" + id).style = "display:none";
            document.getElementById("editButton" + id).style = "display:none";
        }
    }
}
