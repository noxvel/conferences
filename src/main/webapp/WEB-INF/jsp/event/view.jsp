<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="mytag" tagdir="/WEB-INF/tags" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set var="isSpeaker" value="${sessionScope.userRole == 'SPEAKER'}" />
<c:set var="isModerator" value="${sessionScope.userRole == 'MODERATOR'}" />
<c:set var="isUser" value="${sessionScope.userRole == 'ORDINARY_USER'}" />

<fmt:setLocale value="${lang}" />
<fmt:setBundle basename="text" />
<!DOCTYPE html>
<html lang="${lang}">
    <jsp:include page="/WEB-INF/templates/_head.jsp"/>
    <body class="d-flex flex-column min-vh-100">
        <jsp:include page="/WEB-INF/templates/_header.jsp"/>

        <main class="container flex-fill">

            <div class="d-flex flex-column">
                <nav aria-label="breadcrumb">
                    <ol class="breadcrumb">
                        <li class="breadcrumb-item"><a href="${contextPath}/pages/home"><fmt:message key="event-view.breadcrumb.home"/></a></li>
                        <li class="breadcrumb-item active" aria-current="page">${event.name}</li>
                    </ol>
                </nav>
                <div class="border-bottom border-dark">
                    <h3>${event.name}</h3>
                </div>

                <section class="py-3 text-center container">
                    <div class="d-flex flex-row">
                        <div class="d-grid gap-2 d-md-block">
                            <c:if test="${isModerator}">
                                <a role="button" href="edit?eventID=${event.id}" class="btn btn-secondary">
                                    <fmt:message key="event-view.button.edit"/>
                                </a>
                                <a role="button" href="delete?eventID=${event.id}" class="btn btn-danger">
                                    <fmt:message key="event-view.button.delete"/>
                                </a>
                            </c:if>
                            <c:if test="${empty sessionScope.user or isUser}">
                                <form action="register" method="post">
                                    <input type="hidden" name="register" value="${!isRegister}"/>
                                    <input type="hidden" name="eventID" value="${event.id}"/>
                                    <c:if test="${isRegister == null}">
                                        <fmt:message key="event-view.register.tooltip" var="registerButtonTooltip"/>
                                        <span class="d-inline-block" tabindex="0" data-bs-toggle="tooltip" data-bs-title="${registerButtonTooltip}">
                                    </c:if>
                                        <fmt:message key="event-view.register.button.unregister" var="registerButtonUnregisterName"/>
                                        <fmt:message key="event-view.register.button.register" var="registerButtonRegisterName"/>
                                        <button ${isRegister == null ? 'disabled' : ''} class="btn btn-${isRegister == true ? 'danger' : 'primary'}" type="submit">
                                            <c:out value="${isRegister ? registerButtonUnregisterName : registerButtonRegisterName}"/>
                                        </button>
                                    <c:if test="${isRegister == null}">
                                        </span>
                                    </c:if>
                                </form>
                                <%-- <a role="button" href="register?eventID=${event.id}&register=${!isRegister}" class="btn btn-primary">
                                    <c:out value="${isRegister ? 'Unregister' : 'Register'}"/> to event
                                </a> --%>
                            </c:if>
                        </div> 
                    </div>
                </section>

                <h4><fmt:message key="event-view.text.location"/>: ${event.place}</h4>
                <h5><fmt:message key="event-view.text.event-date"/>: 
                    <fmt:parseDate value="${event.beginDate}" pattern="yyyy-MM-dd'T'HH:mm" var="beginDate" type="both"/>
                    <fmt:parseDate value="${event.endDate}" pattern="yyyy-MM-dd'T'HH:mm" var="endDate" type="both"/>
                        <fmt:formatDate type="both" dateStyle="medium" timeStyle="short" value="${beginDate}" /> - 
                        <fmt:formatDate type="both" dateStyle="medium" timeStyle="short" value="${endDate}" /> 
                </h5>

                <nav>
                    <div class="nav nav-tabs" id="nav-tab-event-view" role="tablist">
                        <button class="nav-link active" id="nav-profile-tab" data-bs-toggle="tab" data-bs-target="#nav-reports" type="button" 
                                role="tab" aria-controls="nav-reports" aria-selected="false"><fmt:message key="event-view.tab-button.reports"/></button>
                        <button class="nav-link" id="nav-description-tab" data-bs-toggle="tab" data-bs-target="#nav-description" type="button" 
                                role="tab" aria-controls="nav-description" aria-selected="true"><fmt:message key="event-view.tab-button.description"/></button>
                        <c:if test="${isModerator}">
                            <button class="nav-link" id="nav-participants-tab" data-bs-toggle="tab" data-bs-target="#nav-participants" type="button" 
                                role="tab" aria-controls="nav-participants" aria-selected="true">
                                <fmt:message key="event-view.tab-button.participants"/>
                                <span class="badge text-bg-primary">
                                    <c:out value="${event.participants.size()}" />
                                </span>
                            </button>
                        </c:if>
                    </div>
                </nav>
                <div class="tab-content pt-3" id="nav-tabContent">
                    <div class="tab-pane fade show active" id="nav-reports" role="tabpanel" aria-labelledby="nav-reports-tab" tabindex="0">

                        <c:if test="${isSpeaker or isModerator}">
                            <section class="py-3 text-center container">
                                <div class="d-flex flex-row">
                                    <a role="button" href="${contextPath}/pages/report/create?eventID=${event.id}" class="btn btn-success">
                                        <fmt:message key="event-view.button.create-new-report"/>
                                    </a>
                                    <div class="ms-2">
                                        <form action="${contextPath}/pages/report/list-filter" method="post">
                                            <input type="hidden" name="redirectPath" value="/event/view?eventID=${event.id}"/>
                                            <div class="input-group">
                                                <select name="reportStatusFilter" class="form-select" id="statusFilter" aria-label="Status filter">
                                                    <option ${empty reportStatusFilter ? 'selected' : ''} value=""><fmt:message key="event-view.filter.select.all"/></option>
                                                    <c:forEach var="status" items="${reportStatuses}">
                                                        <option ${reportStatusFilter == status ? 'selected' : ''} value="${status}">
                                                            <mytag:report-status-i18n reportStatus="${status}"/>
                                                        </option>
                                                    </c:forEach>
                                                </select>
                                                <button class="btn btn-primary" type="submit"><fmt:message key="event-view.filter.button"/></button>
                                            </div>
                                        </form>
                                    </div>
                                </div>
                            </section>
                        </c:if>

                        <div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-3">
                            <c:forEach var="report" items="${event.reports}">
                                <c:if test="${isModerator or isSpeaker}">
                                    <mytag:report-status-color reportStatus="${report.status}"/>
                                </c:if>
                                <div class="col">
                                    <div class="card shadow-md ${empty reportColor ? '' : 'border-' += reportColor}">
                                        <div class="card-body">
                                            <h5 class="card-title">${report.topic}</h5>
                                            <c:if test="${isModerator or isSpeaker}">
                                                <h6 class="card-subtitle mb-2 ${empty reportColor ? '' : 'text-' += reportColor}">
                                                    <mytag:report-status-i18n reportStatus="${report.status}"/>
                                                </h6>
                                            </c:if>
                                            <h6><fmt:message key="report-view.text-speaker"/> 
                                            <c:choose>
                                                <c:when test="${report.speaker != null}">
                                                    ${report.speaker.firstName} ${report.speaker.lastName}
                                                </c:when>
                                                <c:otherwise>
                                                    <fmt:message key="report-view.text.no-speaker"/>
                                                </c:otherwise>
                                            </c:choose>
                                            </h6>
                                            <p class="card-text" style="height: 4em;">${fn:substring(report.description, 0, 140)}...</p>
                                            <div class="d-flex justify-content-between align-items-center">
                                                <div class="btn-group">
                                                    <a role="button" href="${contextPath}/pages/report/view?reportID=${report.id}" class="btn btn-sm btn-outline-primary">
                                                        <fmt:message key="event-view.report.button.view"/>
                                                    </a>
                                                    <c:if test="${isModerator}">
                                                        <a role="button" href="${contextPath}/pages/report/edit?reportID=${report.id}" class="btn btn-sm btn-outline-secondary">
                                                        <fmt:message key="event-view.report.button.edit"/>
                                                    </a>
                                                    </c:if>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>

                        <mytag:pagination arialLabel="Event page navigation" page="${page}" numOfPages="${numOfPages}" 
                                    linkPath="view" queryString="&eventID=${event.id}" />

                    </div>
                    <div class="tab-pane fade" id="nav-description" role="tabpanel" aria-labelledby="nav-description-tab" tabindex="0">
                        <c:out value="${event.description}" />
                    </div>
                    <c:if test="${isModerator}">
                        <div class="tab-pane fade" id="nav-participants" role="tabpanel" aria-labelledby="nav-participants-tab" tabindex="0">
                            <ul class="list-group">
                                <c:forEach var="participant" items="${event.participants}">
                                    <li class="list-group-item list-group-item-info">
                                        <c:out value="${participant.firstName} ${participant.lastName} (${participant.email})" />
                                    </li>
                                </c:forEach>
                            </ul>
                        </div>
                    </c:if>
                </div>

            </div>

        </main>

        <jsp:include page="/WEB-INF/templates/_footer.jsp"/>
        <jsp:include page="/WEB-INF/templates/_scripts.jsp"/>

        <script>
            $(document).ready(() => {
                const tooltipTriggerList = document.querySelectorAll('[data-bs-toggle="tooltip"]');
                const tooltipList = [...tooltipTriggerList].map(tooltipTriggerEl => new bootstrap.Tooltip(tooltipTriggerEl));
            });
        </script>
    </body>
</html>