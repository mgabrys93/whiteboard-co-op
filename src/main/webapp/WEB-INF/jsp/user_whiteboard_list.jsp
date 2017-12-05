<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script src="../../../resources/static/js/loadElements.js"></script>
    <script type="text/javascript" src="/webjars/jquery/3.1.0/jquery.min.js"></script>
    <jsp:include page="fragments/link_header.jsp"/>
    <script>
        $(document).ready(function () {
            loadWhiteboards('${pageContext.request.userPrincipal.name}',
                '${pageContext.request.contextPath}',
                '/user/${searchedUsername}/whiteboard/list/', 0, true, '${searchedUsername}');
        });
    </script>
</head>
<body>

    <jsp:include page="fragments/header.jsp"/>

    <div class="container">
        <div class="row">
            <div class="panel panel-default">
                <div class="panel-body">
                    <div class="pull-right">
                        <div class="btn-group">
                            <button id="activeButton" type="button" class="btn btn-success"
                                    onclick="loadWhiteboards('${pageContext.request.userPrincipal.name}',
                                    '${pageContext.request.contextPath}',
                                    '/user/${searchedUsername}/whiteboard/list/', 0, true, '${searchedUsername}')">Active</button>
                            <button id="inactiveButton" type="button" class="btn"
                                    onclick="loadWhiteboards('${pageContext.request.userPrincipal.name}',
                                    '${pageContext.request.contextPath}',
                                    '/user/${searchedUsername}/whiteboard/list/', 0, false, '${searchedUsername}')">Inactive</button>
                        </div>
                    </div>
                    <div id="mainTable" class="table-container">
                        <table class="table table-filter">
                            <tbody id="tbody">

                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="text-center">
        <ul id="pagination" class="pagination pagination-lg">

        </ul>
    </div>

</body>
</html>
