<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />


    <jsp:include page="header.jsp"/>

        <div class="container">

            <a href="event?eventID=${report.event.id}">
                <h4>${report.event.name}</h4>
            </a>
            <h3>${report.topic}</h3>
            <h4>${report.status}</h4>
            <h5>${report.speaker.firstName} ${report.speaker.lastName}</h5>

        </div>


    <jsp:include page="footer.jsp"/>