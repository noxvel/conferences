<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set var="isSpeaker" value="${not empty sessionScope.user and sessionScope.user.role == 'SPEAKER'}" />
<c:set var="isModerator" value="${not empty sessionScope.user and sessionScope.user.role == 'MODERATOR'}" />
<c:set var="isUser" value="${not empty sessionScope.user and sessionScope.user.role == 'USER'}" />


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
            <h5>Event time: ${event.beginDate} - ${event.endDate}</h5>

            <section class="py-3 text-center container">
                <div class="d-flex flex-row">
                    <div class="d-grid gap-2 d-md-block">
                        <c:if test="${isModerator or isSpeaker}}">
                            <a role="button" href="${contextPath}/report/create?eventID=${event.id}" class="btn btn-success">Create new report</a>
                        </c:if>
                        <c:if test="${isModerator}">
                            <a role="button" href="edit?eventID=${event.id}" class="btn btn-secondary">Edit</a>
                        </c:if>
                        <c:if test="${empty sessionScope.user or isUser}">
                            <form action="register" method="post">
                                <input type="hidden" name="register" value="${!isRegister}"/>
                                <input type="hidden" name="eventID" value="${event.id}"/>
                                <c:if test="${isRegister == null}">
                                    <span class="d-inline-block" tabindex="0" data-bs-toggle="tooltip" data-bs-title="You need to register before">
                                </c:if>
                                    <button ${isRegister == null ? 'disabled' : ''} class="btn btn-primary" type="submit">
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
                    <%-- <c:if test="${isSpeaker}"> --%>
                        <div class="ms-2">
                            <form action="${contextPath}/report-list-filter" method="post">
                                <input type="hidden" name="eventID" value="${event.id}" />
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
                    <%-- </c:if> --%>
                </div>
            </section>

            <div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-3">

                <c:forEach var="report" items="${event.reports}">
                    <div class="col">
                        <div class="card shadow-sm">
                            <div class="card-body">
                                <h5 class="card-title">${report.topic}</h5>
                                <h6 class="card-subtitle mb-2 text-muted">${report.status}</h6>
                                <p class="card-text">This is a wider card with supporting text below as a natural lead-in to additional content. This content is a little bit longer.</p>
                                <div class="d-flex justify-content-between align-items-center">
                                    <div class="btn-group">
                                        <a role="button" href="${contextPath}/report/view?reportID=${report.id}" class="btn btn-sm btn-outline-primary">View</a>
                                        <c:if test="${not empty sessionScope.user and sessionScope.user.role == 'MODERATOR'}">
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

        <script>
            $(document).ready(() => {
                const tooltipTriggerList = document.querySelectorAll('[data-bs-toggle="tooltip"]');
                const tooltipList = [...tooltipTriggerList].map(tooltipTriggerEl => new bootstrap.Tooltip(tooltipTriggerEl));
            });
        </script>

    <jsp:include page="/WEB-INF/templates/footer.jsp"/>