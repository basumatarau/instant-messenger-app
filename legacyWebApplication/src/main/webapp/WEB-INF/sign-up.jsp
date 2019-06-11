<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html lang="en">
<head>
    <%@ include file="header.html" %>

    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.css"
        type="text/css" rel="stylesheet">
</head>
<body>
<div>
<%@ include file="menu.jsp" %>
    <div class="container">
            <p>${error}</p>
            <form class="form-horizontal col-lg-6 offset-lg-3" id="singUpForm" action="q?command=Signup" method="post">

                <fieldset>
                    <!-- Form Name -->
                    <legend>Sign-up</legend>

                    <!-- NickName input-->
                    <div class="form-group row justify-content-center">
                      <label class="col-md-4 control-label" for="nickNameInput">Login*</label>
                      <div class="col-md-6">
                      <input id="nickNameInput" name="nickNameInput" type="text" value="testNickName" class="form-control input-md" required="">
                      </div>
                    </div>

                    <!-- Email input-->
                    <div class="form-group row justify-content-center">
                      <label class="col-md-4 control-label" for="emailInput">Email*</label>
                      <div class="col-md-6">
                      <input id="emailInput" name="emailInput" type="text" placeholder="" value="testemail@mail.com" class="form-control input-md" required="">
                      </div>
                    </div>

                    <!-- Password input-->
                    <div class="form-group row justify-content-center">
                      <label class="col-md-4 control-label" for="passwordInput">Password*</label>
                      <div class="col-md-6">
                        <input id="passwordInput" name="passwordInput" type="password" placeholder="" value="stub" class="form-control input-md" required="">
                      </div>
                    </div>

                    <!-- FirstName input (optional)-->
                    <div class="form-group row justify-content-center">
                      <label class="col-md-4 control-label" for="firstNameInput">First name (optional)</label>
                      <div class="col-md-6">
                      <input id="firstNameInput" name="firstNameInput" type="text" placeholder="" value="testFirstNameInput" class="form-control input-md" >
                      </div>
                    </div>

                    <!-- LastName input (optional)-->
                    <div class="form-group row justify-content-center">
                      <label class="col-md-4 control-label" for="lastNameInput">Last name (optional)</label>
                      <div class="col-md-6">
                      <input id="lastNameInput" name="lastNameInput" type="text" placeholder="" value="testLastNameInput" class="form-control input-md" >
                      </div>
                    </div>

                    <!-- Button -->
                    <div class="form-group row justify-content-center">
                      <label class="control-label" for="signupsubmit"></label>
                      <div class="col-md-3">
                        <button id="signupsubmit" name="signupsubmit" type="submit" class="btn btn-primary">Sing me up</button>
                      </div>
                    </div>

                </fieldset>

            </form>
    </div>
</body>
</html>