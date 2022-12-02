<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<fmt:formatDate value="${event.beginDate}" var="beginDate" pattern="yyyy-MM-dd'T'HH:mm"/>
<fmt:formatDate value="${event.endDate}" var="endDate" pattern="yyyy-MM-dd'T'HH:mm"/>

    <jsp:include page="/header.jsp"/>

        <div class="container d-flex flex-column">

            <form id="createEvent" class="row g-3" action="edit" method="post">
                <div class="col-12">
                    <label for="name" class="form-label">Name</label>
                    <input name="name" type="text" class="form-control" id="name" placeholder="" value="${event.name}">
                </div>
                <div class="col-12">
                    <label for="place" class="form-label">Place</label>
                    <input name="place" type="text" class="form-control" id="place" placeholder="New York..." value="${event.place}">
                </div>
                <div class="col-md-6">
                    <label for="beginDate" class="form-label">Begin date</label>
                    <input name="beginDate" type="datetime-local" class="form-control" id="beginDate" value="${beginDate}" min="2020-01-01T00:00" max="2100-01-01T00:00">
                </div>
                <input type="hidden" id="beginDateISO" name="beginDateISO" value="" />
                <div class="col-md-6">
                    <label for="endDate" class="form-label">End date</label>
                    <input name="endDate" type="datetime-local" class="form-control" id="endDate" value="${endDate}" min="2020-01-01T00:00" max="2100-01-01T00:00">
                </div>
                <input type="hidden" id="endDateISO" name="endDateISO" value="" />
                <div class="input-group">
                    <span class="input-group-text">Description</span>
                    <textarea name="description" class="form-control" rows="6 aria-label="Description" >${event.description}</textarea>
                </div>
                <%-- <input type="hidden" id="timezone" name="timezone" value="-08:00" /> --%>
                <input type="hidden" id="eventID" name="eventID" value="${event.id}" />
                <div class="col-12">
                    <button type="submit" class="btn btn-primary">Edit</button>
                </div>
            </form>

        </div>


        <script>

            $(document).ready(function() {
                $("#createEvent").submit(function( event ) {
                    let beginDate = $('#beginDate');
                    let endDate = $('#endDate');
                    $("#beginDateISO").val(new Date(beginDate.val()).toISOString());
                    $("#endDateISO").val(new Date(endDate.val()).toISOString());
                });
            });

        </script>

    <jsp:include page="/footer.jsp"/>