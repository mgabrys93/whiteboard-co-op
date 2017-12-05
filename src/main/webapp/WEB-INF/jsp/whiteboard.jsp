<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <jsp:include page="fragments/link_header.jsp"/>
    <script type="text/javascript" src="/webjars/jquery/3.1.0/jquery.min.js"></script>
    <script type="text/javascript" src="../../../resources/static/js/whiteboard.js"></script>
    <script type="text/javascript" src="../../../resources/static/js/whiteboardView.js"></script>
    <link rel="stylesheet" href="../../../resources/static/css/whiteboard_display_style.css">
    <script src="${pageContext.request.contextPath}/webjars/sockjs-client/1.0.2/sockjs.min.js"></script>
    <script src="${pageContext.request.contextPath}/webjars/stomp-websocket/2.3.3/stomp.min.js"></script>
    <script>
        var whiteboard = "${whiteboardId}";
        var isEdited = "${isEdited}";
        var url = "${pageContext.request.contextPath}";
    </script>
</head>
<body>

    <jsp:include page="fragments/header.jsp"/>

    <div class="row first-row">
        <div class="col-md-2 col-md-offset-1">
            <div class="form-inline">
                <div class="form-group">
                    <label for="colorSelect"><span class="label label-primary">Color</span></label>
                    <input id="colorSelect" type="color" name="Color Picker">
                </div>
            </div>
        </div>
        <div class="col-md-2">
            <div class="form-inline">
                <div class="form-group">
                    <input id="markerSize" class="form-control" type="range" min="1" max="50" step="1" value="10" onchange="updateRageValue(this.value)">
                </div>
            </div>
        </div>
        <div class="col-md-2">
            <div class="form-inline">
                <div class="form-group">
                    <label for="markerSizeValue"><span class="label label-info">Marker Size</span></label>
                    <input id="markerSizeValue" class="form-control" type="text" value="10" size="4" disabled>
                </div>
            </div>
        </div>
        <div class="col-md-2 col-md-offset-3">
            <div class="form-inline">
                <div class="form-group">
                    <button class="btn btn-success btn-lg" type="submit" onclick="saveImage()">Save</button>
                </div>
            </div>
        </div>
    </div>





    <div id="canvasDiv">
        <canvas id="whiteboardCanvas" width="1300" height="600"></canvas>
    </div>
</body>
</html>
