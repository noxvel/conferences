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

            <section class="w-100 p-4 d-flex justify-content-center pb-4">
                <div class="d-flex flex-column">
                    <c:if test="${requestScope.message != null}">
                        <div class="alert ${'alert-' += requestScope.message.type} text-center" role="alert">
                            <fmt:message key="${requestScope.message.msgKey}"/>
                        </div>
                    </c:if>
                </div>
            </section>

        </main>

        <jsp:include page="/WEB-INF/templates/_footer.jsp"/>
        <jsp:include page="/WEB-INF/templates/_scripts.jsp"/>

    </body>
</html>
