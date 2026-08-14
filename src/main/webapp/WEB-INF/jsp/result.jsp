<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<jsp:include page="/WEB-INF/jspf/header.jsp">
    <jsp:param name="pageTitle" value="Result"/>
    <jsp:param name="bodyClass" value="page-result"/>
</jsp:include>

<div class="narrow result-main">
    <h1>Quiz Result</h1>
    <p style="text-align:center;">Congratulations, <b><c:out value="${attempt.playerName}"/></b>!</p>

    <p class="score-line">
        Score: <c:out value="${attempt.score}"/> / 100
    </p>
    <p style="text-align:center;"><b><c:out value="${message}"/></b></p>

    <table class="data">
        <tr>
            <th>Total Questions</th>
            <td><c:out value="${attempt.totalQuestions}"/></td>
        </tr>
        <tr>
            <th>Correct Answers</th>
            <td><c:out value="${attempt.correctAnswers}"/></td>
        </tr>
        <tr>
            <th>Wrong Answers</th>
            <td><c:out value="${attempt.wrongAnswers}"/></td>
        </tr>
        <tr>
            <th>Percentage</th>
            <td><fmt:formatNumber value="${attempt.percentage}" maxFractionDigits="0"/>%</td>
        </tr>
    </table>

    <h2>Answer Review</h2>
    <ol class="review-list">
        <c:forEach items="${answers}" var="a" varStatus="st">
            <li class="${a.correct ? 'is-right' : 'is-wrong'}">
                <p><b>${st.count}. <c:out value="${a.questionText}"/></b></p>
                <p>
                    Your answer:
                    <c:choose>
                        <c:when test="${empty a.selectedOption}">No answer</c:when>
                        <c:when test="${a.selectedOption == 'A'}">A. <c:out value="${a.optionA}"/></c:when>
                        <c:when test="${a.selectedOption == 'B'}">B. <c:out value="${a.optionB}"/></c:when>
                        <c:when test="${a.selectedOption == 'C'}">C. <c:out value="${a.optionC}"/></c:when>
                        <c:when test="${a.selectedOption == 'D'}">D. <c:out value="${a.optionD}"/></c:when>
                        <c:otherwise><c:out value="${a.selectedOption}"/></c:otherwise>
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
                <c:if test="${not empty a.explanation}">
                    <p><c:out value="${a.explanation}"/></p>
                </c:if>
                <hr>
            </li>
        </c:forEach>
    </ol>

    <p class="after-links" style="text-align:center;">
        <a class="btn btn-green" href="${pageContext.request.contextPath}/quiz/start">Play again</a>
        &nbsp;
        <a href="${pageContext.request.contextPath}/about">About Tangail</a>
    </p>
</div>

<jsp:include page="/WEB-INF/jspf/footer.jsp"/>
