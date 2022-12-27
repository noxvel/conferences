<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ attribute name="reportStatus" required="true" type="com.nextvoyager.conferences.model.entity.Report.Status" description="Status of the report." %>
<%@ variable name-given="reportColor" scope="AT_BEGIN"%>

<c:choose>
    <c:when test="${reportStatus == 'OFFERED_BY_SPEAKER'}">
        <c:set var="reportColor" value="info"/>
    </c:when>
    <c:when test="${reportStatus == 'PROPOSE_TO_SPEAKER'}">
        <c:set var="reportColor" value="secondary"/>
    </c:when>
    <c:when test="${reportStatus == 'SUGGESTED_SPEAKER'}">
        <c:set var="reportColor" value="warning"/>
    </c:when>
    <c:when test="${reportStatus == 'CONFIRMED'}">
        <c:set var="reportColor" value="success"/>
    </c:when>
    <c:when test="${reportStatus == 'CANCELED'}">
        <c:set var="reportColor" value="danger"/>
    </c:when>
    <c:when test="${reportStatus == 'FREE'}">
        <c:set var="reportColor" value="primary"/>
    </c:when>
    <c:otherwise>
        <c:set var="reportColor" value=""/>
    </c:otherwise>

</c:choose>