<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<html>
<head>
    <title>Meals</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<form method="post" action="meals?action=${meal.id != 0 ? 'update' : 'create'}">
    <input type="hidden" name="id" value="${meal.id}">
    DATE/TIME: <input type="datetime-local" name="dateTime" value="${meal.dateTime}"><br>
    DESCRIPTION: <input type="text" name="description" value="${meal.description}"><br>
    CALORIES: <input type="number" name="calories" value="${meal.calories}" step="10"><br>
    <input type="submit" value="${meal.id != 0 ? 'update' : 'create'}">
</form>
<table>
    <thead>
    <tr>
        <th>Id</th>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
        <th colspan="2">Action</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="mealFromList" items="${meals}">
        <tr mealExcess="${mealFromList.excess}">
            <td>${mealFromList.id}</td>
            <td>${datesFormatter.formatLocalDateTime(mealFromList.dateTime, 'dd.MM.yyyy HH:mm')}</td>
            <td>${mealFromList.description}</td>
            <td>${mealFromList.calories}</td>
            <td>
                <button onclick="location.href='meals?action=update&id=${mealFromList.id}'" type="button">
                    Update
                </button>
            </td>
            <td>
                <button onclick="location.href='meals?action=delete&id=${mealFromList.id}'" type="button">
                    Delete
                </button>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
