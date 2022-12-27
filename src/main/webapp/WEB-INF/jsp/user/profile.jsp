<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<fmt:setLocale value="${lang}" />
<fmt:setBundle basename="text" />


    <jsp:include page="/WEB-INF/templates/header.jsp"/>

        <div class="container">

            <section class="w-100 p-4 d-flex justify-content-center pb-4">
              <div class="d-flex flex-column">

                <h2 class="text-center mb-3">Your profile</h2>
                <h3> Your email: <c:out value="${currentUser.email}"/></h3>
                <h3> Your role: <c:out value="${currentUser.role.name}"/></h3>
                <form class="needs-validation mb-3" novalidate id="updateProfile" action="profile" method="post">
                    <c:if test="${requestScope.message != null}">
                        <div class="alert alert-danger" role="alert">
                            ${requestScope.message}
                        </div>
                    </c:if>
                    <!-- layout with text inputs for the first and last names -->
                    <div class="row">
                      <div class="col-md-6 mb-4">
                        <div class="form-outline">
                          <label class="form-label" for="firstName">First name</label>
                          <input name="firstName" value="${currentUser.firstName}" type="text" id="firstName" class="form-control" autoComplete="off" pattern="[A-Za-zА-Яа-яёЁЇїІіЄєҐґ\s'-]{1,60}" required/>
                          <div class="valid-feedback">
                            Looks good!
                          </div>
                        </div>
                      </div>
                      <div class="col-md-6 mb-4">
                        <div class="form-outline">
                          <label class="form-label" for="lastName">Last name</label>
                          <input name="lastName" value="${currentUser.lastName}" type="text" id="lastName" class="form-control" autoComplete="off" pattern="[A-Za-zА-Яа-яёЁЇїІіЄєҐґ\s'-]{1,60}" required/>
                          <div class="valid-feedback">
                            Looks good!
                          </div>
                        </div>
                      </div>
                    </div>

                    <%-- <!-- Email input -->
                    <div class="form-outline mb-4">
                      <label class="form-label" for="email">Email address</label>
                      <input name="email" type="email" id="email" class="form-control" autoComplete="off" pattern="^[\w\-\.]+@([\w-]+\.)+[\w-]{2,4}$" required />
                      <div class="invalid-feedback">
                        Bad email!
                      </div>
                    </div> --%>

                    <!-- Submit button -->
                    <div class="d-grid gap-2">
                      <button type="submit" class="btn btn-primary btn-block mb-4">Update profile</button>
                    </div>

                </form>

                <hr/>
                
                <a role="button" href="change-password" class="btn btn-info">Change passoword</a>
              </div>
            </section>

        </div>

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