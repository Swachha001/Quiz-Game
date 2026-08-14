<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="/WEB-INF/jspf/admin-header.jsp">
    <jsp:param name="pageTitle" value="Question form"/>
</jsp:include>

<main class="admin-main narrow-admin">
    <h1>${mode == 'edit' ? 'Edit question' : 'Add question'}</h1>

    <c:if test="${not empty error}">
        <p class="flash-error"><c:out value="${error}"/></p>
    </c:if>

    <c:choose>
        <c:when test="${mode == 'edit'}">
            <c:url var="formAction" value="/admin/questions/edit"/>
        </c:when>
        <c:otherwise>
            <c:url var="formAction" value="/admin/questions/create"/>
        </c:otherwise>
    </c:choose>

    <form class="stack-form" method="post" action="${formAction}">
        <c:if test="${mode == 'edit'}">
            <input type="hidden" name="id" value="${question.id}">
        </c:if>

        <label>
            Question text
            <textarea name="questionText" required rows="3"><c:out value="${question.questionText}"/></textarea>
        </label>
        <label>
            Option A
            <input type="text" name="optionA" required value="<c:out value='${question.optionA}'/>">
        </label>
        <label>
            Option B
            <input type="text" name="optionB" required value="<c:out value='${question.optionB}'/>">
        </label>
        <label>
            Option C
            <input type="text" name="optionC" required value="<c:out value='${question.optionC}'/>">
        </label>
        <label>
            Option D
            <input type="text" name="optionD" required value="<c:out value='${question.optionD}'/>">
        </label>
        <label>
            Correct option
            <select name="correctOption" required>
                <option value="A" ${question.correctOption == 'A' ? 'selected' : ''}>A</option>
                <option value="B" ${question.correctOption == 'B' ? 'selected' : ''}>B</option>
                <option value="C" ${question.correctOption == 'C' ? 'selected' : ''}>C</option>
                <option value="D" ${question.correctOption == 'D' ? 'selected' : ''}>D</option>
            </select>
        </label>
        <label>
            Category
            <select name="category" required>
                <option value="Crops & Agriculture" ${question.category == 'Crops & Agriculture' ? 'selected' : ''}>Crops &amp; Agriculture</option>
                <option value="Academic Institutions" ${question.category == 'Academic Institutions' ? 'selected' : ''}>Academic Institutions</option>
                <option value="Geography" ${question.category == 'Geography' ? 'selected' : ''}>Geography</option>
            </select>
        </label>
        <label>
            Difficulty
            <select name="difficulty" required>
                <option value="Easy" ${question.difficulty == 'Easy' ? 'selected' : ''}>Easy</option>
                <option value="Medium" ${question.difficulty == 'Medium' ? 'selected' : ''}>Medium</option>
                <option value="Hard" ${question.difficulty == 'Hard' ? 'selected' : ''}>Hard</option>
            </select>
        </label>
        <label>
            Explanation
            <textarea name="explanation" rows="3"><c:out value="${question.explanation}"/></textarea>
        </label>
        <label>
            Source URL
            <input type="text" name="sourceUrl" value="<c:out value='${question.sourceUrl}'/>">
        </label>
        <label class="check">
            <input type="checkbox" name="active" ${question.active ? 'checked' : ''}>
            Active (only active questions appear in the quiz)
        </label>
        <button class="btn btn-green" type="submit">Save question</button>
    </form>
</main>

<jsp:include page="/WEB-INF/jspf/footer.jsp"/>
