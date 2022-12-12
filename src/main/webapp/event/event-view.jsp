<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set var="isSpeaker" value="${not empty sessionScope.user and sessionScope.user.role == 'SPEAKER'}" />


    <jsp:include page="/header.jsp"/>

        <div class="container d-flex flex-column">

            <h3>${event.name}</h3>
            <h4>${event.place}</h4>
            <h5>${event.beginDate} - ${event.endDate}</h5>

            <section class="py-3 text-center container">
                <div class="d-flex flex-row">
                    <div class="d-grid gap-2 d-md-block">
                        <c:if test="${not empty sessionScope.user and (sessionScope.user.role == 'MODERATOR' or sessionScope.user.role == 'SPEAKER')}">
                            <a role="button" href="report/create?eventID=${event.id}" class="btn btn-success">Create new report</a>
                        </c:if>
                        <c:if test="${not empty sessionScope.user and sessionScope.user.role == 'MODERATOR'}">
                            <a role="button" href="edit?eventID=${event.id}" class="btn btn-secondary">Edit</a>
                        </c:if>
                        <c:if test="${isRegister != null}">
                            <a role="button" href="register?eventID=${event.id}&register=${!isRegister}" class="btn btn-primary">
                                <c:out value="${isRegister ? 'Unregister' : 'Register'}"/> to event
                            </a>
                        </c:if>
                    </div> 
                    <%-- <c:if test="${isSpeaker}"> --%>
                        <div class="ms-2">
                            <form action="filter" method="get">
                                <div class="input-group">
                                    <select name="speakerFilter" class="form-select" id="speakerFilter" aria-label="Speaker filter">
                                        <option ${orderType == 'Date' ? 'selected' : ''} value="Date" >by date</option>
                                        <option ${orderType == 'ReportsCount' ? 'selected' : ''} value="ReportsCount">by number of reports</option>
                                        <option ${orderType == 'ParticipantsCount' ? 'selected' : ''} value="ParticipantsCount">by number of participants</option>
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
                                        <a role="button" href="report/view?reportID=${report.id}" class="btn btn-sm btn-outline-primary">View</a>
                                        <c:if test="${not empty sessionScope.user and sessionScope.user.role == 'MODERATOR'}">
                                            <a role="button" href="report/edit?reportID=${report.id}" class="btn btn-sm btn-outline-secondary">Edit</a>
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

    <jsp:include page="/footer.jsp"/>