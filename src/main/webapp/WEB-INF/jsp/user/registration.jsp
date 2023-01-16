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

                <h2 class="text-center">
                  <fmt:message key="registration.header.text"/>
                </h2>
                <form class="needs-validation" novalidate id="registerUser" action="registration" method="post">
                    <c:if test="${requestScope.message != null}">
                        <div class="alert alert-danger" role="alert">
                            ${requestScope.message}
                        </div>
                    </c:if>
                    <!-- layout with text inputs for the first and last names -->
                    <div class="row">
                      <div class="col-md-6 mb-4">
                        <div class="form-outline">
                          <label class="form-label" for="firstName">
                            <fmt:message key="registration.firstname.label"/>
                          </label>
                          <input name="firstName" type="text" id="firstName" class="form-control" autoComplete="off" pattern="[A-Za-zА-Яа-яёЁЇїІіЄєҐґ\s'-]{1,60}" required/>
                          <div class="valid-feedback">
                            <fmt:message key="registration.firstname.good-feedback"/>
                          </div>
                        </div>
                      </div>
                      <div class="col-md-6 mb-4">
                        <div class="form-outline">
                          <label class="form-label" for="lastName">
                            <fmt:message key="registration.lastname.label"/>
                          </label>
                          <input name="lastName" type="text" id="lastName" class="form-control" autoComplete="off" pattern="[A-Za-zА-Яа-яёЁЇїІіЄєҐґ\s'-]{1,60}" required/>
                          <div class="valid-feedback">
                            <fmt:message key="registration.lastname.good-feedback"/>
                          </div>
                        </div>
                      </div>
                    </div>

                    <!-- Email input -->
                    <div class="form-outline mb-4">
                      <label class="form-label" for="email">
                        <fmt:message key="registration.email.label"/>
                      </label>
                      <input name="email" type="email" id="email" class="form-control" autoComplete="off" pattern="^[\w\-\.]+@([\w-]+\.)+[\w-]{2,4}$" required />
                      <div class="invalid-feedback">
                        <fmt:message key="registration.email.bad-feedback"/>
                      </div>
                    </div>

                    <!-- Password input -->
                    <div class="form-outline mb-4">
                      <label class="form-label" for="password">
                        <fmt:message key="registration.password.label"/>
                      </label>
                      <input name="password" type="password" id="password" class="form-control" autoComplete="off" pattern=".{3,60}" required />
                      <div class="invalid-feedback">
                        <fmt:message key="registration.password.bad-feedback"/>
                      </div>
                    </div>
                    
                    <div class="form-outline mb-4">
                        <select name="userRole" id="userRole" class="form-select">
                            <option selected value="ORDINARY_USER"><fmt:message key="registration.role.ordinary-user"/></option>
                            <option value="SPEAKER"><fmt:message key="registration.role.speaker"/></option>
                        </select>
                    </div>

                    <!-- Submit button -->
                    <div class="d-grid gap-2">
                      <button type="submit" class="btn btn-warning btn-block mb-5">
                        <fmt:message key="registration.button.signup"/>
                      </button>
                    </div>

                </form>
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