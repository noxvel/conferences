
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%-- <%@ taglib prefix="my" tagdir="/WEB-INF/tags" %> --%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />
    <jsp:include page="header.jsp"/>

    <main>

        <section class="py-3 text-center container">
            <div class="d-flex flex-row">
                <div class="d-grid gap-2 d-md-block">
                    <a role="button" href="event/create" class="btn btn-success">Create new event</a>
                    <%-- <button class="btn btn-success" type="button">Create new event</button> --%>
                    <button class="btn btn-primary" type="button">Button</button>
                </div>
                <div class="ms-2">
                    <form action="home" method="get">
                        <div class="input-group">
                        <select name="orderType" class="form-select" id="sortTypeSelect" aria-label="sort type select">
                            <option ${orderType == 'Date' ? 'selected' : ''} value="Date" >by date</option>
                            <option ${orderType == 'ReportsCount' ? 'selected' : ''} value="ReportsCount">by number of reports</option>
                            <option ${orderType == 'ParticipantsCount' ? 'selected' : ''} value="ParticipantsCount">by number of participants</option>
                        </select>
                        <button class="btn btn-outline-secondary" type="submit">Sort</button>
                        </div>
                    </form>
                </div>
            </div>
        </section>

        <div class="album py-5 bg-light">
            <div class="container">
                <div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-3">

                    <c:forEach var="event" items="${events}">
                        <div class="col">
                            <div class="card shadow-sm">
                                <div class="card-body">
                                    <h5 class="card-title">${event.name}</h5>
                                    <h6 class="card-subtitle mb-2 text-muted">${event.place}</h6>
                                    <h6 class="card-subtitle mb-2 text-muted">${event.beginDate} - ${event.endDate}</h6>
                                    <p class="card-text">This is a wider card with supporting text below as a natural lead-in to additional content. This content is a little bit longer.</p>
                                    <div class="d-flex justify-content-between align-items-center">
                                        <div class="btn-group">
                                            <a role="button" href="event/view?eventID=${event.id}" class="btn btn-sm btn-outline-primary">View</a>
                                            <a role="button" href="event/edit?eventID=${event.id}" class="btn btn-sm btn-outline-secondary">Edit</a>
                                        </div>
                                        <small class="text-muted">${event.reportsCount} reports</small>
                                        <small class="text-muted">${event.participantsCount} participants</small>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                    
                 
                </div>
                <nav aria-label="Home page navigation" class="p-3">
                    <%-- <fmt:formatNumber var="numOfPages" value="${(eventCount/limit)}" maxFractionDigits="0" />  --%>
                    <ul class="pagination justify-content-center">
                        <li class="${(page == 1) ? 'page-item disabled' : 'page-item'}">
                            <a class="page-link" href="home?page=${page == 1 ? page : page - 1}"><span aria-hidden="true">&laquo;</span></a>
                        </li>

                        <c:forEach begin="1" end="${numOfPages}" varStatus="loop">
                            <c:if test="${loop.index > page - 5 && (loop.index < page + 5)}">
                                <li class="${(loop.index == page) ? 'page-item active' : 'page-item'}">
                                    <a class="page-link" href="home?page=${loop.index}">${loop.index}</a>
                                </li>
                            </c:if>
                        </c:forEach>

                        <li class="${numOfPages == page ? 'page-item disabled' : 'page-item'}">
                            <a class="page-link" href="home?page=${numOfPages == page ? page : page + 1}"><span aria-hidden="true">&raquo;</span></a>
                        </li>

                    </ul>
                </nav>
            </div>
        </div>

    </main>
    
    <jsp:include page="footer.jsp"/>