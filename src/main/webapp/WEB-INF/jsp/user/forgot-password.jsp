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

                    <h2 class="fw-normal mb-3 pb-3 text-center"><fmt:message key="forgot-password.header.text"/></h2>      
                    <form id="loginUser" action="forgot-password" method="post">
                        <c:if test="${requestScope.message != null}">
                            <div class="alert alert-info text-center" role="alert">
                                <fmt:message key="${requestScope.message}"/>
                            </div>
                        </c:if>
                        <!-- Email input -->
                        <div class="form-outline mb-4">
                            <label class="form-label" for="email"><fmt:message key="forgot-password.email.label"/></label>
                            <input name="email" type="email" id="email" class="form-control" autoComplete="off" pattern="^[\w\-\.]+@([\w-]+\.)+[\w-]{2,4}$" required value="moderator@mail.com"/>
                        </div>

                        <!-- Submit button -->
                        <div class="d-grid gap-2">
                            <button type="submit" class="btn btn-warning btn-block mb-4"><fmt:message key="forgot-password.button.send-link"/></button>
                        </div>

                    </form>
                </div>
            </section>

        </main>

        <jsp:include page="/WEB-INF/templates/_footer.jsp"/>
        <jsp:include page="/WEB-INF/templates/_scripts.jsp"/>

    </body>
</html>
