<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

        <div class="container align-self-end">
            <footer class="d-flex flex-wrap justify-content-between align-items-center py-3 my-4 border-top">
            <p class="col-md-4 mb-0 text-muted">© 2022 Company, Inc</p>

            <ul class="nav col-md-4 justify-content-end">
                <li class="nav-item"><a href="${contextPath}/home" class="nav-link px-2 text-muted">Home</a></li>
                <li class="nav-item"><a href="${contextPath}/about.jsp" class="nav-link px-2 text-muted">About</a></li>
            </ul>
            </footer>
        </div>

    </body>
</html>
