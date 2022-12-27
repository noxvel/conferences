<%@ page contentType="text/html; charset=UTF-8" isErrorPage="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="status" value="${pageContext.response.status}" />

<fmt:setLocale value="${lang}" />
<fmt:setBundle basename="text" />

<!DOCTYPE html>
<html lang="${lang}">
    <head>
        <meta charset="UTF-8">
        <title>Conferences</title>
        <link href="${contextPath}/css/bootstrap.min.css" rel="stylesheet">
        
        <script type="text/javascript" src="${contextPath}/js/bootstrap.bundle.min.js"></script>
        <script type="text/javascript" src="${contextPath}/js/jquery-3.6.1.min.js"></script>
    </head>
    <body class="d-flex flex-column h-100">

        <div class="container">
            <h1><fmt:message key="error.text.error-code"/>: ${status}</h1>
            <c:choose>
            <c:when test="${status == 500}">
                <h3><fmt:message key="error.text.error-message"/>: ${pageContext.exception.getMessage()}</h3>
            </c:when >
            <c:when test="${status == 404}">
                <h3><fmt:message key="error.text.page-not-found"/></h3>
            </c:when>
            <c:otherwise>
                <h2><fmt:message key="error.text.error-occurred"/>!</h2>
            </c:otherwise>
            </c:choose>
        </div>

    </body>
</html>