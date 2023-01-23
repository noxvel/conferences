<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set var="isSpeaker" value="${sessionScope.userRole == 'SPEAKER'}" />
<c:set var="isModerator" value="${sessionScope.userRole == 'MODERATOR'}" />

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

        <header class="py-3 mb-3 border-bottom bg-warning bg-gradient">
            <div class="container d-flex flex-wrap align-items-center justify-content-center justify-content-md-between">
                <a href="${contextPath}/pages/home" class="d-flex align-items-center col-md-3 mb-2 mb-md-0 text-dark text-decoration-none fs-2 fw-bold">
                    Conferences
                </a>

                <ul class="nav col-12 col-md-auto mb-2 justify-content-center mb-md-0">
                    <li><a href="${contextPath}/pages/home" class="nav-link px-2 link-dark fs-5"><fmt:message key="header.link.home"/></a></li>
                    <li><a href="${contextPath}/about.jsp" class="nav-link px-2 link-dark fs-5"><fmt:message key="header.link.about"/></a></li>
                </ul>

                <div class="d-flex flex-row align-items-center">
                    <div class="d-flex align-items-center me-5">
                        <div class="me-1">
                            <svg xmlns="http://www.w3.org/2000/svg" width="30" height="30" fill="currentColor" class="bi bi-translate" viewBox="0 0 20 20">
                                <path d="M4.545 6.714 4.11 8H3l1.862-5h1.284L8 8H6.833l-.435-1.286H4.545zm1.634-.736L5.5 3.956h-.049l-.679 2.022H6.18z"/>
                                <path d="M0 2a2 2 0 0 1 2-2h7a2 2 0 0 1 2 2v3h3a2 2 0 0 1 2 2v7a2 2 0 0 1-2 2H7a2 2 0 0 1-2-2v-3H2a2 2 0 0 1-2-2V2zm2-1a1 1 0 0 0-1 1v7a1 1 0 0 0 1 1h7a1 1 0 0 0 1-1V2a1 1 0 0 0-1-1H2zm7.138 9.995c.193.301.402.583.63.846-.748.575-1.673 1.001-2.768 1.292.178.217.451.635.555.867 1.125-.359 2.08-.844 2.886-1.494.777.665 1.739 1.165 2.93 1.472.133-.254.414-.673.629-.89-1.125-.253-2.057-.694-2.82-1.284.681-.747 1.222-1.651 1.621-2.757H14V8h-3v1.047h.765c-.318.844-.74 1.546-1.272 2.13a6.066 6.066 0 0 1-.415-.492 1.988 1.988 0 0 1-.94.31z"/>
                            </svg>
                        </div>
                        <i class="bi bi-translate"></i>
                        <form id="changeLanguage" action="${contextPath}/pages/change-language" method="post">
                            <input type="hidden" name="redirectPath" 
                                value="${sessionScope.originRequestURL}${sessionScope.originRequestQuery == null ? '' : '?' += sessionScope.originRequestQuery}" />
                            <select name="lang" class="form-select" onchange="submit()" aria-label="Default select example">
                                <option value="en" ${lang == 'en' ? 'selected' : ''}>EN</option>
                                <option value="uk" ${lang == 'uk' ? 'selected' : ''}>UA</option>
                            </select>
                        </form>
                    </div>
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
                                        <li><a class="dropdown-item" href="${contextPath}/pages/user/list"><fmt:message key="header.button.user-list"/></a></li>
                                    </c:if>
                                    <li><a class="dropdown-item" href="${contextPath}/pages/user/profile"><fmt:message key="header.button.profile"/></a></li>
                                    <li><hr class="dropdown-divider"></li>
                                    <li><a class="dropdown-item" href="${contextPath}/pages/user/sign-out"><fmt:message key="header.button.sign-out"/></a></li>
                                </ul>
                            </div>
                        </c:when>    
                        <c:otherwise>
                            <div class="d-flex flex-row">
                                <a role="button" href="${contextPath}/pages/user/login" class="btn btn-outline-dark me-2"><fmt:message key="header.button.login"/></a>
                                <a role="button" href="${contextPath}/pages/user/registration" class="btn btn-dark"><fmt:message key="header.button.signup"/></a>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>

            </div>
        </header>

        <main class="container flex-fill">
