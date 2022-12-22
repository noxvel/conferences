<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

    <jsp:include page="/WEB-INF/templates/header.jsp"/>

        <div class="container">

            <section class="w-100 p-4 d-flex justify-content-center pb-4">
                <div class="d-flex flex-column">

                    <h2 class="fw-normal mb-3 pb-3 text-center">Change password</h2>      
                    <form id="changePassword" action="change-password" method="post">
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
                            <input type="password" id="confirmNewPassword" class="form-control" autoComplete="off" pattern=".{3,60}" required />
                            <div class="invalid-feedback">
                                Bad password!
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
                $("#registerUser").submit(function( event ) {
                  if (!event.target.checkValidity()) {
                    event.preventDefault()
                    event.stopPropagation()
                  }
                  event.target.classList.add('was-validated')
                });
            });

        </script>
    <jsp:include page="/WEB-INF/templates/footer.jsp"/>
