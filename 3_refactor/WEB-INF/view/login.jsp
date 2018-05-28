<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <style>
        .hide {
            display: none;
        }

        .show {
            display: inline;
            color: red;
        }
    </style>
</head>
<body>
<c:if test="${display==null}">
<c:set var="display" value="hide" scope="request"></c:set>
</c:if>
<p class="${display}">user exist!</p>
<form action="/login" method="post" id="sign_register">
    <input name="name" placeholder="input your name">
    <input name="password" placeholder="input your password">
    <button type="submit">SignIn</button>
    <button type="submit" change="/register" parentId="sign_register">Register</button>
</form>
<script src="/js/changeAction.js"></script>
<%--<script src="/js/wsChannel.js"></script>--%>
</body>
</html>