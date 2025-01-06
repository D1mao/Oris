<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title>Главная</title>
    <style>
        body {
            margin: 0;
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
        }

        /* Верхний контейнер */
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

        .header .username {
            font-size: 16px;
            font-weight: bold;
        }

        /* Заголовок страницы */
        .title {
            text-align: center;
            margin: 20px 0;
            font-size: 32px;
            color: #333;
        }

        /* Контейнер проектов */
        .projects-container {
            display: flex;
            flex-direction: column;
            align-items: center;
            padding: 20px;
        }

        .project-card {
            background-color: #fff;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
            width: 80%;
            padding: 20px;
            margin: 10px 0;
            display: flex;
            justify-content: space-between;
            align-items: center;
            cursor: pointer;
            transition: transform 0.2s;
        }

        .project-card:hover {
            transform: scale(1.02);
        }

        .project-name {
            font-size: 18px;
            font-weight: bold;
            color: #333;
        }

        .project-creator {
            font-size: 16px;
            color: #666;
        }
    </style>
</head>
<body>
    <div class="header">
        <img src="${pageContext.request.contextPath}/static/images/logo.png" alt="Логотип">
        <div class="username">${sessionScope.username}</div>
    </div>

    <div class="title">Мои проекты</div>

    <div class="projects-container">
        <c:forEach var="project" items="${userProjects}">
            <div class="project-card" onclick="location.href='${pageContext.request.contextPath}/projectPageForUser?id=${project.id}'">
                <div class="project-name">${project.name}</div>
                <div class="project-creator">Создал: ${project.createdBy}</div>
            </div>
        </c:forEach>
    </div>
</body>
</html>
