<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<jsp:include page="/WEB-INF/jspf/admin-header.jsp">
    <jsp:param name="pageTitle" value="Dashboard"/>
</jsp:include>

<main class="admin-main">
    <h1>Admin dashboard</h1>

    <ul class="stat-row">
        <li><a href="${pageContext.request.contextPath}/admin/questions">Questions <strong>${totalQuestions}</strong></a></li>
        <li><a href="${pageContext.request.contextPath}/admin/questions">Active <strong>${activeQuestions}</strong></a></li>
        <li><a href="${pageContext.request.contextPath}/admin/players">Players <strong>${totalPlayers}</strong></a></li>
        <li><a href="${pageContext.request.contextPath}/admin/attempts">Attempts <strong>${totalAttempts}</strong></a></li>
        <li><span>Average score <strong><fmt:formatNumber value="${averageScore}" maxFractionDigits="0"/>%</strong></span></li>
    </ul>

    <section>
        <h2>Recent quiz attempts</h2>
        <c:choose>
            <c:when test="${empty recentAttempts}">
                <p>No attempts yet.</p>
            </c:when>
            <c:otherwise>
                <table class="data">
                    <thead>
                    <tr>
                        <th>Player</th>
                        <th>Score</th>
                        <th>Percentage</th>
                        <th>Completed</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${recentAttempts}" var="a">
                        <tr>
                            <td><c:out value="${a.playerName}"/></td>
                            <td><c:out value="${a.score}"/></td>
                            <td><fmt:formatNumber value="${a.percentage}" maxFractionDigits="0"/>%</td>
                            <td><fmt:formatDate value="${a.completedAt}" pattern="dd MMM yyyy HH:mm"/></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:otherwise>
        </c:choose>
    </section>

    <section>
        <h2>Top players</h2>
        <c:choose>
            <c:when test="${empty topPlayers}">
                <p>No players with scores yet.</p>
            </c:when>
            <c:otherwise>
                <table class="data">
                    <thead>
                    <tr>
                        <th>Player</th>
                        <th>Best score</th>
                        <th>Best percentage</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${topPlayers}" var="a">
                        <tr>
                            <td><c:out value="${a.playerName}"/></td>
                            <td><c:out value="${a.score}"/></td>
                            <td><fmt:formatNumber value="${a.percentage}" maxFractionDigits="0"/>%</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:otherwise>
        </c:choose>
    </section>

    <section>
        <h2>Highest scores</h2>
        <c:choose>
            <c:when test="${empty topScores}">
                <p>No scores yet.</p>
            </c:when>
            <c:otherwise>
                <table class="data">
                    <thead>
                    <tr>
                        <th>Player</th>
                        <th>Score</th>
                        <th>Percentage</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${topScores}" var="a">
                        <tr>
                            <td><c:out value="${a.playerName}"/></td>
                            <td><c:out value="${a.score}"/></td>
                            <td><fmt:formatNumber value="${a.percentage}" maxFractionDigits="0"/>%</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:otherwise>
        </c:choose>
    </section>
</main>

<jsp:include page="/WEB-INF/jspf/footer.jsp"/>
