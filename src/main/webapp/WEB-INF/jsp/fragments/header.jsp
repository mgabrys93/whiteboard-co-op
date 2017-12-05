<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:url var="logoutUrl" value="/logout"/>
<form action="${logoutUrl}" method="post" id="logoutForm">
    <input type="hidden"
           name="${_csrf.parameterName}"
           value="${_csrf.token}"/>
</form>

<jsp:include page="link_header.jsp"/>
<script src="../../../../resources/static/js/headerFunctions.js"></script>
<script>
    $(document).ready(function () {
        userAutocomplete("${pageContext.request.contextPath}", "user-search");
    });
</script>

<nav style="z-index: 1" class="navbar navbar-inverse navbar-static-top">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#my-navbar-collapse">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
        </div>

        <div class="navbar-collapse collapse" id="my-navbar-collapse">
            <ul class="nav navbar-nav">

                <c:if test="${pageContext.request.userPrincipal.name != null}">
                    <li><a href="${pageContext.request.contextPath}/whiteboard/list">Home</a></li>
                    <li><a href="${pageContext.request.contextPath}/whiteboard/new">New Whiteboard</a></li>
                    <li>
                        <div class="navbar-form header-form">
                            <form action="${pageContext.request.contextPath}/user/find" method="post">
                                <div class="form-group ui-widget">
                                    <input id="user-search" type="search" class="form-control" placeholder="Search user..." name="username">
                                </div>
                                <button type="submit" class="btn btn-default">Search</button>
                            </form>
                        </div>
                    </li>
                </c:if>

            </ul>
            <c:choose>
                <c:when test="${pageContext.request.userPrincipal.name != null}">
                    <ul class="nav navbar-nav navbar-right">
                        <li><a href="javascript:formSubmit()">Logout</a></li>
                    </ul>
                </c:when>
                <c:otherwise>
                    <ul class="nav navbar-nav navbar-right">
                        <li><a href="${pageContext.request.contextPath}/registration">Sing up</a></li>
                        <li><a href="${pageContext.request.contextPath}/login">Sign in</a></li>
                    </ul>
                </c:otherwise>
            </c:choose>
        </div>

    </div>
</nav>

<script>
    function formSubmit() {
        document.getElementById("logoutForm").submit();
    }
</script>