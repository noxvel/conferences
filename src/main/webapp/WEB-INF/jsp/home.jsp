
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- <%@ taglib prefix="my" tagdir="/WEB-INF/tags" %> --%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set var="isModerator" value="${not empty sessionScope.user and sessionScope.user.role == 'MODERATOR'}" />
<c:set var="isSpeaker" value="${not empty sessionScope.user and sessionScope.user.role == 'SPEAKER'}" />

    <jsp:include page="/WEB-INF/templates/header.jsp"/>

    <main>

        <section class="py-3 text-center container">
            <div class="d-flex flex-row align-items-center">
                <div class="d-grid gap-2 d-md-block">
                    <c:if test="${isModerator}">
                        <a role="button" href="event/create" class="btn btn-success">Create new event</a>
                    </c:if>
                </div>
                <div class="ms-3 p-2 bg-info bg-opacity-10 border border-info rounded">
                    <form class="row row-cols-lg-auto g-3 align-items-center" action="event-list-sort" method="post">
                        <div class="col-12">
                            <select name="sortType" class="form-select" id="sortTypeSelect" aria-label="sort type select">
                                <option ${sortType == 'Date' ? 'selected' : ''} value="Date" >by date</option>
                                <option ${sortType == 'ReportsCount' ? 'selected' : ''} value="ReportsCount">by number of reports</option>
                                <option ${sortType == 'ParticipantsCount' ? 'selected' : ''} value="ParticipantsCount">by number of participants</option>
                            </select>
                        </div>
                        <div class="col-12">
                            <select name="sortDirection" class="form-select" id="sortDirectionSelect" aria-label="sort direction select">
                                <option ${sortDirection == 'Ascending' ? 'selected' : ''} value="Ascending">in ascending order</option>
                                <option ${sortDirection == 'Descending' ? 'selected' : ''} value="Descending" >in descending order</option>
                            </select>
                        </div>
                        <div class="col-12">
                            <button class="btn btn-secondary" type="submit">Sort</button>
                        </div>
                    </form>
                </div>
                <c:if test="${isSpeaker}">
                <div class="ms-3 p-2 d-flex align-items-center bg-secondary bg-opacity-10 border border-secondary rounded">
                    <form action="event-list-filter" method="post" class="d-flex flex-row align-items-center">
                        <div class="form-check">
                                <input class="form-check-input" type="checkbox" value="true" name="showInWhichParticipated" 
                                                        id="showInWhichParticipated" ${showSpeakerEventParticipated ? 'checked' : ''}>
                                <label class="form-check-label" for="showInWhichParticipated">
                                    Show events in which you participated
                                </label>
                        </div>
                        <button class="btn btn-warning ms-3" type="submit">Filter</button>
                    </form>
                </div>
                </c:if>
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
                                    <h6 class="card-subtitle mb-2 text-muted">
                                        <fmt:formatDate type="date" value="${event.beginDate}" /> - <fmt:formatDate type="date" value="${event.endDate}" />
                                    </h6>
                                    <p class="card-text">${fn:substring(event.description, 0, 140)}...</p>
                                    <div class="d-flex justify-content-between align-items-center">
                                        <div class="btn-group">
                                            <a role="button" href="event/view?eventID=${event.id}" class="btn btn-sm btn-outline-primary">View</a>
                                            <c:if test="${isModerator}">
                                                <a role="button" href="event/edit?eventID=${event.id}" class="btn btn-sm btn-outline-secondary">Edit</a>
                                            </c:if>
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

    <script>

        // $(document).ready(() => {
        //     $("filterSpeakerParticipated").submit((event) => {
        //         event.preventDefault();
        //         let elSpeakerPrticipated = $("#showInWhichParticipated");
        //         $.post("filterBySpeakerParticipated", { showInWhichParticipated: elSpeakerPrticipated.checked });
        //         $("#showInWhichParticipated").click(( event ) => {
        //             $.post("filterBySpeakerParticipated", { showInWhichParticipated: event.target.checked });
        //             $.get("home");
        //         });
        //     })
        // });

    </script>
    
    <jsp:include page="/WEB-INF/templates/footer.jsp"/>