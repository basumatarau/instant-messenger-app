<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ page isELIgnored="false" %>

<html lang="en">
<head>
    <%@ include file="header.html" %>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.css"
        type="text/css" rel="stylesheet">
</head>
<body>
<div class="frame_container">
<%@ include file="menu.html" %>
    <div class="container">
        <p>${error}</p>
        <h2>Contact list</h2>

    </div>
</body>
</html>