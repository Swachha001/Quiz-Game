<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="/WEB-INF/jspf/header.jsp">
    <jsp:param name="pageTitle" value="Error"/>
    <jsp:param name="bodyClass" value="page-error"/>
</jsp:include>

<div class="narrow">
    <h1>Error <c:out value="${errorCode}"/></h1>
    <p><c:out value="${errorMessage}"/></p>
    <p><a href="${pageContext.request.contextPath}/home">Go to Home</a></p>
</div>

<jsp:include page="/WEB-INF/jspf/footer.jsp"/>
