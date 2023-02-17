<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set var="currentUser" value="${sessionScope.user}" />
<c:set var="isModerator" value="${sessionScope.userRole == 'MODERATOR'}" />

<fmt:setLocale value="${lang}" />
<fmt:setBundle basename="text" />
<!DOCTYPE html>
<html lang="${lang}">
    <jsp:include page="/WEB-INF/templates/_head.jsp"/>
    <body class="d-flex flex-column min-vh-100">
        <jsp:include page="/WEB-INF/templates/_header.jsp"/>

        <main class="container flex-fill">

            <div class="d-flex flex-column">

                <form id="createReport" class="row g-3" action="edit" method="post">
                    <div class="col-12">
                        <label for="topic" class="form-label"><fmt:message key="report-edit.topic.label"/></label>
                        <input name="topic" type="text" class="form-control" id="topic" placeholder="Reports topic" value="${report.topic}" required>
                    </div>
                    <c:if test="${isModerator}">
                        <div class="col-md-8">
                            <label for="speaker" class="form-label"><fmt:message key="report-edit.speaker.label"/></label>
                            <select name="speaker" id="speakerSelect" class="form-select" >
                                <option value="0"><fmt:message key="report-edit.speaker.select.vacant"/></option>
                                <c:forEach var="speaker" items="${speakers}">
                                    <option ${report.speaker.id == speaker.id ? 'selected' : ''} value="${speaker.id}">
                                        ${speaker.firstName} ${speaker.lastName}(${speaker.email})
                                    </option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col-md-4">
                            <div id="actionForSpeakerBlock" class="${report.status == 'FREE' ? 'd-none' : 'd-block'}">
                                <div><fmt:message key="report-edit.action.label"/></div>
                                <div class="form-check">
                                    <input class="form-check-input" type="radio" name="status" value="CONFIRMED" id="radioReportStatus1" checked>
                                    <label class="form-check-label" for="radioReportStatus1">
                                        <fmt:message key="report-edit.action.radio.consolidate"/>
                                    </label>
                                </div>
                                <div class="form-check">
                                    <input class="form-check-input" type="radio" name="status" value="PROPOSE_TO_SPEAKER" id="radioReportStatus2" >
                                    <label class="form-check-label" for="radioReportStatus2">
                                        <fmt:message key="report-edit.action.radio.propose"/>
                                    </label>
                                </div>
                            </div>
                        </div>
                    </c:if>
                    <div class="input-group">
                        <span class="input-group-text"><fmt:message key="report-edit.description.label"/></span>
                        <textarea name="description" class="form-control" rows="6 aria-label="Description" >${report.description}</textarea>
                    </div>
                    <input type="hidden" id="reportID" name="reportID" value="${report.id}" />
                    <div class="col-12">
                        <a role="button" href="${contextPath}/pages/report/view?reportID=${report.id}" class="btn btn-secondary">
                            <fmt:message key="report-edit.button.back"/>
                        </a>
                        <button type="submit" class="btn btn-primary">
                            <fmt:message key="report-edit.button.edit"/>
                        </button>
                    </div>
                </form>

            </div>

        </main>

        <jsp:include page="/WEB-INF/templates/_footer.jsp"/>
        <jsp:include page="/WEB-INF/templates/_scripts.jsp"/>

        <script>
            $(document).ready(() => {
                $('#speakerSelect').change(function(){ 
                    let actionBlock = $('#actionForSpeakerBlock');
                    if($(this).val() === '0'){
                        actionBlock.addClass('d-none');
                    }else{
                        actionBlock.removeClass('d-none');
                    }
                });
            });
        </script>
    </body>
</html>