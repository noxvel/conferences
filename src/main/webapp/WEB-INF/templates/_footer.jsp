<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="mytl" uri="/WEB-INF/custom-taglib.tld" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<fmt:setLocale value="${lang}" />
<fmt:setBundle basename="text" />

<footer class="container d-flex flex-wrap justify-content-between align-items-center py-3 my-4 border-top">
    <p class="col-md-4 mb-0 text-muted">
        <mytl:company-label/>
    </p>

    <ul class="nav col-md-4 justify-content-end">
        <li class="nav-item"><a href="${contextPath}/pages/home" class="nav-link px-2 text-muted"><fmt:message key="footer.link.home"/></a></li>
        <li class="nav-item"><a href="${contextPath}/pages/about" class="nav-link px-2 text-muted"><fmt:message key="footer.link.about"/></a></li>
    </ul>
</footer>
