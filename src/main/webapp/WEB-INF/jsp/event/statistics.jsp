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

    <jsp:include page="/WEB-INF/templates/header.jsp"/>

    <main>
        <section class="py-2 text-center container">
            <h2 class="mb-3"><fmt:message key="event-statistics.header.text"/></h2>
            <div class="d-flex flex-row align-items-center">
                <div class="ms-3 p-2 bg-info bg-opacity-10 border border-info rounded">
                    <form class="row row-cols-lg-auto g-3 align-items-center" action="${contextPath}/pages/event/list-sort" method="post">
                        <input type="hidden" name="redirectPath" value="${originRequestURL}" />
                        <div class="col-12">
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
                    <form action="${contextPath}/pages/event/list-filter" method="post" class="d-flex flex-row align-items-center">
                        <input type="hidden" name="redirectPath" value="${originRequestURL}" />
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
                        <button class="btn btn-warning ms-3" type="submit">
                            <fmt:message key="home.filter.button"/>
                        </button>
                    </form>
                </div>
                <div class="ms-2">
                    <div class="dropdown">
                        <button class="btn btn-secondary dropdown-toggle" type="button" data-bs-toggle="dropdown" aria-expanded="false">
                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-file-earmark-arrow-down" viewBox="0 0 16 16">
                                <path d="M8.5 6.5a.5.5 0 0 0-1 0v3.793L6.354 9.146a.5.5 0 1 0-.708.708l2 2a.5.5 0 0 0 .708 0l2-2a.5.5 0 0 0-.708-.708L8.5 10.293V6.5z"/>
                                <path d="M14 14V4.5L9.5 0H4a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h8a2 2 0 0 0 2-2zM9.5 3A1.5 1.5 0 0 0 11 4.5h2V14a1 1 0 0 1-1 1H4a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1h5.5v2z"/>
                            </svg>  
                            <fmt:message key="event-statistics.button.save-to-file"/>
                        </button>
                        <ul class="dropdown-menu">
                            <c:forEach var="format" items="${fileFormats}">
                                <li><a class="dropdown-item" href="${contextPath}/pages/event/save-statistics?fileFormat=${format}">${format}</a></li>
                            </c:forEach>
                        </ul>
                    </div>
                    <%-- <a role="button" href="${contextPath}/pages/event/save-statistics" class="btn btn-secondary">
                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-file-earmark-arrow-down" viewBox="0 0 16 16">
                            <path d="M8.5 6.5a.5.5 0 0 0-1 0v3.793L6.354 9.146a.5.5 0 1 0-.708.708l2 2a.5.5 0 0 0 .708 0l2-2a.5.5 0 0 0-.708-.708L8.5 10.293V6.5z"/>
                            <path d="M14 14V4.5L9.5 0H4a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h8a2 2 0 0 0 2-2zM9.5 3A1.5 1.5 0 0 0 11 4.5h2V14a1 1 0 0 1-1 1H4a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1h5.5v2z"/>
                        </svg>  
                        <fmt:message key="event-statistics.button.save-to-file"/>
                    </a> --%>
                </div>
            </div>
        </section>

        <div class="album py-3 bg-light">
            <div class="container">
                <div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-3">
                    <table class="table">
                        <thead>
                            <tr>
                                <th scope="col"><fmt:message key="event-statistics.table.event"/></th>
                                <th scope="col"><fmt:message key="event-statistics.table.reports-count"/></th>
                                <th scope="col"><fmt:message key="event-statistics.table.participants-count"/></th>
                                <th scope="col"><fmt:message key="event-statistics.table.participants-came"/></th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="event" items="${events}">
                                <tr>
                                    <th scope="row">${event.name}</th>
                                    <td>${event.reportsCount}</td>
                                    <td>${event.participantsCount}</td>
                                    <td>${event.participantsCame}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>

                <mytag:pagination arialLabel="Event statistics navigation" page="${page}" numOfPages="${numOfPages}" 
                                linkPath="${contextPath}/pages/event/statistics?" />

            </div>
        </div>

    </main>

    <jsp:include page="/WEB-INF/templates/footer.jsp"/>