<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set var="currentUser" value="${sessionScope.user}" />


    <jsp:include page="/WEB-INF/templates/header.jsp"/>

        <div class="container">
            <nav aria-label="breadcrumb">
                <ol class="breadcrumb">
                    <li class="breadcrumb-item"><a href="${contextPath}/home">Home</a></li>
                    <li class="breadcrumb-item"><a href="${contextPath}/event/view?eventID=${report.event.id}">${report.event.name}</a></li>
                    <li class="breadcrumb-item active" aria-current="page">${report.topic}</li>
                </ol>
            </nav>
            <h3>${report.topic}</h3>
            <h4>Status: ${report.status.name}</h4>
            <h5>Speaker: 
             <c:choose>
                <c:when test="${report.speaker.id != 0}">
                    ${report.speaker.firstName} ${report.speaker.lastName}
                </c:when>
                <c:otherwise>
                    No speaker
                </c:otherwise>
            </c:choose>
            </h5>
            
            <section class="py-3 text-center container">
                <div class="d-flex flex-row">
                    <div class="d-grid gap-2 d-md-block">
                        <c:if test="${not empty currentUser and (currentUser.role == 'MODERATOR' 
                                    or (currentUser.role == 'SPEAKER' and currentUser.id == report.speaker.id and report.status == 'OFFERED_BY_SPEAKER'))}">
                            <a role="button" href="edit?reportID=${report.id}" class="btn btn-secondary">Edit</a>
                        </c:if>
                    </div>
                    <c:if test="${currentUser.role == 'SPEAKER'}">
                        <div class="btn-group ms-3">
                            <c:choose>
                                <c:when test="${report.status == 'OFFERED_BY_SPEAKER'}">
                                    <a role="button" href="${contextPath}/speaker-report-action?reportID=${report.id}&action=cancel-offer-speaker" 
                                        class="btn btn btn-outline-danger"
                                        data-bs-toggle="tooltip" data-bs-title="Cancel your report offer">Cancel offer</a>
                                </c:when>
                                <c:when test="${report.status == 'PROPOSE_TO_SPEAKER'}">
                                    <a role="button" href="${contextPath}/speaker-report-action?reportID=${report.id}&action=accept-propose-speaker" 
                                        class="btn btn btn-success"
                                        data-bs-toggle="tooltip" data-bs-title="Accept to be a speaker">Accept</a>
                                    <a role="button" href="${contextPath}/speaker-report-action?reportID=${report.id}&action=cancel-propose-speaker" 
                                        class="btn btn btn-danger"
                                        data-bs-toggle="tooltip" data-bs-title="Refuse to be a speaker">Reject</a>
                                </c:when>
                                <c:when test="${report.status == 'SUGGESTED_SPEAKER'}">
                                    <a role="button" href="${contextPath}/speaker-report-action?reportID=${report.id}&action=cancel-suggestion-speaker" 
                                        class="btn btn btn-outline-danger"
                                        data-bs-toggle="tooltip" data-bs-title="Cancel suggestion to be a speaker">Cancel suggestion</a>
                                </c:when>
                                <c:when test="${report.status == 'FREE'}">
                                    <a role="button" href="${contextPath}/speaker-report-action?reportID=${report.id}&action=make-suggestion-speaker" 
                                        class="btn btn btn-info"
                                        data-bs-toggle="tooltip" data-bs-title="Make suggestion to be a speaker">Make suggestion</a>
                                </c:when>
                            </c:choose>
                        </div>
                    </c:if>
                    <c:if test="${currentUser.role == 'MODERATOR'}">
                        <div class="btn-group ms-3">
                            <c:choose>
                                <c:when test="${report.status == 'OFFERED_BY_SPEAKER'}">
                                    <a role="button" href="${contextPath}/moderator-report-action?reportID=${report.id}&action=accept-offer-moderator" 
                                        class="btn btn btn-success"
                                        data-bs-toggle="tooltip" data-bs-title="Accept a proposed speaker's report">Accept offer</a>
                                    <a role="button" href="${contextPath}/moderator-report-action?reportID=${report.id}&action=deny-offer-moderator" 
                                        class="btn btn btn-danger"
                                        data-bs-toggle="tooltip" data-bs-title="Reject a proposed speaker's report">Deny offer</a>
                                </c:when>
                                <c:when test="${report.status == 'SUGGESTED_SPEAKER'}">
                                    <a role="button" href="${contextPath}/moderator-report-action?reportID=${report.id}&action=accept-suggestion-moderator" 
                                        class="btn btn btn-success"
                                        data-bs-toggle="tooltip" data-bs-title="Accept speaker suggestion">Accept suggestion</a>
                                    <a role="button" href="${contextPath}/moderator-report-action?reportID=${report.id}&action=deny-suggestion-moderator" 
                                        class="btn btn btn-danger"
                                        data-bs-toggle="tooltip" data-bs-title="Cancel speaker suggestion">Cancel suggestion</a>
                                </c:when>
                            </c:choose>
                        </div>
                    </c:if>
                </div>
            </section>

            <h4>Description</h4>
            <p class="mt-5">${not empty report.description ? report.description : 'no description'}</p>

        </div>   

        <script>
            $(document).ready(() => {
                const tooltipTriggerList = document.querySelectorAll('[data-bs-toggle="tooltip"]');
                const tooltipList = [...tooltipTriggerList].map(tooltipTriggerEl => new bootstrap.Tooltip(tooltipTriggerEl));
            });
        </script>

    <jsp:include page="/WEB-INF/templates/footer.jsp"/>