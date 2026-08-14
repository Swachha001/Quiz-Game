<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="/WEB-INF/jspf/header.jsp">
    <jsp:param name="pageTitle" value="Start Quiz"/>
    <jsp:param name="bodyClass" value="page-player"/>
</jsp:include>

<div class="narrow">
    <h1>Player Information</h1>
    <p>Please enter your name to start the quiz. Email and phone are optional.</p>

    <c:if test="${not empty error}">
        <p class="flash-error"><c:out value="${error}"/></p>
    </c:if>

    <form class="stack-form" method="post" action="${pageContext.request.contextPath}/quiz/start">
        <label>
            Name:<br>
            <input type="text" name="name" required maxlength="100" value="<c:out value='${name}'/>">
        </label>
        <label>
            Email:<br>
            <input type="email" name="email" maxlength="150" value="<c:out value='${email}'/>">
        </label>
        <label>
            Phone:<br>
            <input type="text" name="phone" maxlength="30" value="<c:out value='${phone}'/>">
        </label>
        <p style="text-align:center;">
            <button class="btn btn-green" type="submit">Start Quiz</button>
        </p>
    </form>
</div>

<jsp:include page="/WEB-INF/jspf/footer.jsp"/>
