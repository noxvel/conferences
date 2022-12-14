<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set var="currentUser" value="${sessionScope.user}" />


    <jsp:include page="/WEB-INF/templates/header.jsp"/>

        <div class="container">
            <nav aria-label="breadcrumb">
                <ol class="breadcrumb">
                    <li class="breadcrumb-item"><a href="${contextPath}/home">Home</a></li>
                    <li class="breadcrumb-item"><a href="${contextPath}/event/view?eventID=${report.event.id}">${report.event.name}</a></li>
                    <li class="breadcrumb-item active" aria-current="page">${report.topic}</li>
                </ol>
            </nav>
            <h3>${report.topic}</h3>
            <h4>Status: ${report.status.name}</h4>
            <h5>Speaker: 
             <c:choose>
                <c:when test="${report.speaker.id != 0}">
                    ${report.speaker.firstName} ${report.speaker.lastName}
                </c:when>
                <c:otherwise>
                    No speaker
                </c:otherwise>
            </c:choose>
            </h5>
            
            <section class="py-3 text-center container">
                <div class="d-flex flex-row">
                    <div class="d-grid gap-2 d-md-block">
                        <c:if test="${not empty currentUser and (currentUser.role == 'MODERATOR' or (currentUser.role == 'SPEAKER' and currentUser.id == report.speaker.id))}">
                            <a role="button" href="edit?reportID=${report.id}" class="btn btn-secondary">Edit</a>
                        </c:if>
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

            <p class="mt-5">${not empty report.description ? report.description : 'no description'}</p>

        </div>   


    <jsp:include page="/WEB-INF/templates/footer.jsp"/>