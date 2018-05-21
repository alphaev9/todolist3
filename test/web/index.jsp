<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/5/18
  Time: 20:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>$Title$</title>
</head>
<body>

<form action="/memo" method="post" enctype="multipart/form-data">
    <h1>ToDoList</h1>
    <input type="text" placeholder="backlog title" name="title"><br>
    <input type="text" placeholder="describe the backlog" name="description"><br>
    <input type="date" name="dueTime"><br>
    <input type="file" name="attachment"><br>
    <input placeholder="input save location" name="location"><br>

    <input name="cooperators[0].name" placeholder="please input your cooperator name">
    <input name="cooperators[0].email" placeholder="please input your cooperator email">
    <button type="button" id="addCooperator" index="0">+</button>
    <br>
    <button type="submit">memo</button>
</form>
<script src="jquery-3.2.0.js"></script>
<script src="addCooperator.js"></script>

</body>
</html>
