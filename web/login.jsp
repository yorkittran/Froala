<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
	<title>Login</title>
        <meta charset="ISO-8859-1">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="css/bootstrap.min.css">
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto:100,100i,300,300i,400,400i,500,500i,700,700i,900,900i">
        <link rel="stylesheet" href="css/froala_blocks.css">
        <link rel="stylesheet" href="css/font-awesome.min.css">
        <link rel="stylesheet" href="css/froala_editor.pkgd.min.css">
        <link rel="stylesheet" href="css/froala_style.min.css">
    </head>
    <body>
        <section class="fdb-block py-0 fp-active" data-block-type="forms" data-id="1" draggable="true">
            <div class="container py-5 my-5" style="background-image: url(https://cdn.jsdelivr.net/gh/froala/design-blocks@master/dist/imgs//shapes/6.svg);">
                <a href="index.jsp">
                    <img src="https://cdn.jsdelivr.net/gh/froala/design-blocks@master/dist/imgs//logo.png" height="80" alt="image">
                </a>
                <div class="row mt-5">
                    <div class="col-12 col-md-8 col-lg-7 col-xl-5 text-left">
                        <div class="row">
                            <div class="col">
                                <h1>Log In</h1>
                            </div>
                        </div>
                        <form action="LoginController" method="POST">
                            <c:if test="${not empty MESSAGE}">
                                <div class="row">
                                    <div class="col mt-4">
                                        <div class="alert alert-danger">${MESSAGE}</div>
                                    </div>
                                </div>
                            </c:if>
                            <div class="row">
                                <div class="col mt-4">
                                    <input type="text" name="txtEmail" class="form-control" placeholder="Email">
                                </div>
                            </div>
                            <div class="row mt-4">
                                <div class="col">
                                    <input type="password" name="txtPassword" class="form-control" placeholder="Password">
                                </div>
                            </div>
                            <div class="row mt-4">
                                <div class="col">
                                    <button class="btn btn-primary" type="submit">Submit</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </section>
        <script src="js/jquery-3.3.1.slim.min.js"></script>
        <script src="js/popper.min.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <script src="js/froala_editor.pkgd.min.js"></script>
        <script src="js/all.min.js"></script>
    </body>
</html>