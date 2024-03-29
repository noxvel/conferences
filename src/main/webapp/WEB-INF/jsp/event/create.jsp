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

            <div class="d-flex flex-column">

                <form id="createEvent" class="row g-3" action="create" method="post">
                    <div class="col-12">
                        <label for="name" class="form-label"><fmt:message key="event-create.name.label"/></label>
                        <input name="name" type="text" class="form-control" id="name" placeholder="" value="Test Event" required>
                    </div>
                    <div class="col-12">
                        <label for="place" class="form-label"><fmt:message key="event-create.place.label"/></label>
                        <input name="place" type="text" class="form-control" id="place" placeholder="New York..." value="New Place" required>
                    </div>
                    <div class="col-md-6">
                        <label for="beginDate" class="form-label"><fmt:message key="event-create.begin-date.label"/></label>
                        <input name="beginDate" type="datetime-local" class="form-control" id="beginDate" value="2022-01-01T00:00" 
                                            min="2020-01-01T00:00" max="2100-01-01T00:00" required>
                    </div>
                    <div class="col-md-6">
                        <label for="endDate" class="form-label"><fmt:message key="event-create.end-date.label"/></label>
                        <input name="endDate" type="datetime-local" class="form-control" id="endDate" value="2022-01-01T00:00" 
                                            min="2020-01-01T00:00" max="2100-01-01T00:00" required>
                    </div>
                    <div class="input-group">
                        <span class="input-group-text"><fmt:message key="event-create.description.label"/></span>
                        <fmt:message key="event-create.description.placeholder" var="descriptionPlaceholder"/>
                        <textarea name="description" class="form-control" rows="10" aria-label="Description" placeholder="${descriptionPlaceholder}"></textarea>
                    </div>
                    <%-- <input type="hidden" id="timezone" name="timezone" value="-08:00" /> --%>
                    <div class="col-12">
                        <a role="button" href="${contextPath}/pages/home" class="btn btn-secondary">
                            <fmt:message key="event-create.button.back"/>
                        </a>
                        <button type="submit" class="btn btn-primary">
                            <fmt:message key="event-create.button.create"/>
                        </button>
                    </div>
                </form>

            </div>

        </main>

        <jsp:include page="/WEB-INF/templates/_footer.jsp"/>
        <jsp:include page="/WEB-INF/templates/_scripts.jsp"/>
    </body>
</html>