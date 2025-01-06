<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title>${project.name}</title>
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

        /* Контейнер задач */
        .tasks-container {
            display: flex;
            flex-direction: column;
            align-items: center;
            padding: 20px;
        }

        .task-card {
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

        .task-card:hover {
            transform: scale(1.02);
        }

        .task-card.completed {
            background-color: #abdab1;
        }

        .task-title {
            font-size: 18px;
            font-weight: bold;
            color: #333;
        }

        .task-status {
            font-size: 14px;
            color: #666;
            margin-top: 10px;
        }

        .task-username {
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

    <div class="title">${project.name}</div>

    <div class="tasks-container">
        <c:forEach var="task" items="${projectTasks}">
            <div class="task-card ${task.status == 'design done' || task.status == 'develop done' || task.status == 'test done' || task.status == 'deploy done' || task.status == 'review done' ? 'completed' : ''}"
                 onclick="location.href='${pageContext.request.contextPath}/taskPage?id=${task.id}'">
                <div>
                    <div class="task-title">${task.title}</div>
                    <div class="task-status">Статус: ${task.status}</div>
                </div>
                <div class="task-username">
                    Назначено: ${task.assignedUser != null ? task.assignedUser : "Не назначено"}
                </div>
            </div>
        </c:forEach>
    </div>
</body>
</html>

