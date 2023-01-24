<%@ page contentType="text/html; charset=UTF-8" isErrorPage="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set var="status" value="${pageContext.response.status}" />

<fmt:setLocale value="${lang}" />
<fmt:setBundle basename="text" />

<!DOCTYPE html>
<html lang="${lang}">
    <jsp:include page="/WEB-INF/templates/_head.jsp"/>
    <body class="d-flex flex-column h-100">

        <div class="container pt-5">
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

        <jsp:include page="/WEB-INF/templates/_scripts.jsp"/>
    </body>
</html>