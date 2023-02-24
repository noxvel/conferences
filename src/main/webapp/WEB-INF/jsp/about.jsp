<%@ page contentType="text/html; charset=UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${lang}" />
<fmt:setBundle basename="text" />
<!DOCTYPE html>
<html lang="${lang}">
    <jsp:include page="/WEB-INF/templates/_head.jsp"/>
    <body class="d-flex flex-column min-vh-100">
        <jsp:include page="/WEB-INF/templates/_header.jsp"/>

        <main class="container flex-fill">

            <div class="container d-flex flex-column">
                <h1 class="mb-3"><fmt:message key="about.header.text"/></h2>
                <pre style="font: inherit;"><fmt:message key="about.body.text"/></pre>
            </div>

        </main>
        
        <jsp:include page="/WEB-INF/templates/_footer.jsp"/>
        <jsp:include page="/WEB-INF/templates/_scripts.jsp"/>
    </body>
</html>