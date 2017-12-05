<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="../../../resources/static/css/whiteboard_display_style.css">
    <jsp:include page="fragments/link_header.jsp"/>
    <script type="text/javascript" src="../../../resources/static/js/subwhiteboard.js"></script>
    <script src="${pageContext.request.contextPath}/webjars/sockjs-client/1.0.2/sockjs.min.js"></script>
    <script src="${pageContext.request.contextPath}/webjars/stomp-websocket/2.3.3/stomp.min.js"></script>
    <script>
        var whiteboard = "${whiteboardId}";
    </script>
</head>
<body>
    <jsp:include page="fragments/header.jsp"/>

    <div id="canvasDiv">
        <canvas id="whiteboardCanvas" width="1300" height="600"></canvas>
    </div>

</body>
</html>
