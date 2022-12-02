<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />


    <jsp:include page="/header.jsp"/>

        <div class="container d-flex flex-column">

            <form id="createReport" class="row g-3" action="create" method="post">
                <div class="col-12">
                    <label for="topic" class="form-label">Topic</label>
                    <input name="topic" type="text" class="form-control" id="topic" placeholder="Reports topic" value="Basic topic">
                </div>
                <div class="col-md-8">
                    <label for="speaker" class="form-label">Speaker</label>
                    <select name="speaker" id="speaker" class="form-select">
                        <option value="">Vacant</option>
                        <c:forEach var="speaker" items="${speakers}">
                            <option value="${speaker.id}">${speaker.email}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="col-md-4">
                    <label for="status" class="form-label">Status</label>
                    <select name="status" id="status" class="form-select">
                        <c:forEach var="status" items="${statuses}">
                            <option value="${status}">${status.name}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="input-group">
                    <span class="input-group-text">Description</span>
                    <textarea name="description" class="form-control" rows="6 aria-label="Description" >Interesting description of the new report</textarea>
                </div>
                <input type="hidden" id="event" name="event" value="${eventID}" />
                <div class="col-12">
                    <button type="submit" class="btn btn-primary">Create</button>
                </div>
            </form>

        </div>

    <jsp:include page="/footer.jsp"/>