<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="/WEB-INF/jspf/admin-header.jsp">
    <jsp:param name="pageTitle" value="Questions"/>
</jsp:include>

<main class="admin-main">
    <div class="admin-title-row">
        <h1>Questions</h1>
        <a class="btn btn-gold" href="${pageContext.request.contextPath}/admin/questions/create">Add question</a>
    </div>

    <form class="filter-row" method="get" action="${pageContext.request.contextPath}/admin/questions">
        <input type="search" name="q" placeholder="Search question text" value="<c:out value='${q}'/>">
        <select name="category">
            <option value="">All categories</option>
            <option value="Crops & Agriculture" ${category == 'Crops & Agriculture' ? 'selected' : ''}>Crops &amp; Agriculture</option>
            <option value="Academic Institutions" ${category == 'Academic Institutions' ? 'selected' : ''}>Academic Institutions</option>
            <option value="Geography" ${category == 'Geography' ? 'selected' : ''}>Geography</option>
        </select>
        <select name="difficulty">
            <option value="">All difficulties</option>
            <option value="Easy" ${difficulty == 'Easy' ? 'selected' : ''}>Easy</option>
            <option value="Medium" ${difficulty == 'Medium' ? 'selected' : ''}>Medium</option>
            <option value="Hard" ${difficulty == 'Hard' ? 'selected' : ''}>Hard</option>
        </select>
        <button class="btn btn-green" type="submit">Filter</button>
    </form>

    <table class="data">
        <thead>
        <tr>
            <th>ID</th>
            <th>Question</th>
            <th>Category</th>
            <th>Difficulty</th>
            <th>Status</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${questions}" var="item">
            <tr>
                <td>${item.id}</td>
                <td><c:out value="${item.questionText}"/></td>
                <td><c:out value="${item.category}"/></td>
                <td><c:out value="${item.difficulty}"/></td>
                <td>${item.active ? 'Active' : 'Inactive'}</td>
                <td class="actions">
                    <a href="${pageContext.request.contextPath}/admin/questions/edit?id=${item.id}">Edit</a>
                    <form method="post" action="${pageContext.request.contextPath}/admin/questions/toggle">
                        <input type="hidden" name="id" value="${item.id}">
                        <button type="submit">${item.active ? 'Deactivate' : 'Activate'}</button>
                    </form>
                    <form method="post" action="${pageContext.request.contextPath}/admin/questions/delete"
                          onsubmit="return confirm('Delete this question?');">
                        <input type="hidden" name="id" value="${item.id}">
                        <button type="submit">Delete</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</main>

<jsp:include page="/WEB-INF/jspf/footer.jsp"/>
