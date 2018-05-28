<%--
组件化：把列表抽象出来
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>show todo list</title>
    <style>
        .warn {
            color: red;
        }
    </style>
</head>
<body>
<nav>
    <a href="/logout">logout</a>
    <span>welcome ${userName}</span>
</nav>
<div>
    <form action="/memo" method="post" enctype="multipart/form-data">
        <h1>ToDoList</h1>
        <input type="text" placeholder="backlog title" name="title"><br>
        <input type="text" placeholder="describe the backlog" name="description"><br>
        <input type="date" name="dueTime"><br>
        <input type="file" name="attachmentFile"><br>
        <input placeholder="input save location" name="attachment"><br>
        <input name="address.street" placeholder="input street">
        <input name="address.number" placeholder="input number"><br/>

        <input name="cooperators[0].name" placeholder="please input your cooperator name">
        <input name="cooperators[0].email" placeholder="please input your cooperator email">
        <button type="button" id="addCooperator" index="0">+</button>
        <br>
        <button type="submit">memo</button>
    </form>

    <c:set var="list" value="${pendingList}" scope="request"></c:set>
    <jsp:include page="listArea.jsp">
        <jsp:param name="listTitle" value="Pending"/>
        <jsp:param name="action" value="/removePending"/>
        <jsp:param name="formId" value="pending"/>
        <jsp:param name="btn1" value="remove"/>
        <jsp:param name="btn2" value="finish"/>
        <jsp:param name="btn2Change" value="/finish"/>
        <jsp:param name="btn2ParentId" value="pending"/>
    </jsp:include>

    <c:set var="list" value="${finishedList}" scope="request"></c:set>
    <jsp:include page="listArea.jsp">
        <jsp:param name="listTitle" value="Finished"/>
        <jsp:param name="action" value="/removeFinished"/>
        <jsp:param name="formId" value="finish"/>
        <jsp:param name="btn1" value="remove"/>
        <jsp:param name="btn2" value="redo"/>
        <jsp:param name="btn2Change" value="/redo"/>
        <jsp:param name="btn2ParentId" value="finish"/>
    </jsp:include>
</div>
<script src="/js/jquery-3.2.0.js"></script>
<script src="/js/addCooperator.js"></script>
<script src="/js/changeAction.js"></script>
<script src="/js/wsChannel.js"></script>
</body>
</html>
