<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title><c:out value="${param.pageTitle}"/> - Tangail District Quiz</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="site-nav">
    <a class="nav-brand" href="${pageContext.request.contextPath}/home">Tangail District Quiz</a>
    <a href="${pageContext.request.contextPath}/home">Home</a> |
    <a href="${pageContext.request.contextPath}/quiz/start">Start Quiz</a> |
    <a href="${pageContext.request.contextPath}/about">About Tangail</a> |
    <a href="${pageContext.request.contextPath}/admin/dashboard">Admin</a>
</div>
