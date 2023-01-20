<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ attribute name="arialLabel" required="true" type="java.lang.String" description="Arial label" %>
<%@ attribute name="page" required="true" type="java.lang.Integer" description="Current page" %>
<%@ attribute name="numOfPages" required="true" type="java.lang.Integer" description="Count of all pages" %>
<%@ attribute name="linkPath" required="true" type="java.lang.String" description="Link path" %>
<%@ attribute name="showLimitSelect" required="false" type="java.lang.Boolean" description="Show limit select field" %>
<%@ attribute name="queryString" required="false" type="java.lang.String" description="Query string" %>
           
<fmt:setLocale value="${lang}" />
<fmt:setBundle basename="text" />                   

<nav aria-label="${reportStatus}" class="p-3 d-flex justify-content-${showLimitSelect ? 'between' : 'center'}">
    <c:if test="${showLimitSelect}">
        <div>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;</div>
    </c:if>
    <div class="d-flex align-items-center">
        <ul class="pagination">
            <li class="${(page == 1) ? 'page-item disabled' : 'page-item'}">
                <a class="page-link" href="${linkPath}?page=${page == 1 ? page : page - 1}&limit=${limit}${queryString}">
                    <span aria-hidden="true">&laquo;</span>
                </a>
            </li>

            <c:forEach begin="1" end="${numOfPages}" varStatus="loop">
                <c:if test="${loop.index > page - 5 && (loop.index < page + 5)}">
                    <li class="${(loop.index == page) ? 'page-item active' : 'page-item'}">
                        <a class="page-link" href="${linkPath}?page=${loop.index}&limit=${limit}${queryString}">${loop.index}</a>
                    </li>
                </c:if>
            </c:forEach>

            <li class="${(numOfPages == page || numOfPages == 0) ? 'page-item disabled' : 'page-item'}">
                <a class="page-link" href="${linkPath}?page=${numOfPages == page ? page : page + 1}&limit=${limit}${queryString}">
                    <span aria-hidden="true">&raquo;</span>
                </a>
            </li>

        </ul>
    </div>
    <c:if test="${showLimitSelect}">
        <div class="d-flex flex-row">
            <div class="me-2 fs-5"><fmt:message key="pagination.text.on-page"/></div>
            <form id="selectLimit" action="${linkPath}" method="get">
                <input name="page" type="hidden" value="1" />
                <select name="limit" class="form-select" onchange="submit()" aria-label="Elements number on page">
                    <c:forEach var="elLimit" items="${applicationScope.paginationLimitList}">
                        <option ${limit == elLimit ? 'selected' : ''} value="${elLimit}">${elLimit}</option>
                    </c:forEach>
                </select>
            </form>
        </div>
    </c:if>
</nav>