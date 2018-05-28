<%@ page import="com.alpha.repository.entity.Backlog" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div>
    <h2>
        <span>${param.listTitle}</span>
        <span>${list.size()}</span>
    </h2>
    <form action="${param.action}" method="post" id="${param.formId}">
        <c:forEach items="${list}" var="backlog">
            <input type="checkbox" name="backlogId" value=${backlog.id.getRealId()}>
            ${backlog.title}
            ${backlog.description}
            <c:if test="${backlog.attachment!=null}">
                <a href="/${backlog.attachment}">download attachment</a>
            </c:if>
            ${backlog.dueTime}<br/>

            <c:if test="${!backlog.cooperators.isEmpty()}">
                <c:forEach items="${backlog.cooperators}" var="cooperator">
                    ${cooperator.name}   ${cooperator.email}<br/>
                </c:forEach>
            </c:if>
            </br>
        </c:forEach>
        <button type="submit" name="${param.btn1}">${param.btn1}</button>
        <button type="submit" name="${param.btn2}" change="${param.btn2Change}"
                parentId="${param.btn2ParentId}">${param.btn2}</button>
    </form>
</div>
