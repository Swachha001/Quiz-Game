<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="/WEB-INF/jspf/header.jsp">
    <jsp:param name="pageTitle" value="Home"/>
    <jsp:param name="bodyClass" value="page-home"/>
</jsp:include>

<div class="hero">
    <h1>Welcome to Tangail District Quiz</h1>
    <p class="lede"><b>Test Your Knowledge About Tangail</b></p>
    <img class="hero-image"
         src="https://images.unsplash.com/photo-1500382017468-9049fed747ef?auto=format&fit=crop&w=1200&q=60"
         alt="Green fields">
    <p class="lede">This quiz has 10 questions. Topics are crops, schools and geography of Tangail.</p>
    <p style="text-align:center;">
        <a class="btn btn-gold" href="${pageContext.request.contextPath}/quiz/start">Start Quiz</a>
    </p>
    <hr>
    <h2>Quiz Categories</h2>
    <ol class="category-list">
        <li>
            <span>Crops &amp; Agriculture</span>
            <em>Rice, jute, mustard, wheat and vegetables.</em>
        </li>
        <li>
            <span>Academic Institutions</span>
            <em>MBSTU, Mirzapur Cadet College, Tangail Medical College.</em>
        </li>
        <li>
            <span>Geography</span>
            <em>12 upazilas, Jamuna river, nearby districts.</em>
        </li>
    </ol>
    <h2>How to play</h2>
    <p>1. Enter your name</p>
    <p>2. Answer 10 questions</p>
    <p>3. Submit and see your score</p>
</div>

<jsp:include page="/WEB-INF/jspf/footer.jsp"/>
