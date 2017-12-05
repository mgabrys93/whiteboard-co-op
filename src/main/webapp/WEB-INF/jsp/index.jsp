<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="../../../resources/static/css/whiteboard_display_style.css"/>
    <jsp:include page="fragments/link_header.jsp"/>
    <script src="${pageContext.request.contextPath}/webjars/sockjs-client/1.0.2/sockjs.min.js"></script>
    <script src="${pageContext.request.contextPath}/webjars/stomp-websocket/2.3.3/stomp.min.js"></script>
    <script>
        $(document).ready(function () {
            $.get("${pageContext.request.contextPath}/whiteboard/${whiteboardId}/img", function (data, status) {
                document.getElementById("image").src = data;
            });
        });

    </script>
</head>
<body>

    <jsp:include page="fragments/header.jsp"/>
    <img id="image">

</body>
</html>
