<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="/WEB-INF/jspf/header.jsp">
    <jsp:param name="pageTitle" value="Quiz"/>
    <jsp:param name="bodyClass" value="page-quiz"/>
</jsp:include>

<div class="quiz-wrap">
    <h1>Tangail District Quiz</h1>
    <p>Hello <b><c:out value="${playerName}"/></b>, please answer the questions below.</p>
    <p id="quiz-progress"><b>Total questions: 10</b></p>
    <hr>

    <form id="quiz-form"
          method="post"
          action="${pageContext.request.contextPath}/quiz/submit"
          data-total="${questions.size()}">

        <c:forEach items="${questions}" var="q" varStatus="st">
            <div class="question">
                <p><b>Question ${st.count} of ${questions.size()}</b></p>
                <p><c:out value="${q.questionText}"/></p>
                <p><i><c:out value="${q.category}"/> - <c:out value="${q.difficulty}"/></i></p>

                <label class="choice">
                    <input type="radio" name="answer_${q.id}" value="A">
                    A. <c:out value="${q.optionA}"/>
                </label>
                <label class="choice">
                    <input type="radio" name="answer_${q.id}" value="B">
                    B. <c:out value="${q.optionB}"/>
                </label>
                <label class="choice">
                    <input type="radio" name="answer_${q.id}" value="C">
                    C. <c:out value="${q.optionC}"/>
                </label>
                <label class="choice">
                    <input type="radio" name="answer_${q.id}" value="D">
                    D. <c:out value="${q.optionD}"/>
                </label>
            </div>
        </c:forEach>

        <p style="text-align:center;">
            <button class="btn btn-gold" type="submit">Submit Quiz</button>
        </p>
    </form>
</div>

<jsp:include page="/WEB-INF/jspf/footer.jsp"/>
