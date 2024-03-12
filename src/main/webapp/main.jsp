<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<style>
    <%@include file="css/main.css"%>
</style>
<html>
<head>
    <title>FileManager</title>
</head>
<body>
<p>${currentTime}</p>
<form method="post" action="/files">
    <input type="submit" name="submit" value="Logout">
</form>
<h2>${currentPath}</h2>
<hr>
<c:if test="${pathToUp != null}">
    <img class="icon" src="${pageContext.request.contextPath}/img/up.png" alt="">
    <a href="/files?path=${pathToUp}">Вверх</a>
</c:if>
<table>
    <tr>
        <th>Файл</th>
        <th>Размер</th>
        <th>Дата</th>
    </tr>
    <c:forEach items="${listOfFiles}" var="file">
        <tr>
            <td>
                <c:choose>
                    <c:when test="${file.isFile()}">
                        <form style="margin-block-end: 0;" method="post" action="/download?path=${file.getAbsolutePath()}">
                            <img class="icon" src="/img/file.png" alt="">
                            <input class="file-path" type="submit" value="${file.getAbsolutePath()}"/>
                        </form>
                    </c:when>
                    <c:otherwise>
                        <img class="icon" src="/img/folder.png" alt="">
                        <a class="file-path" href="/files?path=${file.getAbsolutePath()}">${file.getAbsolutePath()}</a>
                    </c:otherwise>
                </c:choose>
            </td>
            <td>
                <c:if test="${file.isFile()}">
                    <p>${file.length()}B</p>
                </c:if>
            </td>
            <td>
                    ${creationTime}
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>