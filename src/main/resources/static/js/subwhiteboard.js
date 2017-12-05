$(document).ready(function () {
    var imageLoaded = false;
    canvas = document.getElementById('whiteboardCanvas');
    context = canvas.getContext('2d');

    var stompClient = null;
    connect();


    function connect() {
        var socket = new SockJS('/gs-guide-websocket');
        stompClient = Stomp.over(socket);
        stompClient.connect({}, function (frame) {
            console.log('Connected: ' + frame);
            stompClient.subscribe('/topic/whiteboard/' + whiteboard + "/content", function (image) {
                updateCanvas(image.body);
            });
            stompClient.send('/app/hello/whiteboard/' + whiteboard + '/content/send', {}, "");
            stompClient.subscribe('/topic/whiteboard/' + whiteboard, function (pixel) {
                draw(JSON.parse(pixel.body));
            });
        });
    }
    function updateCanvas(image) {
        if(!imageLoaded){
            var loadImage = new Image();
            loadImage.src = image;
            loadImage.onload = function () {
                context.drawImage(loadImage, 0, 0);
            };
            imageLoaded = true;
        }
    }

    function draw(e) {
        context.strokeStyle = e.color;
        context.lineWidth = e.lineWidth;
        context.lineCap = e.lineCap;

        context.beginPath();
        context.moveTo(e.x, e.y);
        context.lineTo(e.offsetX, e.offsetY);
        context.stroke();
    }
});