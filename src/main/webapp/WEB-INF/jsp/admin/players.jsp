<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<jsp:include page="/WEB-INF/jspf/admin-header.jsp">
    <jsp:param name="pageTitle" value="Players"/>
</jsp:include>

<main class="admin-main">
    <h1>Players</h1>

    <form class="filter-row" method="get" action="${pageContext.request.contextPath}/admin/players">
        <input type="search" name="q" placeholder="Search name or email" value="<c:out value='${q}'/>">
        <button class="btn btn-green" type="submit">Search</button>
    </form>

    <table class="data">
        <thead>
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Email</th>
            <th>Phone</th>
            <th>Created</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${players}" var="p">
            <tr>
                <td>${p.id}</td>
                <td><c:out value="${p.name}"/></td>
                <td><c:out value="${p.email}"/></td>
                <td><c:out value="${p.phone}"/></td>
                <td><fmt:formatDate value="${p.createdAt}" pattern="dd MMM yyyy HH:mm"/></td>
                <td class="actions">
                    <a href="${pageContext.request.contextPath}/admin/players/edit?id=${p.id}">Edit / attempts</a>
                    <form method="post" action="${pageContext.request.contextPath}/admin/players/delete"
                          onsubmit="return confirm('Delete this player and their quiz attempts?');">
                        <input type="hidden" name="id" value="${p.id}">
                        <button type="submit">Delete</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</main>

<jsp:include page="/WEB-INF/jspf/footer.jsp"/>
