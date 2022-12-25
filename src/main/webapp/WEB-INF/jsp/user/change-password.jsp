<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

    <jsp:include page="/WEB-INF/templates/header.jsp"/>

        <div class="container">

            <section class="w-100 p-4 d-flex justify-content-center pb-4">
                <div class="d-flex flex-column">

                    <h2 class="fw-normal mb-3 pb-3 text-center">Change password</h2>      
                    <form class="needs-validation" novalidate id="changePassword" action="change-password" method="post">
                        <c:if test="${requestScope.message != null}">
                            <div class="alert alert-danger" role="alert">
                                ${requestScope.message}
                            </div>
                        </c:if>

                        <!-- Current password input -->
                        <div class="form-outline mb-4">
                            <label class="form-label" for="password">Current password</label>
                            <input name="currentPassword" type="password" id="currentPassword" class="form-control" autoComplete="off" pattern=".{3,60}" required />
                            <div class="invalid-feedback">
                                Bad password!
                            </div>
                        </div>

                        <!-- New password input -->
                        <div class="form-outline mb-4">
                            <label class="form-label" for="password">New password</label>
                            <input name="newPassword" type="password" id="newPassword" class="form-control" autoComplete="off" pattern=".{3,60}" required />
                            <div class="invalid-feedback">
                                Bad password!
                            </div>
                        </div>

                        <!-- Confirm new password input -->
                        <div class="form-outline mb-4">
                            <label class="form-label" for="password">Confirm new password</label>
                            <input name="confirmPassword" type="password" id="confirmPassword" class="form-control" autoComplete="off" required />
                            <div class="invalid-feedback">
                                Passwords do not match!
                            </div>
                        </div>

                        <!-- Submit button -->
                        <div class="d-grid gap-2">
                            <button type="submit" class="btn btn-primary btn-block mb-4">Update password</button>
                        </div>

                    </form>
                </div>
            </section>

        <script>

            $(document).ready(function() {

                // $('#password, #confirm_password').on('keyup', function () {
                //     if ($('#password').val() == $('#confirm_password').val()) {
                //         $('#message').html('Matching').css('color', 'green');
                //     } else 
                //         $('#message').html('Not Matching').css('color', 'red');
                // });

                $("#changePassword").submit(function( event ) {

                    const password = document.querySelector('#newPassword');
                    const confirm = document.querySelector('#confirmPassword');
                    if (confirm.value === password.value) {
                        confirm.setCustomValidity('');
                    } else {
                        confirm.setCustomValidity('Passwords do not match');
                        // confirm.reportValidity();
                    }

                    if (!event.target.checkValidity()) {
                        event.preventDefault()
                        event.stopPropagation()
                    }
                    event.target.classList.add('was-validated')
                });
            });

        </script>
    <jsp:include page="/WEB-INF/templates/footer.jsp"/>
