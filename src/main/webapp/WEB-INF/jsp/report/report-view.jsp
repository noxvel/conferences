<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="mytag" tagdir="/WEB-INF/tags" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set var="currentUser" value="${sessionScope.user}" />

<fmt:setLocale value="${lang}" />
<fmt:setBundle basename="text" />

    <jsp:include page="/WEB-INF/templates/header.jsp"/>

        <div class="container">
            <nav aria-label="breadcrumb">
                <ol class="breadcrumb">
                    <li class="breadcrumb-item"><a href="${contextPath}/home"><fmt:message key="report-view.breadcrumb-home-text"/></a></li>
                    <li class="breadcrumb-item"><a href="${contextPath}/event/view?eventID=${report.event.id}">${report.event.name}</a></li>
                    <li class="breadcrumb-item active" aria-current="page">${report.topic}</li>
                </ol>
            </nav>
            <div class="border-bottom border-dark">
                <h3>${report.topic}</h3>
            </div>

            <section class="py-3">
                <c:if test="${userRole == 'MODERATOR' or userRole == 'SPEAKER'}">
                    <mytag:report-status-color reportStatus="${report.status}"/>
                    <div>
                        <h4 class="${empty reportColor ? '' : 'text-' += reportColor}">
                            <mytag:report-status-i18n reportStatus="${report.status}"/>
                        </h4>
                    </div>
                    <div class="d-flex flex-row align-items-center p-2 border border-success rounded-3">
                        <div class="fs-5 me-2 "><fmt:message key="report-view.text.available-actions"/>: </div>
                        <div class="d-grid gap-2 d-md-block">
                            <c:if test="${not empty currentUser and (currentUser.role == 'MODERATOR' 
                                        or (currentUser.role == 'SPEAKER' and currentUser.id == report.speaker.id and report.status == 'OFFERED_BY_SPEAKER'))}">
                                <a role="button" href="edit?reportID=${report.id}" class="btn btn-secondary">
                                    <fmt:message key="report-view.button.edit"/>
                                </a>
                            </c:if>
                        </div>
                        <c:if test="${currentUser.role == 'SPEAKER'}">
                            <div class="btn-group ms-3">
                                <c:choose>
                                    <c:when test="${report.status == 'OFFERED_BY_SPEAKER'}">
                                        <fmt:message key="report-view.speaker.tooltip.cancel-offer" var="tooltipSpeakerCancelOffer"/>
                                        <a role="button" href="${contextPath}/speaker-report-action?reportID=${report.id}&action=cancel-offer-speaker" 
                                            class="btn btn btn-outline-danger"
                                            data-bs-toggle="tooltip" data-bs-title="${tooltipSpeakerCancelOffer}">
                                            <fmt:message key="report-view.speaker.button.cancel-offer"/>
                                        </a>
                                    </c:when>
                                    <c:when test="${report.status == 'PROPOSE_TO_SPEAKER'}">
                                        <fmt:message key="report-view.speaker.tooltip.accept" var="tooltipSpeakerAccept"/>
                                        <a role="button" href="${contextPath}/speaker-report-action?reportID=${report.id}&action=accept-propose-speaker" 
                                            class="btn btn btn-success"
                                            data-bs-toggle="tooltip" data-bs-title="${tooltipSpeakerAccept}">
                                            <fmt:message key="report-view.speaker.button.accept"/>
                                        </a>
                                        <fmt:message key="report-view.speaker.tooltip.reject" var="tooltipSpeakerReject"/>
                                        <a role="button" href="${contextPath}/speaker-report-action?reportID=${report.id}&action=cancel-propose-speaker" 
                                            class="btn btn btn-danger"
                                            data-bs-toggle="tooltip" data-bs-title="${tooltipSpeakerReject}">
                                            <fmt:message key="report-view.speaker.button.reject"/>
                                        </a>
                                    </c:when>
                                    <c:when test="${report.status == 'SUGGESTED_SPEAKER'}">
                                        <fmt:message key="report-view.speaker.tooltip.cancel-suggestion" var="tooltipSpeakerCancelSuggestion"/>
                                        <a role="button" href="${contextPath}/speaker-report-action?reportID=${report.id}&action=cancel-suggestion-speaker" 
                                            class="btn btn btn-outline-danger"
                                            data-bs-toggle="tooltip" data-bs-title="${tooltipSpeakerCancelSuggestion}">
                                            <fmt:message key="report-view.speaker.button.cancel-suggestion"/>
                                        </a>
                                    </c:when>
                                    <c:when test="${report.status == 'FREE'}">
                                        <fmt:message key="report-view.speaker.tooltip.make-suggestion" var="tooltipSpeakerMakeSuggestion"/>
                                        <a role="button" href="${contextPath}/speaker-report-action?reportID=${report.id}&action=make-suggestion-speaker" 
                                            class="btn btn btn-info"
                                            data-bs-toggle="tooltip" data-bs-title="${tooltipSpeakerMakeSuggestion}">
                                            <fmt:message key="report-view.speaker.button.make-suggestion"/>
                                        </a>
                                    </c:when>
                                </c:choose>
                            </div>
                        </c:if>
                        <c:if test="${currentUser.role == 'MODERATOR'}">
                            <div class="btn-group ms-3">
                                <c:choose>
                                    <c:when test="${report.status == 'OFFERED_BY_SPEAKER'}">
                                        <fmt:message key="report-view.moderator.tooltip.accept-offer" var="tooltipModeratorAcceptOffer"/>
                                        <a role="button" href="${contextPath}/moderator-report-action?reportID=${report.id}&action=accept-offer-moderator" 
                                            class="btn btn btn-success"
                                            data-bs-toggle="tooltip" data-bs-title="${tooltipModeratorAcceptOffer}">
                                            <fmt:message key="report-view.moderator.button.accept-offer"/>
                                        </a>
                                        <fmt:message key="report-view.moderator.tooltip.deny-offer" var="tooltipModeratorDenyOffer"/>
                                        <a role="button" href="${contextPath}/moderator-report-action?reportID=${report.id}&action=deny-offer-moderator" 
                                            class="btn btn btn-danger"
                                            data-bs-toggle="tooltip" data-bs-title="${tooltipModeratorDenyOffer}">
                                            <fmt:message key="report-view.moderator.button.deny-offer"/>
                                        </a>
                                    </c:when>
                                    <c:when test="${report.status == 'SUGGESTED_SPEAKER'}">
                                        <fmt:message key="report-view.moderator.tooltip.accept-suggestion" var="tooltipModeratorAcceptSuggestion"/>
                                        <a role="button" href="${contextPath}/moderator-report-action?reportID=${report.id}&action=accept-suggestion-moderator" 
                                            class="btn btn btn-success"
                                            data-bs-toggle="tooltip" data-bs-title="${tooltipModeratorAcceptSuggestion}">
                                            <fmt:message key="report-view.moderator.button.accept-suggestion"/>
                                        </a>
                                        <fmt:message key="report-view.moderator.tooltip.cancel-suggestion" var="tooltipModeratorCancelSuggestion"/>
                                        <a role="button" href="${contextPath}/moderator-report-action?reportID=${report.id}&action=deny-suggestion-moderator" 
                                            class="btn btn btn-danger"
                                            data-bs-toggle="tooltip" data-bs-title="${tooltipModeratorCancelSuggestion}">
                                            <fmt:message key="report-view.moderator.button.cancel-suggestion"/>
                                        </a>
                                        </a>
                                    </c:when>
                                </c:choose>
                            </div>
                            <c:if test="${report.status == 'CANCELED'}">
                                <div>
                                    <a role="button" href="delete?reportID=${report.id}" class="btn btn-danger">
                                        <fmt:message key="report-view.button.delete"/>
                                    </a>
                                </div>
                            </c:if>
                        </c:if>
                        <c:if test="${report.status != 'CANCELED'}">
                            <div>
                                <fmt:message key="report-view.moderator.tooltip.cancel-report" var="tooltipModeratorCancelReport"/>
                                <a role="button" href="${contextPath}/moderator-report-action?reportID=${report.id}&action=cancel-report-moderator" 
                                    class="btn btn btn-outline-danger ms-2"
                                    data-bs-toggle="tooltip" data-bs-title="${tooltipModeratorCancelReport}">
                                    <fmt:message key="report-view.moderator.button.cancel-report"/>
                                </a>
                            </div>
                        </c:if>
                    </div>
                </c:if>
            </section>

            <div class="mt-3">
                <h5><fmt:message key="report-view.text-speaker"/> 
                <c:choose>
                    <c:when test="${report.speaker != null}">
                        ${report.speaker.firstName} ${report.speaker.lastName}
                    </c:when>
                    <c:otherwise>
                        <fmt:message key="report-view.text.no-speaker"/>
                    </c:otherwise>
                </c:choose>
                </h5>

                <h4 class="mt-3"><fmt:message key="report-view.text.description"/></h4>
                <fmt:message key="report-view.text.no-description" var="textNoDescription"/>
                <p class="mt-1">${not empty report.description ? report.description : textNoDescription}</p>
            </div>

        </div>   

        <script>
            $(document).ready(() => {
                const tooltipTriggerList = document.querySelectorAll('[data-bs-toggle="tooltip"]');
                const tooltipList = [...tooltipTriggerList].map(tooltipTriggerEl => new bootstrap.Tooltip(tooltipTriggerEl));
            });
        </script>

    <jsp:include page="/WEB-INF/templates/footer.jsp"/>