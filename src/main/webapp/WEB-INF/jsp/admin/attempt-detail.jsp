<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<jsp:include page="/WEB-INF/jspf/admin-header.jsp">
    <jsp:param name="pageTitle" value="Attempt detail"/>
</jsp:include>

<main class="admin-main">
    <h1>Attempt by <c:out value="${attempt.playerName}"/></h1>
    <p>
        Score <c:out value="${attempt.score}"/> / 100
        · Correct <c:out value="${attempt.correctAnswers}"/>
        · Wrong <c:out value="${attempt.wrongAnswers}"/>
        · <fmt:formatDate value="${attempt.completedAt}" pattern="dd MMM yyyy HH:mm"/>
    </p>

    <ol class="review-list">
        <c:forEach items="${answers}" var="a" varStatus="st">
            <li class="${a.correct ? 'is-right' : 'is-wrong'}">
                <p class="review-q">${st.count}. <c:out value="${a.questionText}"/></p>
                <p>
                    Player answer:
                    <c:choose>
                        <c:when test="${empty a.selectedOption}">No answer</c:when>
                        <c:when test="${a.selectedOption == 'A'}">A. <c:out value="${a.optionA}"/></c:when>
                        <c:when test="${a.selectedOption == 'B'}">B. <c:out value="${a.optionB}"/></c:when>
                        <c:when test="${a.selectedOption == 'C'}">C. <c:out value="${a.optionC}"/></c:when>
                        <c:when test="${a.selectedOption == 'D'}">D. <c:out value="${a.optionD}"/></c:when>
                    </c:choose>
                </p>
                <p>
                    Correct answer:
                    <c:choose>
                        <c:when test="${a.correctOption == 'A'}">A. <c:out value="${a.optionA}"/></c:when>
                        <c:when test="${a.correctOption == 'B'}">B. <c:out value="${a.optionB}"/></c:when>
                        <c:when test="${a.correctOption == 'C'}">C. <c:out value="${a.optionC}"/></c:when>
                        <c:when test="${a.correctOption == 'D'}">D. <c:out value="${a.optionD}"/></c:when>
                    </c:choose>
                </p>
                <p class="status">${a.correct ? 'Correct' : 'Wrong'}</p>
            </li>
        </c:forEach>
    </ol>
</main>

<jsp:include page="/WEB-INF/jspf/footer.jsp"/>
