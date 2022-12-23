<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set var="isSpeaker" value="${sessionScope.userRole == 'SPEAKER'}" />


    <jsp:include page="/WEB-INF/templates/header.jsp"/>

        <div class="container d-flex flex-column">
            <h2 class="mb-3">Your list of reports</h2>

            <section class="py-3 text-center container">
                <div class="d-flex flex-row">
                    <div class="ms-2">
                        <form action="${contextPath}/report-list-filter" method="post">
                            <input type="hidden" name="redirectPath" value="speaker-report-list"/>
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

            <div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-3">

                <c:forEach var="report" items="${reports}">
                    <c:choose>
                        <c:when test="${report.status == 'OFFERED_BY_SPEAKER'}">
                            <c:set var="reportColor" value="border-info"/>
                        </c:when>
                        <c:when test="${report.status == 'PROPOSE_TO_SPEAKER'}">
                            <c:set var="reportColor" value="border-primary"/>
                        </c:when>
                        <c:when test="${report.status == 'SUGGESTED_SPEAKER'}">
                            <c:set var="reportColor" value="border-warning"/>
                        </c:when>
                        <c:when test="${report.status == 'CONFIRMED'}">
                            <c:set var="reportColor" value="border-success"/>
                        </c:when>
                        <c:when test="${report.status == 'CANCELED'}">
                            <c:set var="reportColor" value="border-danger"/>
                        </c:when>
                        <c:otherwise>
                            <c:set var="reportColor" value=""/>
                        </c:otherwise>
                    </c:choose>
                    <div class="col">
                        <div class="card shadow-sm ${reportColor}">
                            <div class="card-body">
                                <h5 class="card-title">${report.topic}</h5>
                                <h6 class="card-subtitle mb-2 text-muted">${report.status.name}</h6>
                                <p class="card-text">${fn:substring(report.description, 0, 140)}...</p>
                                <div class="d-flex justify-content-between align-items-center">
                                    <a role="button" href="report/view?reportID=${report.id}" class="btn btn-sm btn-outline-primary">View</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>
                
            </div>

            <nav aria-label="Reports list page navigation" class="p-3">
                <ul class="pagination justify-content-center">
                    <li class="${(page == 1) ? 'page-item disabled' : 'page-item'}">
                        <a class="page-link" href="speaker-report-list?page=${page == 1 ? page : page - 1}"><span aria-hidden="true">&laquo;</span></a>
                    </li>

                    <c:forEach begin="1" end="${numOfPages}" varStatus="loop">
                        <c:if test="${loop.index > page - 5 && (loop.index < page + 5)}">
                            <li class="${(loop.index == page) ? 'page-item active' : 'page-item'}">
                                <a class="page-link" href="speaker-report-list?page=${loop.index}">${loop.index}</a>
                            </li>
                        </c:if>
                    </c:forEach>

                    <li class="${numOfPages == page ? 'page-item disabled' : 'page-item'}">
                        <a class="page-link" href="speaker-report-list?page=${numOfPages == page ? page : page + 1}"><span aria-hidden="true">&raquo;</span></a>
                    </li>

                </ul>
            </nav>

        </div>

    <jsp:include page="/WEB-INF/templates/footer.jsp"/>