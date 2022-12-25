<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set var="isSpeaker" value="${sessionScope.userRole == 'SPEAKER'}" />
<c:set var="isModerator" value="${sessionScope.userRole == 'MODERATOR'}" />
<c:set var="isUser" value="${sessionScope.userRole == 'ORDINARY_USER'}" />


    <jsp:include page="/WEB-INF/templates/header.jsp"/>

        <div class="container d-flex flex-column">
            <nav aria-label="breadcrumb">
                <ol class="breadcrumb">
                    <li class="breadcrumb-item"><a href="${contextPath}/home">Home</a></li>
                    <li class="breadcrumb-item active" aria-current="page">${event.name}</li>
                </ol>
            </nav>
            <h3>${event.name}</h3>
            <h4>Location: ${event.place}</h4>
            <h5>Event time: <fmt:formatDate type="date" value="${event.beginDate}" /> - <fmt:formatDate type="date" value="${event.endDate}" /> </h5>

            <section class="py-3 text-center container">
                <div class="d-flex flex-row">
                    <div class="d-grid gap-2 d-md-block">
                        <c:if test="${isModerator}">
                            <a role="button" href="edit?eventID=${event.id}" class="btn btn-secondary">Edit event</a>
                        </c:if>
                        <c:if test="${empty sessionScope.user or isUser}">
                            <form action="register" method="post">
                                <input type="hidden" name="register" value="${!isRegister}"/>
                                <input type="hidden" name="eventID" value="${event.id}"/>
                                <c:if test="${isRegister == null}">
                                    <span class="d-inline-block" tabindex="0" data-bs-toggle="tooltip" data-bs-title="You need to login, before registering for the event">
                                </c:if>
                                    <button ${isRegister == null ? 'disabled' : ''} class="btn btn-${isRegister == true ? 'danger' : 'primary'}" type="submit">
                                        <c:out value="${isRegister ? 'Unregister' : 'Register'}"/> to event
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

            <nav>
                <div class="nav nav-tabs" id="nav-tab-event-view" role="tablist">
                    <button class="nav-link active" id="nav-profile-tab" data-bs-toggle="tab" data-bs-target="#nav-reports" type="button" role="tab" aria-controls="nav-reports" aria-selected="false">Reports</button>
                    <button class="nav-link" id="nav-description-tab" data-bs-toggle="tab" data-bs-target="#nav-description" type="button" role="tab" aria-controls="nav-description" aria-selected="true">Description</button>
                    <c:if test="${isModerator}">
                        <button class="nav-link" id="nav-participants-tab" data-bs-toggle="tab" data-bs-target="#nav-participants" type="button" role="tab" aria-controls="nav-participants" aria-selected="true">Participants</button>
                    </c:if>
                </div>
            </nav>
            <div class="tab-content pt-3" id="nav-tabContent">
                <div class="tab-pane fade show active" id="nav-reports" role="tabpanel" aria-labelledby="nav-reports-tab" tabindex="0">

                    <c:if test="${isSpeaker or isModerator}">
                        <section class="py-3 text-center container">
                            <div class="d-flex flex-row">
                                <a role="button" href="${contextPath}/report/create?eventID=${event.id}" class="btn btn-success">Create new report</a>
                                <div class="ms-2">
                                    <form action="${contextPath}/report-list-filter" method="post">
                                        <input type="hidden" name="redirectPath" value="event/view?eventID=${event.id}"/>
                                        <div class="input-group">
                                            <select name="reportStatusFilter" class="form-select" id="statusFilter" aria-label="Status filter">
                                                <option ${empty reportStatusFilter ? 'selected' : ''} value="">All</option>
                                                <c:forEach var="status" items="${reportStatuses}">
                                                    <option ${reportStatusFilter == status ? 'selected' : ''} value="${status}">${status.name}</option>
                                                </c:forEach>
                                            </select>
                                            <button class="btn btn-primary" type="submit">Filter</button>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </section>
                    </c:if>

                    <div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-3">
                        <c:forEach var="report" items="${event.reports}">
                            <c:set var="reportColor" value=""/>
                            <c:if test="${isModerator or isSpeaker}">
                                <c:choose>
                                    <c:when test="${report.status == 'OFFERED_BY_SPEAKER'}">
                                        <c:set var="reportColor" value="info"/>
                                    </c:when>
                                    <c:when test="${report.status == 'PROPOSE_TO_SPEAKER'}">
                                        <c:set var="reportColor" value="secondary"/>
                                    </c:when>
                                    <c:when test="${report.status == 'SUGGESTED_SPEAKER'}">
                                        <c:set var="reportColor" value="warning"/>
                                    </c:when>
                                    <c:when test="${report.status == 'CONFIRMED'}">
                                        <c:set var="reportColor" value="success"/>
                                    </c:when>
                                    <c:when test="${report.status == 'CANCELED'}">
                                        <c:set var="reportColor" value="danger"/>
                                    </c:when>
                                    <c:when test="${report.status == 'FREE'}">
                                        <c:set var="reportColor" value="primary"/>
                                    </c:when>
                                </c:choose>
                            </c:if>
                            <div class="col">
                                <div class="card shadow-md ${empty reportColor ? '' : 'border-' += reportColor}">
                                    <div class="card-body">
                                        <h5 class="card-title">${report.topic}</h5>
                                        <c:if test="${isModerator or isSpeaker}">
                                            <h6 class="card-subtitle mb-2 text-${reportColor}">${report.status.name}</h6>
                                        </c:if>
                                        <p class="card-text">${fn:substring(report.description, 0, 140)}...</p>
                                        <div class="d-flex justify-content-between align-items-center">
                                            <div class="btn-group">
                                                <a role="button" href="${contextPath}/report/view?reportID=${report.id}" class="btn btn-sm btn-outline-primary">View</a>
                                                <c:if test="${isModerator}">
                                                    <a role="button" href="${contextPath}/report/edit?reportID=${report.id}" class="btn btn-sm btn-outline-secondary">Edit</a>
                                                </c:if>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>

                    <nav aria-label="Event page navigation" class="p-3">
                        <ul class="pagination justify-content-center">
                            <li class="${(page == 1) ? 'page-item disabled' : 'page-item'}">
                                <a class="page-link" href="view?eventID=${event.id}&page=${page == 1 ? page : page - 1}"><span aria-hidden="true">&laquo;</span></a>
                            </li>

                            <c:forEach begin="1" end="${numOfPages}" varStatus="loop">
                                <c:if test="${loop.index > page - 5 && (loop.index < page + 5)}">
                                    <li class="${(loop.index == page) ? 'page-item active' : 'page-item'}">
                                        <a class="page-link" href="view?eventID=${event.id}&page=${loop.index}">${loop.index}</a>
                                    </li>
                                </c:if>
                            </c:forEach>

                            <li class="${numOfPages == page ? 'page-item disabled' : 'page-item'}">
                                <a class="page-link" href="view?eventID=${event.id}&page=${numOfPages == page ? page : page + 1}"><span aria-hidden="true">&raquo;</span></a>
                            </li>

                        </ul>
                    </nav>               
                
                </div>
                <div class="tab-pane fade" id="nav-description" role="tabpanel" aria-labelledby="nav-description-tab" tabindex="0">
                    <c:out value="${event.description}" />
                </div>
                <div class="tab-pane fade" id="nav-participants" role="tabpanel" aria-labelledby="nav-participants-tab" tabindex="0">
                    List of participants
                </div>
            </div>

        </div>

        <script>
            $(document).ready(() => {
                const tooltipTriggerList = document.querySelectorAll('[data-bs-toggle="tooltip"]');
                const tooltipList = [...tooltipTriggerList].map(tooltipTriggerEl => new bootstrap.Tooltip(tooltipTriggerEl));
            });
        </script>

    <jsp:include page="/WEB-INF/templates/footer.jsp"/>