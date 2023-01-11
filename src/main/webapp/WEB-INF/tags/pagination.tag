<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ attribute name="arialLabel" required="true" type="java.lang.String" description="Arial label" %>
<%@ attribute name="page" required="true" type="java.lang.Integer" description="Current page" %>
<%@ attribute name="numOfPages" required="true" type="java.lang.Integer" description="Count of all pages" %>
<%@ attribute name="linkPath" required="true" type="java.lang.String" description="Link path" %>
                              
<nav aria-label="${reportStatus}" class="p-3">
    <ul class="pagination justify-content-center">
        <li class="${(page == 1) ? 'page-item disabled' : 'page-item'}">
            <a class="page-link" href="${linkPath}page=${page == 1 ? page : page - 1}"><span aria-hidden="true">&laquo;</span></a>
        </li>

        <c:forEach begin="1" end="${numOfPages}" varStatus="loop">
            <c:if test="${loop.index > page - 5 && (loop.index < page + 5)}">
                <li class="${(loop.index == page) ? 'page-item active' : 'page-item'}">
                    <a class="page-link" href="${linkPath}page=${loop.index}">${loop.index}</a>
                </li>
            </c:if>
        </c:forEach>

        <li class="${(numOfPages == page || numOfPages == 0) ? 'page-item disabled' : 'page-item'}">
            <a class="page-link" href="${linkPath}page=${numOfPages == page ? page : page + 1}"><span aria-hidden="true">&raquo;</span></a>
        </li>

    </ul>
</nav>