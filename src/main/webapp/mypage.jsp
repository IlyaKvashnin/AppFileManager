<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>FileManager</title>
</head>
<body>
<p>${currentDateTime}</p>
<h2>${pathNow}</h2>
<hr>
<c:if test="${upPath != null}">
    <a href="/files?path=${upPath}">Вверх</a>
</c:if>
<table>
    <tr>
        <th>Файл</th>
        <th>Размер</th>
        <th>Дата</th>
    </tr>
    <c:forEach items="${filesList}" var="file">
        <tr>
            <td>
                <a href="/files?path=${file.getAbsolutePath()}">${file.getAbsolutePath()}</a>
            </td>
            <td>10mb</td>
            <td>21</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>