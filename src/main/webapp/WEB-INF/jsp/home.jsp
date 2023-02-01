<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="mytag" tagdir="/WEB-INF/tags" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set var="isModerator" value="${sessionScope.userRole == 'MODERATOR'}" />
<c:set var="isSpeaker" value="${sessionScope.userRole == 'SPEAKER'}" />
<c:set var="isOrdinaryUser" value="${sessionScope.userRole == 'ORDINARY_USER'}" />

<fmt:setLocale value="${lang}" />
<fmt:setBundle basename="text" />

<!DOCTYPE html>
<html lang="${lang}">
    <jsp:include page="/WEB-INF/templates/_head.jsp"/>
    <body class="d-flex flex-column min-vh-100">
        <jsp:include page="/WEB-INF/templates/_header.jsp"/>

        <main class="container flex-fill">

            <section class="py-2 text-center d-flex d-row justify-content-between">
                <div class="d-flex flex-row align-items-center">
                    <div class="d-grid gap-2 d-md-block">
                        <c:if test="${isModerator}">
                            <a role="button" href="event/create" class="btn btn-success">
                                <fmt:message key="home.button.create-event"/>
                            </a>
                        </c:if>
                    </div>
                    <div class="ms-3 p-2 bg-info bg-opacity-10 border border-info rounded">
                        <form class="row row-cols-lg-auto g-3 align-items-center" action="event/list-sort" method="post">
                            <div class="col-12">
                                <input type="hidden" name="redirectPath" value="${sessionScope.originRequestURL}" />
                                <select name="sortType" class="form-select" id="sortTypeSelect" aria-label="sort type select">
                                    <option ${sortType == 'Date' ? 'selected' : ''} value="Date" >
                                        <fmt:message key="home.sort.date"/>
                                    </option>
                                    <option ${sortType == 'ReportsCount' ? 'selected' : ''} value="ReportsCount">
                                        <fmt:message key="home.sort.number-of-reports"/>
                                    </option>
                                    <option ${sortType == 'ParticipantsCount' ? 'selected' : ''} value="ParticipantsCount">
                                        <fmt:message key="home.sort.number-of-participants"/>
                                    </option>
                                </select>
                            </div>
                            <div class="col-12">
                                <select name="sortDirection" class="form-select" id="sortDirectionSelect" aria-label="sort direction select">
                                    <option ${sortDirection == 'Ascending' ? 'selected' : ''} value="Ascending">
                                        <fmt:message key="home.sort.ascending-order"/>
                                    </option>
                                    <option ${sortDirection == 'Descending' ? 'selected' : ''} value="Descending" >
                                        <fmt:message key="home.sort.descending-order"/>
                                    </option>
                                </select>
                            </div>
                            <div class="col-12">
                                <button class="btn btn-warning" type="submit">
                                    <fmt:message key="home.sort.button"/>
                                </button>
                            </div>
                        </form>
                    </div>
                    <div class="ms-3 p-2 d-flex align-items-center bg-secondary bg-opacity-10 border border-secondary rounded">
                        <form action="event/list-filter" method="post" class="d-flex flex-row align-items-center">
                            <input type="hidden" name="redirectPath" value="${sessionScope.originRequestURL}" />
                            <select name="timeFilter" class="form-select" id="timefilterSelect" aria-label="time filter select">
                                <option ${eventTimeFilter == 'AllTime' ? 'selected' : ''} value="AllTime">
                                    <fmt:message key="home.filter.time-filter.all-time"/>
                                </option>
                                <option ${eventTimeFilter == 'Future' ? 'selected' : ''} value="Future" >
                                    <fmt:message key="home.filter.time-filter.future"/>
                                </option>
                                <option ${eventTimeFilter == 'Past' ? 'selected' : ''} value="Past">
                                    <fmt:message key="home.filter.time-filter.past"/>
                                </option>
                            </select>
                            <c:if test="${isSpeaker or isOrdinaryUser}">
                                <div class="form-check d-flex align-items-center ms-3">
                                        <input class="form-check-input w-25" type="checkbox" value="true" name="showInWhichParticipated" 
                                                                id="showInWhichParticipated" ${showEventParticipated ? 'checked' : ''}>
                                        <label class="form-check-label" for="showInWhichParticipated">
                                            <fmt:message key="home.filter.in-which-participated"/>
                                        </label>
                                </div>
                            </c:if>
                            <button class="btn btn-warning ms-3" type="submit">
                                <fmt:message key="home.filter.button"/>
                            </button>
                        </form>
                    </div>
                </div>
                <form action="event/list-view-form" method="post" class="d-flex flex-row align-items-center">
                    <input type="radio" class="btn-check" name="viewForm" value="BLOCK" id="eventsViewFormBlock" 
                            onchange="submit()" autocomplete="off" ${eventListViewForm == 'BLOCK' ? 'checked' : ''} >
                    <label class="btn btn-outline-secondary" for="eventsViewFormBlock">
                        <svg xmlns="http://www.w3.org/2000/svg" width="22" height="22" fill="currentColor" aria-hidden="true" class="bi bi-grid" viewBox="0 0 16 16">
                            <path d="M1 2.5A1.5 1.5 0 0 1 2.5 1h3A1.5 1.5 0 0 1 7 2.5v3A1.5 1.5 0 0 1 5.5 7h-3A1.5 1.5 0 0 1 1 5.5v-3zM2.5 2a.5.5 0 0 0-.5.5v3a.5.5 0 0 0 .5.5h3a.5.5 0 0 0 .5-.5v-3a.5.5 0 0 0-.5-.5h-3zm6.5.5A1.5 1.5 0 0 1 10.5 1h3A1.5 1.5 0 0 1 15 2.5v3A1.5 1.5 0 0 1 13.5 7h-3A1.5 1.5 0 0 1 9 5.5v-3zm1.5-.5a.5.5 0 0 0-.5.5v3a.5.5 0 0 0 .5.5h3a.5.5 0 0 0 .5-.5v-3a.5.5 0 0 0-.5-.5h-3zM1 10.5A1.5 1.5 0 0 1 2.5 9h3A1.5 1.5 0 0 1 7 10.5v3A1.5 1.5 0 0 1 5.5 15h-3A1.5 1.5 0 0 1 1 13.5v-3zm1.5-.5a.5.5 0 0 0-.5.5v3a.5.5 0 0 0 .5.5h3a.5.5 0 0 0 .5-.5v-3a.5.5 0 0 0-.5-.5h-3zm6.5.5A1.5 1.5 0 0 1 10.5 9h3a1.5 1.5 0 0 1 1.5 1.5v3a1.5 1.5 0 0 1-1.5 1.5h-3A1.5 1.5 0 0 1 9 13.5v-3zm1.5-.5a.5.5 0 0 0-.5.5v3a.5.5 0 0 0 .5.5h3a.5.5 0 0 0 .5-.5v-3a.5.5 0 0 0-.5-.5h-3z"/>
                        </svg>
                    </label>

                    <input type="radio" class="btn-check" name="viewForm" value="LIST" id="eventsViewFormList" 
                            onchange="submit()" autocomplete="off" ${eventListViewForm == 'LIST' ? 'checked' : ''} >
                    <label class="btn btn-outline-secondary ms-2" for="eventsViewFormList">
                        <svg xmlns="http://www.w3.org/2000/svg" width="22" height="22" fill="currentColor" aria-hidden="true" class="bi bi-list" viewBox="0 0 16 16">
                            <path fill-rule="evenodd" d="M2.5 12a.5.5 0 0 1 .5-.5h10a.5.5 0 0 1 0 1H3a.5.5 0 0 1-.5-.5zm0-4a.5.5 0 0 1 .5-.5h10a.5.5 0 0 1 0 1H3a.5.5 0 0 1-.5-.5zm0-4a.5.5 0 0 1 .5-.5h10a.5.5 0 0 1 0 1H3a.5.5 0 0 1-.5-.5z"/>
                        </svg>
                    </label>
                </form>
            </section>

            <div class="album py-3 bg-light">
                <div class="container">
                    <c:choose>
                        <c:when test="${eventListViewForm == 'LIST'}">
                        <div class="row row-cols-1">

                            <c:forEach var="event" items="${events}">
                                <div class="col-md-8 offset-md-2 mb-3">
                                    <div class="card shadow-sm">
                                        <div class="card-body">
                                            <h5 class="card-title text-center">${event.name}</h5>
                                            <h6 class="card-subtitle mb-2 text-muted d-flex justify-content-center">
                                                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-geo-alt-fill" viewBox="0 0 16 16">
                                                    <path d="M8 16s6-5.686 6-10A6 6 0 0 0 2 6c0 4.314 6 10 6 10zm0-7a3 3 0 1 1 0-6 3 3 0 0 1 0 6z"/>
                                                </svg>
                                                <div class="ms-1">
                                                    <c:out value="${event.place}"/>
                                                </div>
                                            </h6>
                                            <h6 class="card-subtitle mb-2 text-muted d-flex justify-content-center">
                                                <fmt:parseDate value="${event.beginDate}" pattern="yyyy-MM-dd" var="beginDate" type="date"/>
                                                <fmt:parseDate value="${event.endDate}" pattern="yyyy-MM-dd" var="endDate" type="date"/>
                                                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-calendar4" viewBox="0 0 16 16">
                                                    <path d="M3.5 0a.5.5 0 0 1 .5.5V1h8V.5a.5.5 0 0 1 1 0V1h1a2 2 0 0 1 2 2v11a2 2 0 0 1-2 2H2a2 2 0 0 1-2-2V3a2 2 0 0 1 2-2h1V.5a.5.5 0 0 1 .5-.5zM2 2a1 1 0 0 0-1 1v1h14V3a1 1 0 0 0-1-1H2zm13 3H1v9a1 1 0 0 0 1 1h12a1 1 0 0 0 1-1V5z"/>
                                                </svg>
                                                <div class="ms-1">
                                                    <fmt:formatDate type="date" value="${beginDate}" /> - <fmt:formatDate type="date" value="${endDate}" />
                                                </div>
                                            </h6>
                                            <p class="card-text">${fn:substring(event.description, 0, 500)}...</p>
                                            <div class="d-flex justify-content-center align-items-center">
                                                <div class="btn-group">
                                                    <a role="button" href="event/view?eventID=${event.id}" class="btn btn-sm btn-primary">
                                                        <fmt:message key="home.card.button.view"/>
                                                    </a>
                                                    <c:if test="${isModerator}">
                                                        <a role="button" href="event/edit?eventID=${event.id}" class="btn btn-sm btn-secondary">
                                                            <fmt:message key="home.card.button.edit"/>
                                                        </a>
                                                    </c:if>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="card-footer text-muted text-center">
                                            <small class="text-muted"><b>${event.reportsCount}</b> <fmt:message key="home.card.reports"/></small>
                                            <small class="text-muted"><b>${event.participantsCount}</b> <fmt:message key="home.card.participants"/></small>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                            
                        </div>
                        </c:when>

                        <c:otherwise>
                        <div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-3">

                            <c:forEach var="event" items="${events}">
                                <div class="col">
                                    <div class="card shadow-sm">
                                        <div class="card-body">
                                            <h5 class="card-title">${event.name}</h5>
                                            <h6 class="card-subtitle mb-2 text-muted d-flex align-items-center">
                                                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-geo-alt-fill" viewBox="0 0 16 16">
                                                    <path d="M8 16s6-5.686 6-10A6 6 0 0 0 2 6c0 4.314 6 10 6 10zm0-7a3 3 0 1 1 0-6 3 3 0 0 1 0 6z"/>
                                                </svg>
                                                <div class="ms-1">
                                                    <c:out value="${event.place}"/>
                                                </div>
                                            </h6>
                                            <h6 class="card-subtitle mb-2 text-muted d-flex align-items-center">
                                                <fmt:parseDate value="${event.beginDate}" pattern="yyyy-MM-dd" var="beginDate" type="date"/>
                                                <fmt:parseDate value="${event.endDate}" pattern="yyyy-MM-dd" var="endDate" type="date"/>
                                                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-calendar4" viewBox="0 0 16 16">
                                                    <path d="M3.5 0a.5.5 0 0 1 .5.5V1h8V.5a.5.5 0 0 1 1 0V1h1a2 2 0 0 1 2 2v11a2 2 0 0 1-2 2H2a2 2 0 0 1-2-2V3a2 2 0 0 1 2-2h1V.5a.5.5 0 0 1 .5-.5zM2 2a1 1 0 0 0-1 1v1h14V3a1 1 0 0 0-1-1H2zm13 3H1v9a1 1 0 0 0 1 1h12a1 1 0 0 0 1-1V5z"/>
                                                </svg>
                                                <div class="ms-1">
                                                    <fmt:formatDate type="date" value="${beginDate}" /> - <fmt:formatDate type="date" value="${endDate}" />
                                                </div>
                                            </h6>
                                            <p class="card-text" style="height: 4em;">${fn:substring(event.description, 0, 140)}...</p>
                                            <div class="d-flex justify-content-between align-items-center">
                                                <div class="btn-group">
                                                    <a role="button" href="event/view?eventID=${event.id}" class="btn btn-sm btn-primary">
                                                        <fmt:message key="home.card.button.view"/>
                                                    </a>
                                                    <c:if test="${isModerator}">
                                                        <a role="button" href="event/edit?eventID=${event.id}" class="btn btn-sm btn-secondary">
                                                            <fmt:message key="home.card.button.edit"/>
                                                        </a>
                                                    </c:if>
                                                </div>
                                                <small class="text-muted">
                                                    <b>${event.reportsCount}</b> <fmt:message key="home.card.reports"/>
                                                </small>
                                                <small class="text-muted">
                                                    <b>${event.participantsCount}</b> <fmt:message key="home.card.participants"/>
                                                </small>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                            
                        </div>

                        </c:otherwise>
                    </c:choose>

                    <mytag:pagination arialLabel="Home page navigation" page="${page}" numOfPages="${numOfPages}" 
                            linkPath="home" showLimitSelect="true"/>

                </div>
            </div>

        </main>

        <jsp:include page="/WEB-INF/templates/_footer.jsp"/>
        <jsp:include page="/WEB-INF/templates/_scripts.jsp"/>
    </body>
</html>

