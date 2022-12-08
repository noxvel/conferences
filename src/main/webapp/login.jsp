<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

        <jsp:include page="/header.jsp"/>

        <div class="container">

            <section class="w-100 p-4 d-flex justify-content-center pb-4">
                <div class="d-flex flex-column">

                    <h2 class="fw-normal mb-3 pb-3 text-center">Sign into your account</h2>      
                    <form id="loginUser" action="login" method="post">
                        <c:if test="${requestScope.message != null}">
                            <div class="alert alert-danger" role="alert">
                                ${requestScope.message}
                            </div>
                        </c:if>
                        <!-- Email input -->
                        <div class="form-outline mb-4">
                            <label class="form-label" for="email">Email address</label>
                            <input name="email" type="email" id="email" class="form-control" autoComplete="off" pattern="^[\w\-\.]+@([\w-]+\.)+[\w-]{2,4}$" required/>
                        </div>

                        <!-- Password input -->
                        <div class="form-outline mb-4">
                            <label class="form-label" for="password">Password</label>
                            <input name="password" type="password" id="password" class="form-control" autoComplete="off" required/>
                        </div>

                        <!-- 2 column grid layout for inline styling -->
                        <div class="row mb-4">
                            <div class="col d-flex justify-content-center">
                                <!-- Checkbox -->
                                <div class="form-check">
                                    <input name="rememberMe" class="form-check-input" type="checkbox" value="" id="rememberMe" checked />
                                    <label class="form-check-label" for="rememberMe"> Remember me </label>
                                </div>
                            </div>

                            <div class="col">
                            <!-- Simple link -->
                            <a href="#!">Forgot password?</a>
                            </div>
                        </div>

                        <!-- Submit button -->
                        <div class="d-grid gap-2">
                            <button type="submit" class="btn btn-primary btn-block mb-4">Sign in</button>
                        </div>

                        <!-- Register buttons -->
                        <div class="text-center">
                            <p>Not a member? <a href="/conferences/registration">Register</a></p>
                        </div>
                    </form>
                </div>
            </section>

        <jsp:include page="/footer.jsp"/>
