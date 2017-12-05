<html>
<head>
    <jsp:include page="fragments/link_header.jsp"/>

    <title>HOME</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
    <p>HELLO HOME</p>

    <form action="${pageContext.request.contextPath}/whiteboard/new">
        <button type="submit">New Whiteboard</button>
    </form>




</body>
</html>
