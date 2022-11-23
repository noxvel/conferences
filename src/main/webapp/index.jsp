
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

    <jsp:include page="header.jsp"/>

    <main>

        <section class="py-3 text-center container">
            <div class="input-group">
              <select class="form-select" id="inputGroupSelect04" aria-label="Example select with button addon">
                <option selected value="date" >by date</option>
                <option value="reports">by number of reports</option>
                <option value="participants">by number of participants</option>
              </select>
              <button class="btn btn-outline-secondary" type="button">Sort</button>
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
                                            <a role="button" href="event?eventID=${event.id}" class="btn btn-sm btn-outline-primary">View</a>
                                            <a role="button" href="#" class="btn btn-sm btn-outline-secondary">Edit</a>
                                        </div>
                                        <small class="text-muted">${event.participantsCame} participants</small>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                    
                </div>
            </div>
        </div>

    </main>
    
    <jsp:include page="footer.jsp"/>