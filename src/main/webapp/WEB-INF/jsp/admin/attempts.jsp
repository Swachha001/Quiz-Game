<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<jsp:include page="/WEB-INF/jspf/admin-header.jsp">
    <jsp:param name="pageTitle" value="Attempts"/>
</jsp:include>

<main class="admin-main">
    <h1>Quiz attempts</h1>

    <table class="data">
        <thead>
        <tr>
            <th>Player</th>
            <th>Total</th>
            <th>Correct</th>
            <th>Wrong</th>
            <th>Score</th>
            <th>Percentage</th>
            <th>Completed</th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${attempts}" var="a">
            <tr>
                <td><c:out value="${a.playerName}"/></td>
                <td><c:out value="${a.totalQuestions}"/></td>
                <td><c:out value="${a.correctAnswers}"/></td>
                <td><c:out value="${a.wrongAnswers}"/></td>
                <td><c:out value="${a.score}"/></td>
                <td><fmt:formatNumber value="${a.percentage}" maxFractionDigits="0"/>%</td>
                <td><fmt:formatDate value="${a.completedAt}" pattern="dd MMM yyyy HH:mm"/></td>
                <td><a href="${pageContext.request.contextPath}/admin/attempts/view?id=${a.id}">View</a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</main>

<jsp:include page="/WEB-INF/jspf/footer.jsp"/>
