<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<fmt:setLocale value="${lang}" />
<fmt:setBundle basename="text" />
<!DOCTYPE html>
<html lang="${lang}">
    <jsp:include page="/WEB-INF/templates/_head.jsp"/>
    <body class="d-flex flex-column min-vh-100">
        <jsp:include page="/WEB-INF/templates/_header.jsp"/>

        <main class="container flex-fill">

            <div class="container d-flex flex-column">

                <form id="createEvent" class="row g-3" action="edit" method="post">
                    <div class="col-12">
                        <label for="name" class="form-label"><fmt:message key="event-edit.name.label"/></label>
                        <input name="name" type="text" class="form-control" id="name" placeholder="" value="${event.name}">
                    </div>
                    <div class="col-12">
                        <label for="place" class="form-label"><fmt:message key="event-edit.place.label"/></label>
                        <input name="place" type="text" class="form-control" id="place" placeholder="New York..." value="${event.place}">
                    </div>
                    <div class="col-md-6">
                        <label for="beginDate" class="form-label"><fmt:message key="event-edit.begin-date.label"/></label>
                        <input name="beginDate" type="datetime-local" class="form-control" id="beginDate" value="${event.beginDate}" min="2020-01-01T00:00" max="2100-01-01T00:00">
                    </div>
                    <div class="col-md-6">
                        <label for="endDate" class="form-label"><fmt:message key="event-edit.end-date.label"/></label>
                        <input name="endDate" type="datetime-local" class="form-control" id="endDate" value="${event.endDate}" min="2020-01-01T00:00" max="2100-01-01T00:00">
                    </div>
                    <div class="input-group">
                        <span class="input-group-text"><fmt:message key="event-edit.description.label"/></span>
                        <textarea name="description" class="form-control" rows="6 aria-label="Description" >${event.description}</textarea>
                    </div>
                    <div class="col-4">
                        <div class="input-group mb-3">
                            <span class="input-group-text"><fmt:message key="event-edit.participants-came.label"/></span>
                            <input name="participantsCame" type="number" min="0" class="form-control" id="participantsCame" value="${event.participantsCame}">
                        </div>
                    </div>
                    
                    <%-- <input type="hidden" id="timezone" name="timezone" value="-08:00" /> --%>
                    <input type="hidden" id="eventID" name="eventID" value="${event.id}" />
                    <div class="col-12">
                        <a role="button" href="${contextPath}/pages/event/view?eventID=${event.id}" class="btn btn-secondary">
                            <fmt:message key="event-edit.button.back"/></a>
                        <button type="submit" class="btn btn-primary">
                            <fmt:message key="event-edit.button.edit"/></button>
                        <div class="ms-3 form-check  form-check-reverse d-inline-block">
                            <label class="form-check-label" for="sendNotification">
                                <fmt:message key="event-edit.send-notification.label"/>
                            </label>
                            <input class="form-check-input" type="checkbox" value="true" name="sendNotification" id="sendNotification">
                        </div>
                    </div>
                </form>

            </div>

        </main>

        <jsp:include page="/WEB-INF/templates/_footer.jsp"/>
        <jsp:include page="/WEB-INF/templates/_scripts.jsp"/>
    </body>
</html>