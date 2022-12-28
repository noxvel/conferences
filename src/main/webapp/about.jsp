<%@ page contentType="text/html; charset=UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${lang}" />
<fmt:setBundle basename="text" />

    <jsp:include page="/WEB-INF/templates/header.jsp"/>

        <div class="container d-flex flex-column">
            <h1 class="mb-3"><fmt:message key="about.header.text"/></h2>
            <p><fmt:message key="about.body.text"/></p>
        </div>

    <jsp:include page="/WEB-INF/templates/footer.jsp"/>