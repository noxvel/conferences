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
            <h2 class="mb-3"><fmt:message key="user-list.header.text"/></h2>

            <%-- <section class="py-3 text-center container">
                <div class="d-flex flex-row">
                    <div class="ms-2">
                        <form action="${contextPath}/pages/report/list-filter" method="post">
                            <input type="hidden" name="redirectPath" value="/report/list"/>
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
            </section> --%>

            <div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-3">

                <table class="table table-striped">
                    <thead>
                        <tr>
                            <th scope="col">First name</th>
                            <th scope="col">Last name</th>
                            <th scope="col">Email</th>
                            <th scope="col">Role</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="user" items="${users}">
                            <tr>
                                <td><c:out value="${user.firstName}"/></td>
                                <td><c:out value="${user.lastName}"/></td>
                                <td><c:out value="${user.email}"/></td>
                                <td><c:out value="${user.role.name}"/></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>

            <mytag:pagination arialLabel="User list page navigation" page="${page}" numOfPages="${numOfPages}"
                    linkPath="list" showLimitSelect="true"/>


        </div>

    <jsp:include page="/WEB-INF/templates/footer.jsp"/>