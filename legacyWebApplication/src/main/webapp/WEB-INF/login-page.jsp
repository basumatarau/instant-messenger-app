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
        <form class="form-horizontal  col-lg-6 offset-lg-3" action="q?command=Logination" method="post">
        <fieldset>

        <!-- Form Name -->
        <legend>Login</legend>

        <!-- Text input-->
        <div class="form-group row justify-content-center">
          <label class="col-md-4 control-label" for="logininput">Email</label>
          <div class="col-md-6">
          <input id="logininput" name="logininput" type="text" placeholder="" value="testLogin" class="form-control input-md" required="">

          </div>
        </div>

        <!-- Password input-->
        <div class="form-group row justify-content-center">
          <label class="col-md-4 control-label" for="passwordinput">Password</label>
          <div class="col-md-6">
            <input id="passwordinput" name="passwordinput" type="password" placeholder="" value="testpassword" class="form-control input-md" required="">

          </div>
        </div>

        <!-- Button -->
        <div class="form-group row justify-content-center">
          <label class="control-label" for="singlebutton"></label>
          <div class="col-md-3">
            <button id="singlebutton" name="singlebutton" class="btn btn-primary">Login</button>
          </div>
        </div>

        </fieldset>
        </form>

    </div>
</body>
</html>