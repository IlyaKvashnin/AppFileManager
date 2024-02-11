<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>FileManager</title>
</head>
<body>
<h1>Hello ${name}</h1>
<table>
    <tr>
        <th>Файл</th>
        <th>Размер</th>
        <th>Дата</th>
    </tr>
    <c:forEach items="${filesList}" var="file">
        <tr>
            <td>${file}</td>
            <td>10mb</td>
            <td>21</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>