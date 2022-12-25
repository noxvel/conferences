<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%-- <%@ attribute name="cell1" required="true" type="java.lang.String" description="Text to use in the first cell." %>
<%@ attribute name="cell2" required="false" type="java.lang.String" description="Text to use in the second cell." %> --%>

<%@ attribute name="reportStatus" required="true" type="com.nextvoyager.conferences.model.entity.Report.Status" description="Status of the report." %>

<c:choose>
    <c:when test="${report.status == 'OFFERED_BY_SPEAKER'}">border-info</c:when>
    <c:when test="${report.status == 'PROPOSE_TO_SPEAKER'}">border-primary</c:when>
    <c:when test="${report.status == 'SUGGESTED_SPEAKER'}">reportColor</c:when>
    <c:when test="${report.status == 'CONFIRMED'}">border-success</c:when>
    <c:when test="${report.status == 'CANCELED'}">border-danger</c:when>
    <c:otherwise></c:otherwise>
</c:choose>