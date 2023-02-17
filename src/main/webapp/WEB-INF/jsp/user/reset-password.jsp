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

                    <h2 class="fw-normal mb-3 pb-3 text-center"><fmt:message key="change-password.header.text"/></h2>      
                    <form class="needs-validation" novalidate id="changePassword" action="reset-password" method="post">
                        <c:if test="${requestScope.message != null}">
                            <div class="alert alert-danger" role="alert">
                                ${requestScope.message}
                            </div>
                        </c:if>

                        <input type="hidden" id="reset-token" name="token" value="${token}" />

                        <!-- New password input -->
                        <div class="form-outline mb-4">
                            <label class="form-label" for="password"><fmt:message key="change-password.new-password.label"/></label>
                            <input name="newPassword" type="password" id="newPassword" class="form-control" autoComplete="off" pattern=".{3,60}" required />
                            <div class="invalid-feedback">
                                <fmt:message key="change-password.new-password.bad-feedback"/>
                            </div>
                        </div>

                        <!-- Confirm new password input -->
                        <div class="form-outline mb-4">
                            <label class="form-label" for="password"><fmt:message key="change-password.conf-new-pass.label"/></label>
                            <input name="confirmPassword" type="password" id="confirmPassword" class="form-control" autoComplete="off" required />
                            <div class="invalid-feedback">
                                <fmt:message key="change-password.conf-new-pass.bad-feedback"/>
                            </div>
                        </div>

                        <!-- Submit button -->
                        <div class="d-grid gap-2">
                            <button type="submit" class="btn btn-primary btn-block mb-4">
                                <fmt:message key="change-password.button.update-password"/>
                            </button>
                        </div>

                    </form>
                </div>
            </section>

        </main>

        <jsp:include page="/WEB-INF/templates/_footer.jsp"/>
        <jsp:include page="/WEB-INF/templates/_scripts.jsp"/>

        <script>
            $(document).ready(function() {

                $("#changePassword").submit(function( event ) {

                    const password = document.querySelector('#newPassword');
                    const confirm = document.querySelector('#confirmPassword');
                    if (confirm.value === password.value) {
                        confirm.setCustomValidity('');
                    } else {
                        confirm.setCustomValidity('Passwords do not match');
                    }

                    if (!event.target.checkValidity()) {
                        event.preventDefault()
                        event.stopPropagation()
                    }
                    event.target.classList.add('was-validated')
                });
            });
        </script>
        
    </body>
</html>
