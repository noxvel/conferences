<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />



        <jsp:include page="header.jsp"/>

        <div class="container">

            <section class="w-100 p-4 d-flex justify-content-center pb-4">
              <div class="d-flex flex-column">

                <h2 class="text-center">Sign up now</h2>
                <form calss="w-50">
                    <!-- layout with text inputs for the first and last names -->
                    <div class="row">
                      <div class="col-md-6 mb-4">
                        <div class="form-outline">
                          <label class="form-label" for="form3Example1">First name</label>
                          <input type="text" id="form3Example1" class="form-control" />
                        </div>
                      </div>
                      <div class="col-md-6 mb-4">
                        <div class="form-outline">
                          <label class="form-label" for="form3Example2">Last name</label>
                          <input type="text" id="form3Example2" class="form-control" />
                        </div>
                      </div>
                    </div>

                    <!-- Email input -->
                    <div class="form-outline mb-4">
                      <label class="form-label" for="form3Example3">Email address</label>
                      <input type="email" id="form3Example3" class="form-control" />
                    </div>

                    <!-- Password input -->
                    <div class="form-outline mb-4">
                      <label class="form-label" for="form3Example4">Password</label>
                      <input type="password" id="form3Example4" class="form-control" />
                    </div>

                    <!-- Submit button -->
                    <div class="d-grid gap-2">
                      <button type="submit" class="btn btn-primary btn-block mb-4"> Sign up </button>
                    </div>

                </form>
              </div>
            </section>

        </div>

        <jsp:include page="footer.jsp"/>