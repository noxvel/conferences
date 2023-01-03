<%@ page contentType="text/html; charset=UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="mytag" tagdir="/WEB-INF/tags" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set var="isSpeaker" value="${sessionScope.userRole == 'SPEAKER'}" />

<fmt:setLocale value="${lang}" />
<fmt:setBundle basename="text" />

    <jsp:include page="/WEB-INF/templates/header.jsp"/>

        <div class="container d-flex flex-column">
            <h2 class="mb-3"><fmt:message key="report-list.header.text"/></h2>

            <section class="py-3 text-center container">
                <div class="d-flex flex-row">
                    <div class="ms-2">
                        <form action="${contextPath}/pages/report/list-filter" method="post">
                            <input type="hidden" name="redirectPath" value="report/list"/>
                            <div class="input-group">
                                <select name="reportStatusFilter" class="form-select" id="statusFilter" aria-label="Status filter">
                                    <option ${empty reportStatusFilter ? 'selected' : ''} value=""><fmt:message key="report-list.filter.status.all"/></option>
                                    <c:forEach var="status" items="${reportStatuses}">
                                        <option ${reportStatusFilter == status ? 'selected' : ''} value="${status}">
                                            <mytag:report-status-i18n reportStatus="${status}"/>
                                        </option>
                                    </c:forEach>
                                </select>
                                <button class="btn btn-primary" type="submit"><fmt:message key="report-list.filter.button"/></button>
                            </div>
                        </form>
                    </div>
                </div>
            </section>

            <div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-3">

                <c:forEach var="report" items="${reports}">
                    <mytag:report-status-color reportStatus="${report.status}"/>
                    
                    <div class="col">
                        <div class="card shadow-md ${empty reportColor ? '' : 'border-' += reportColor}">
                            <div class="card-body">
                                <h5 class="card-title">${report.topic}</h5>
                                <h6 class="card-subtitle mb-2 ${empty reportColor ? '' : 'text-' += reportColor}">
                                    <mytag:report-status-i18n reportStatus="${report.status}"/>
                                </h6>
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
                                <p class="card-text">${fn:substring(report.description, 0, 140)}...</p>
                                <div class="d-flex justify-content-between align-items-center">
                                    <a role="button" href="view?reportID=${report.id}" class="btn btn-sm btn-outline-primary">
                                        <fmt:message key="report-list.report.button.view"/>
                                    </a>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>
                
            </div>

            <nav aria-label="Reports list page navigation" class="p-3">
                <ul class="pagination justify-content-center">
                    <li class="${(page == 1) ? 'page-item disabled' : 'page-item'}">
                        <a class="page-link" href="list?page=${page == 1 ? page : page - 1}"><span aria-hidden="true">&laquo;</span></a>
                    </li>

                    <c:forEach begin="1" end="${numOfPages}" varStatus="loop">
                        <c:if test="${loop.index > page - 5 && (loop.index < page + 5)}">
                            <li class="${(loop.index == page) ? 'page-item active' : 'page-item'}">
                                <a class="page-link" href="list?page=${loop.index}">${loop.index}</a>
                            </li>
                        </c:if>
                    </c:forEach>

                    <li class="${numOfPages == page ? 'page-item disabled' : 'page-item'}">
                        <a class="page-link" href="list?page=${numOfPages == page ? page : page + 1}"><span aria-hidden="true">&raquo;</span></a>
                    </li>

                </ul>
            </nav>

        </div>

    <jsp:include page="/WEB-INF/templates/footer.jsp"/>