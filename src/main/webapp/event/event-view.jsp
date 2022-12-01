<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />


    <jsp:include page="/header.jsp"/>

        <div class="container d-flex flex-column">

            <h3>${event.name}</h3>
            <h4>${event.place}</h4>
            <h5>${event.beginDate} - ${event.endDate}</h5>

            <section class="py-3 text-center container">
                <div class="d-flex flex-row">
                    <div class="d-grid gap-2 d-md-block">
                        <a role="button" href="report/create" class="btn btn-success">Create new report</a>
                        <%-- <button class="btn btn-success" type="button">Create new event</button> --%>
                        <button class="btn btn-primary" type="button">Button</button>
                    </div>
                    <%-- <div class="ms-2">
                        <form action="home" method="get">
                            <div class="input-group">
                            <select name="orderType" class="form-select" id="inputGroupSelect04" aria-label="Example select with button addon">
                                <option ${orderType == 'Date' ? 'selected' : ''} value="Date" >by date</option>
                                <option ${orderType == 'ReportsCount' ? 'selected' : ''} value="ReportsCount">by number of reports</option>
                                <option ${orderType == 'ParticipantsCount' ? 'selected' : ''} value="ParticipantsCount">by number of participants</option>
                            </select>
                            <button class="btn btn-outline-secondary" type="submit">Sort</button>
                            </div>
                        </form>
                    </div> --%>
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
                                        <a role="button" href="report/edit?reportID=${report.id}" class="btn btn-sm btn-outline-secondary">Edit</a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>
                
            </div>


        </div>

    <jsp:include page="/footer.jsp"/>