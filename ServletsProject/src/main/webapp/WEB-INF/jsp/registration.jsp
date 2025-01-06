<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title>Регистрация</title>
    <style>
        body {
            margin: 0;
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }

        .form-container {
            background-color: #fff;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
            padding: 20px 40px;
            text-align: center;
            width: 350px;
        }

        .form-container h2 {
            margin-bottom: 20px;
            font-size: 24px;
            color: #333;
        }

        .form-container label {
            display: block;
            margin-bottom: 8px;
            text-align: left;
            font-size: 14px;
            color: #666;
        }

        .form-container input,
        .form-container select {
            width: 100%;
            padding: 10px;
            margin-bottom: 15px;
            border: 1px solid #ccc;
            border-radius: 5px;
            font-size: 14px;
        }

        .form-container button {
            background-color: #616161;
            color: #fff;
            padding: 10px 20px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.3s;
            width: 100%;
        }

        .form-container button:hover {
            background-color: #454545;
        }

        .form-container .error-message {
            color: #ff0000;
            font-size: 14px;
            margin-top: -10px;
            margin-bottom: 15px;
            text-align: left;
        }

        .form-container .btn {
            margin-top: 10px;
        }

        .form-container .go-to-login-btn {
            background-color: #007bff;
            color: #fff;
            padding: 10px 20px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.3s;
        }

        .form-container .go-to-login-btn:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
    <div class="form-container">
        <form action = "${pageContext.request.contextPath}/register" method="post" class="form">
            <h2>Регистрация</h2>

            <label for="username">Логин</label>
            <input type="text" id="username" name="username" required><br><br>

            <label for="email">Почта</label>
            <input type="email" id="email" name="email" required><br><br>

            <c:if test="${not empty errorMessage}">
                <p class="error-message">${errorMessage}</p>
            </c:if>

            <label for="role">Должность</label>
            <select id="role" name="role" required>
                <option value="2">Управляющий</option>
                <option value="3">Дизайнер</option>
                <option value="4">Разработчик</option>
                <option value="5">Тестировщик</option>
            </select><br><br>

            <label for="password">Пароль</label>
            <input type="password" id="password" name="password" minlength="4" required><br><br>

            <button type="submit">Зарегестрироваться</button>
        </form>

        <form action="${pageContext.request.contextPath}/logIn" method="get">
            <div class="btn">
                <button type="submit" class="go-to-login-btn">Войти</button>
            </div>
        </form>
    </div>

</body>
</html>
