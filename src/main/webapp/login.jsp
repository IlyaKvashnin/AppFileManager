<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>
    <%@include file="css/login.css"%>
</style>
<html>
<head>
    <title>FileManager</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
</head>
<body>
<form class="form" method="post" action="/">
    <div class="form-group">
        <label >Login</label>
        <input name="login" type="text" class="form-control" placeholder="Enter login">
    </div>
    <div class="form-group">
        <label>Password</label>
        <input name="pass" type="password" class="form-control" placeholder="Password">
    </div>
    <button type="submit" class="log" style="margin-top: 10px;">Login</button>
    <div class="form-group" style="margin-top: 10px;">
        <a href="/registration">Sign in</a>
    </div>
</form>
</body>
</html>
