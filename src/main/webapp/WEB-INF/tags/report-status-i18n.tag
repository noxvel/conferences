<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ attribute name="reportStatus" required="true" type="com.nextvoyager.conferences.model.entity.Report.Status" description="Status of the report." %>
<%-- <%@ variable name-given="reportColor" scope="AT_BEGIN"%> --%>
<fmt:setLocale value="${lang}" />
<fmt:setBundle basename="text" />

<c:choose>
    <c:when test="${reportStatus == 'OFFERED_BY_SPEAKER'}">
        <fmt:message key="common.report.status.offered-by-speaker"/>
    </c:when>
    <c:when test="${reportStatus == 'PROPOSE_TO_SPEAKER'}">
        <fmt:message key="common.report.status.propose-to-speaker"/>
    </c:when>
    <c:when test="${reportStatus == 'SUGGESTED_SPEAKER'}">
        <fmt:message key="common.report.status.suggested-speaker"/>
    </c:when>
    <c:when test="${reportStatus == 'CONFIRMED'}">
        <fmt:message key="common.report.status.confirmed"/>
    </c:when>
    <c:when test="${reportStatus == 'CANCELED'}">
        <fmt:message key="common.report.status.canceled"/>
    </c:when>
    <c:when test="${reportStatus == 'FREE'}">
        <fmt:message key="common.report.status.free"/>
    </c:when>
    <c:otherwise>
        <fmt:message key="common.report.status.canceled"/>
    </c:otherwise>

</c:choose>