<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="/WEB-INF/jspf/header.jsp">
    <jsp:param name="pageTitle" value="Admin login"/>
    <jsp:param name="bodyClass" value="page-player"/>
</jsp:include>

<div class="narrow">
    <h1>Admin Login</h1>
    <p>Username and password are in db.properties</p>

    <c:if test="${not empty error}">
        <p class="flash-error"><c:out value="${error}"/></p>
    </c:if>

    <form class="stack-form" method="post" action="${pageContext.request.contextPath}/admin/login">
        <label>
            Username:<br>
            <input type="text" name="username" required>
        </label>
        <label>
            Password:<br>
            <input type="password" name="password" required>
        </label>
        <p style="text-align:center;">
            <button class="btn btn-green" type="submit">Login</button>
        </p>
    </form>
</div>

<jsp:include page="/WEB-INF/jspf/footer.jsp"/>
