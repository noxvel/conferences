<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />


    <jsp:include page="/header.jsp"/>

        <div class="container">
            <a href="${contextPath}/event/view?eventID=${report.event.id}">
                <h4>${report.event.name}</h4>
            </a>
            <h3>${report.topic}</h3>
            <h4>${report.status}</h4>
            <h5>${report.speaker.firstName} ${report.speaker.lastName}</h5>
            
            <section class="py-3 text-center container">
                <div class="d-flex flex-row">
                    <div class="d-grid gap-2 d-md-block">
                        <a role="button" href="edit?reportID=${report.id}" class="btn btn-secondary">Edit</a>
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

            <p>${report.description}</p>

        </div>   


    <jsp:include page="/footer.jsp"/>