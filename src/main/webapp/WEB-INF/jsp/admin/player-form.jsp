<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<jsp:include page="/WEB-INF/jspf/admin-header.jsp">
    <jsp:param name="pageTitle" value="Edit player"/>
</jsp:include>

<main class="admin-main narrow-admin">
    <h1>Edit player</h1>

    <c:if test="${not empty error}">
        <p class="flash-error"><c:out value="${error}"/></p>
    </c:if>

    <form class="stack-form" method="post" action="${pageContext.request.contextPath}/admin/players/edit">
        <input type="hidden" name="id" value="${player.id}">
        <label>
            Name
            <input type="text" name="name" required value="<c:out value='${player.name}'/>">
        </label>
        <label>
            Email
            <input type="email" name="email" value="<c:out value='${player.email}'/>">
        </label>
        <label>
            Phone
            <input type="text" name="phone" value="<c:out value='${player.phone}'/>">
        </label>
        <button class="btn btn-green" type="submit">Save player</button>
    </form>

    <h2>This player's quiz attempts</h2>
    <c:choose>
        <c:when test="${empty attempts}">
            <p>No attempts yet.</p>
        </c:when>
        <c:otherwise>
            <table class="data">
                <thead>
                <tr>
                    <th>Score</th>
                    <th>Correct</th>
                    <th>Wrong</th>
                    <th>Completed</th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${attempts}" var="a">
                    <tr>
                        <td><c:out value="${a.score}"/></td>
                        <td><c:out value="${a.correctAnswers}"/></td>
                        <td><c:out value="${a.wrongAnswers}"/></td>
                        <td><fmt:formatDate value="${a.completedAt}" pattern="dd MMM yyyy HH:mm"/></td>
                        <td><a href="${pageContext.request.contextPath}/admin/attempts/view?id=${a.id}">View</a></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:otherwise>
    </c:choose>
</main>

<jsp:include page="/WEB-INF/jspf/footer.jsp"/>
