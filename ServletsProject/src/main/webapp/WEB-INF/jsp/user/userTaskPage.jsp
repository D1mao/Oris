<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title>${task.title}</title>
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

        /* Информация о задаче */
        .task-info {
            margin: 20px auto;
            width: 60%;
            background-color: #fff;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
            padding: 20px;
            font-size: 16px;
            color: #333;
        }

        .task-info div {
            margin-bottom: 10px;
        }

        /* Форма для добавления комментария */
        .add-comment-form {
            margin: 20px auto;
            width: 60%;
            background-color: #fff;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
            padding: 20px;
        }

        .add-comment-form textarea {
            width: 100%;
            height: 100px;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 5px;
            font-size: 14px;
            margin-bottom: 10px;
        }

        .add-comment-form button {
            background-color: #616161;
            color: #fff;
            padding: 10px 20px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.3s;
        }

        .add-comment-form button:hover {
            background-color: #454545;
        }

        /* Контейнер комментариев */
        .comments-container {
            display: flex;
            flex-direction: column;
            align-items: center;
            padding: 20px;
        }

        .comment-card {
            background-color: #fff;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
            width: 80%;
            padding: 20px;
            margin: 10px 0;
            display: flex;
            justify-content: space-between;
            flex-direction: column;
        }

        .comment-content {
            font-size: 16px;
            color: #333;
            margin-bottom: 10px;
        }

        .comment-info {
            display: flex;
            justify-content: space-between;
            font-size: 14px;
            color: #666;
        }
    </style>
    <script>
        function renderComments(comments, container) {
            let innerHtml = '';

            for (let i = 0; i < comments.length; i++) {
                innerHtml += '<div class="comment-card">';
                innerHtml += '  <div class="comment-content">' + comments[i]['content'] + '</div>';
                innerHtml += '  <div class="comment-info">';
                innerHtml += '    <span>Автор: ' + comments[i]['username'] + '</span>';
                innerHtml += '    <span>Создано: ' + comments[i]['createdAt'] + '</span>';
                innerHtml += '  </div>';
                innerHtml += '</div>';
            }

            container.html(innerHtml);
        }

        // Отправка нового комментария на сервер
        function sendComment(taskId) {
            const content = $('#new-comment-content').val();

            if (content.trim() === '') {
                alert('Комментарий не может быть пустым');
                return;
            }

            let data = {
                "taskId": taskId,
                "content": content
            };

            $.ajax({
                type: "POST", // метод запроса
                url: "/taskPage", // URL для обработки запроса на сервере
                data: JSON.stringify(data), // данные для отправки в формате JSON
                // что происходит, если запрос прошел успешно?
                success: function (response) {
                    // Обновляем список комментариев на основе ответа
                    renderComments(response, $('.comments-container'));
                    $('#new-comment-content').val(''); // очищаем поле ввода
                },
                // что происходит, если запрос не прошел?
                error: function () {
                    alert('Не удалось добавить комментарий');
                },
                dataType: "json", // тип данных, который ожидается в ответе
                contentType: "application/json" // тип отправляемых данных
            });
        }
    </script>
</head>
<body>
    <div class="header">
        <img src="${pageContext.request.contextPath}/static/images/logo.png" alt="Логотип">
        <div class="username">${sessionScope.username}</div>
    </div>

    <div class="title">${task.title}</div>

    <div class="task-info">
        <div><strong>Описание:</strong> ${task.description}</div>
        <div><strong>Статус:</strong> ${task.status}</div>
        <div><strong>Время создания:</strong> ${task.createdAt}</div>
        <div><strong>Дедлайн:</strong> ${task.dueDate}</div>
        <div><strong>Закреплено за:</strong> ${task.assignedUser}</div>
    </div>

    <div class="add-comment-form">
        <textarea id="new-comment-content" placeholder="Введите ваш комментарий..."></textarea>
        <button onclick="sendComment(${task.id})">Добавить комментарий</button>
    </div>

    <div class="comments-container">
        <c:forEach var="comment" items="${taskComments}">
            <div class="comment-card">
                <div class="comment-content">${comment.content}</div>
                <div class="comment-info">
                    <span>Автор: ${comment.userName}</span>
                    <span>Создано: ${comment.createdAt}</span>
                </div>
            </div>
        </c:forEach>
    </div>
</body>
</html>


