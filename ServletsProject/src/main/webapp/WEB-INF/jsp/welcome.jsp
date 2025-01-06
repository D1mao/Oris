<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title>TaskMaster</title>
    <style>
        body {
            margin: 0;
            font-family: Arial, sans-serif;
        }

        .header {
            background-color: #333;
            color: #fff;
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 10px 20px;
            height: 60px;
        }

        .header img {
            height: 40px;
        }

        .header .buttons {
            display: flex;
            gap: 10px;
        }

        .header .buttons a {
            text-decoration: none;
            color: #fff;
            background-color: #616161;
            padding: 10px 20px;
            border-radius: 5px;
            transition: background-color 0.3s;
        }

        .header .buttons a:hover {
            background-color: #454545;
        }

        .main {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 50px;
            background-color: #f4f4f4;
            height: calc(100vh - 60px);
        }

        .main .text {
            max-width: 50%;
        }

        .main .text h1 {
            font-size: 48px;
            color: #333;
            margin-bottom: 20px;
        }

        .main .text p {
            font-size: 20px;
            color: #666;
        }

        .main .image img {
            max-width: 100%;
            border-radius: 10px;
        }
    </style>
</head>
<body>
<div class="header">
    <img src="../../static/images/logo.png" alt="Логотип">
    <div class="buttons">
        <a href="${pageContext.request.contextPath}/register">Регистрация</a>
        <a href="${pageContext.request.contextPath}/logIn">Войти</a>
    </div>
</div>

<div class="main">
    <div class="text">
        <h1>TaskMaster</h1>
        <p>Лучшее решение для ваших проектов</p>
    </div>
    <div class="image">
        <img src="../../static/images/interface.png" alt="Дизайн интерфейса">
    </div>
</div>

</body>
</html>
