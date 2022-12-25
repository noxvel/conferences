<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set var="isSpeaker" value="${not empty sessionScope.user and sessionScope.user.role == 'SPEAKER'}" />
<c:set var="isModerator" value="${not empty sessionScope.user and sessionScope.user.role == 'MODERATOR'}" />

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Conferences</title>
        <link href="${contextPath}/css/bootstrap.min.css" rel="stylesheet">
        
        <script type="text/javascript" src="${contextPath}/js/bootstrap.bundle.min.js"></script>
        <script type="text/javascript" src="${contextPath}/js/jquery-3.6.1.min.js"></script>
    </head>
    <body class="d-flex flex-column h-100">

        <div class="container">
            <header class="d-flex flex-wrap align-items-center justify-content-center justify-content-md-between py-3 mb-4 border-bottom">
                <a href="${contextPath}/home" class="d-flex align-items-center col-md-3 mb-2 mb-md-0 text-dark text-decoration-none fs-2 fw-bold">
                    Conferences
                </a>

                <ul class="nav col-12 col-md-auto mb-2 justify-content-center mb-md-0">
                    <li><a href="${contextPath}/home" class="nav-link px-2 link-dark">Home</a></li>
                    <li><a href="${contextPath}/about.jsp" class="nav-link px-2 link-dark">About</a></li>
                </ul>

                <div class="d-flex flex-row align-items-center">
                    <form class="me-5" id="chooseLanguage" action="chooseLanguage" method="post">
                        <select class="form-select" aria-label="Default select example">
                            <option selected value="EN">EN</option>
                            <option value="UA">UA</option>
                        </select>
                    </form>
                    <c:choose>
                        <c:when test="${sessionScope.user != null}">
                            <div class="dropdown text-end">
                                <a href="#" class="d-block link-dark text-decoration-none dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="true">
                                    <c:out value="${sessionScope.user.firstName} ${sessionScope.user.lastName}"/>
                                </a>
                                <ul class="dropdown-menu text-small" data-popper-placement="top-start">
                                    <c:if test="${isSpeaker or isModerator}">
                                        <li><a class="dropdown-item" href="${contextPath}/report-list">List of reports</a></li>
                                    </c:if>
                                    <li><a class="dropdown-item" href="${contextPath}/profile">Profile</a></li>
                                    <li><hr class="dropdown-divider"></li>
                                    <li><a class="dropdown-item" href="${contextPath}/signout">Sign out</a></li>
                                </ul>
                            </div>
                        </c:when>    
                        <c:otherwise>
                            <div class="d-flex flex-row">
                                <a role="button" href="${contextPath}/login" class="btn btn-outline-primary me-2">Login</a>
                                <a role="button" href="${contextPath}/registration" class="btn btn-primary">Sign-up</a>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>

            </header>
        </div>
