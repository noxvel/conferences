<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set var="isSpeaker" value="${not empty sessionScope.user and sessionScope.user.role == 'SPEAKER'}" />
<c:set var="isModerator" value="${not empty sessionScope.user and sessionScope.user.role == 'MODERATOR'}" />

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
    <body class="d-flex flex-column min-vh-100">

        <div class="container">
            <header class="d-flex flex-wrap align-items-center justify-content-center justify-content-md-between py-3 mb-4 border-bottom">
                <a href="${contextPath}/pages/home" class="d-flex align-items-center col-md-3 mb-2 mb-md-0 text-dark text-decoration-none fs-2 fw-bold">
                    Conferences
                </a>

                <ul class="nav col-12 col-md-auto mb-2 justify-content-center mb-md-0">
                    <li><a href="${contextPath}/pages/home" class="nav-link px-2 link-dark"><fmt:message key="header.link.home"/></a></li>
                    <li><a href="${contextPath}/about.jsp" class="nav-link px-2 link-dark"><fmt:message key="header.link.about"/></a></li>
                </ul>

                <div class="d-flex flex-row align-items-center">
                    <form class="me-5" id="changeLanguage" action="${contextPath}/pages/change-language" method="post">
                        <input type="hidden" name="redirectPath" 
                            value="${sessionScope.originRequestURL}${sessionScope.originRequestQuery == null ? '' : '?' += sessionScope.originRequestQuery}" />
                        <select name="lang" class="form-select" onchange="submit()" aria-label="Default select example">
                            <option value="en" ${lang == 'en' ? 'selected' : ''}>EN</option>
                            <option value="uk" ${lang == 'uk' ? 'selected' : ''}>UA</option>
                        </select>
                    </form>
                    <c:choose>
                        <c:when test="${sessionScope.user != null}">
                            <div class="dropdown text-end">
                                <a href="#" class="d-block link-dark text-decoration-none dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="true">
                                        <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="currentColor" class="bi bi-person-circle" viewBox="0 0 20 20">
                                            <path d="M11 6a3 3 0 1 1-6 0 3 3 0 0 1 6 0z"/>
                                            <path fill-rule="evenodd" d="M0 8a8 8 0 1 1 16 0A8 8 0 0 1 0 8zm8-7a7 7 0 0 0-5.468 11.37C3.242 11.226 4.805 10 8 10s4.757 1.225 5.468 2.37A7 7 0 0 0 8 1z"/>
                                        </svg>
                                        <c:out value="${sessionScope.user.firstName} ${sessionScope.user.lastName}"/>
                                </a>
                                <ul class="dropdown-menu text-small" data-popper-placement="top-start">
                                    <c:if test="${isSpeaker or isModerator}">
                                        <li><a class="dropdown-item" href="${contextPath}/pages/report/list"><fmt:message key="header.button.list-of-reports"/></a></li>
                                    </c:if>
                                    <c:if test="${isModerator}">
                                        <li><a class="dropdown-item" href="${contextPath}/pages/event/statistics"><fmt:message key="header.button.event-statistics"/></a></li>
                                    </c:if>
                                    <li><a class="dropdown-item" href="${contextPath}/pages/user/profile"><fmt:message key="header.button.profile"/></a></li>
                                    <li><hr class="dropdown-divider"></li>
                                    <li><a class="dropdown-item" href="${contextPath}/pages/user/sign-out"><fmt:message key="header.button.sign-out"/></a></li>
                                </ul>
                            </div>
                        </c:when>    
                        <c:otherwise>
                            <div class="d-flex flex-row">
                                <a role="button" href="${contextPath}/pages/user/login" class="btn btn-outline-primary me-2"><fmt:message key="header.button.login"/></a>
                                <a role="button" href="${contextPath}/pages/user/registration" class="btn btn-primary"><fmt:message key="header.button.signup"/></a>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>

            </header>
        </div>

        <main class="container flex-fill">
