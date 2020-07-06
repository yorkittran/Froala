<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="anhtt.config.Constant" %>
<html>
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="css/bootstrap.min.css">
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto:100,100i,300,300i,400,400i,500,500i,700,700i,900,900i">
        <link rel="stylesheet" href="css/froala_blocks.css">
        <link rel="stylesheet" href="css/font-awesome.min.css">
        <link rel="stylesheet" href="css/froala_editor.pkgd.min.css">
        <link rel="stylesheet" href="css/froala_style.min.css">
    </head>
    <body>
        <header data-block-type="headers" data-id="1">
            <div class="container">
                <nav class="navbar navbar-expand-md no-gutters">
                    <div class="col-3 text-left">
                        <a href="index.jsp">
                        <img src="https://cdn.jsdelivr.net/gh/froala/design-blocks@master/dist/imgs//logo.png" height="30" alt="image">
                        </a>
                    </div>
                    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target=".navbar-collapse-2" aria-controls="navbarNav7" aria-expanded="false" aria-label="Toggle navigation">
                        <span class="navbar-toggler-icon"></span>
                    </button>
                    <div class="collapse navbar-collapse navbar-collapse-2 justify-content-center col-md-6" id="navbarNav7">
                        <ul class="navbar-nav justify-content-center">
                            <li class="nav-item">
                                <a class="nav-link" href="#" contenteditable="false">Recommendation</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="#" contenteditable="false">Shop</a>
                            </li>
                        </ul>
                    </div>
                    <div class="collapse navbar-collapse navbar-collapse-2">
                        <c:set var="user" scope="session" value="${USER}"/>
                        <c:if test="${user.role != null}">
                            <c:if test="${user.role == Constant.ADMIN_ROLE}">
                                Welcome, <c:out value="${user.fullname}"/>
                                <a class="btn btn-primary ml-md-3" href="http://localhost:19266/Froala_API/CrawlController" contenteditable="false">Crawl Data</a>
                                <a class="btn btn-danger ml-md-3" href="LogoutController" contenteditable="false">Log Out</a>
                            </c:if>
                            <c:if test="${user.role == Constant.USER_ROLE}">
                                Welcome, <c:out value="${user.fullname}"/>
                                <a class="btn btn-danger ml-md-3" href="LogoutController" contenteditable="false">Log Out</a>
                            </c:if>
                        </c:if>
                        <c:if test="${user.role == null}">
                            <ul class="navbar-nav ml-auto justify-content-end">
                                <li class="nav-item">
                                    <a class="nav-link" href="login.jsp" contenteditable="false">Log In</a>
                                </li>
                            </ul>
                            <a class="btn btn-primary ml-md-3" href="register.jsp" contenteditable="false">Register</a>
                        </c:if>
                        <%--<c:out value="${cons.ADMIN_ROLE}"/>--%>
                    </div>
                </nav>
            </div>
        </header>