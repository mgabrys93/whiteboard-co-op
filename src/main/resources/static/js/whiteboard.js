
function connect() {
    var socket = new SockJS('/gs-guide-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        stompClient.subscribe('/topic/whiteboard/' + whiteboard + "/content/send", function(e){
            sendContent();
        });
        console.log('Connected: ' + frame);
    });
}

function sendContent() {
    stompClient.send("/app/hello/whiteboard/" + whiteboard + "/content", {}, getDataUrl())
}

function getDataUrl() {
    return document.getElementById("whiteboardCanvas").toDataURL();
}

function saveImage() {
    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        type: 'POST',
        url: url + "/whiteboard/" + whiteboard + "/save",
        data: JSON.stringify({"dataUrl": getDataUrl()}),
        success: function (data) {
            console.error("IMAGE SAVED");
        }
    });
}


function sendPixel(color, x, y, offsetX, offsetY) {
    stompClient.send("/app/hello/whiteboard/" + whiteboard, {}, JSON.stringify(
        {
            'color': color.toString(),
            'x': x,
            'y': y,
            'offsetX': offsetX,
            'offsetY': offsetY,
            'lineWidth': getLineWidth(),
            'lineCap': getLineCap()
        }));
}

function getLineCap() {
    return 'round';
}

function getLineWidth() {
    return  parseInt(document.getElementById('markerSize').value);
}

$(document).ready(function () {
    canvas = document.getElementById('whiteboardCanvas');
    context = canvas.getContext('2d');
    isDrawing = false;
    lastX = 0;
    lastY = 0;
    if(isEdited){
        $.get(url + "/whiteboard/" + whiteboard + "/img", function (data) {
            var loadImage = new Image();
            loadImage.src = data;
            loadImage.onload = function () {
                context.drawImage(loadImage, 0, 0);
            }
        });

    }
    var stompClient = null;
    connect();


    function getColor() {
        return document.getElementById('colorSelect').value;
    }

    function draw(e) {
        if(!isDrawing){
            return;
        }
        context.strokeStyle = getColor();
        context.lineWidth = getLineWidth();
        context.lineCap = 'round';

        context.beginPath();
        context.moveTo(lastX, lastY);
        context.lineTo(e.offsetX, e.offsetY);
        context.stroke();
        sendPixel(getColor(), lastX, lastY, e.offsetX, e.offsetY);
        [lastX, lastY] = [e.offsetX, e.offsetY];

    }

    canvas.addEventListener('mousemove', draw);
    canvas.addEventListener('mousedown', (e) => {
        isDrawing = true;
        [lastX, lastY] = [e.offsetX, e.offsetY];
    });
    canvas.addEventListener('mouseup', () => isDrawing = false);
    canvas.addEventListener('mouseout', () => isDrawing = false);
})
