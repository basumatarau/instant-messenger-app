<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html lang="en">
<head>
    <%@ include file="header.html" %>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.css"
        type="text/css" rel="stylesheet">
</head>
<body>
<div class="frame_container">
<%@ include file="menu.jsp" %>
    <div class="container">

        <div class="container">
            <br/>
            <h2>Profile page</h2>
            <p>${error}</p>
            <p>${message}</p>

            <ul class="nav nav-tabs" id="myTab" role="tablist">
                <li class="nav-item">
                    <a class="nav-link active" id="profile-tab" data-toggle="tab" href="#profile" role="tab" aria-controls="profile" aria-selected="true">Profile data</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" id="password-tab" data-toggle="tab" href="#password" role="tab" aria-controls="password" aria-selected="false">Password update</a>
                </li>
            </ul>

            <div class="tab-content" id="myTabContent">
                  <div class="tab-pane fade show active" id="profile" role="tabpanel" aria-labelledby="profile-tab">
                    <br/>
                    <form class="form-horizontal col-lg-6 offset-lg-3" id="userForm" action="q?command=UserProfile" method="post">
                        <fieldset>

                            <!-- NickName input-->
                            <div class="form-group row justify-content-center">
                              <label class="col-md-4 control-label" for="nickNameInput">Nickname:</label>
                              <div class="col-md-6">
                              <input id="nickNameInput" name="nickNameInput" type="text" value="${authorizedUser.nickName}" class="form-control input-md" required="true">
                              </div>
                            </div>

                            <!-- Email input-->
                            <div class="form-group row justify-content-center">
                              <label class="col-md-4 control-label" for="emailInput">Email:</label>
                              <div class="col-md-6">
                              <input id="emailInput" name="emailInput" type="text" placeholder="" value="${authorizedUser.email}" class="form-control input-md" required="true">
                              </div>
                            </div>

                            <!-- FirstName input (optional)-->
                            <div class="form-group row justify-content-center">
                              <label class="col-md-4 control-label" for="firstNameInput">First name (optional)</label>
                              <div class="col-md-6">
                              <input id="firstNameInput" name="firstNameInput" type="text" placeholder="" value="${authorizedUser.firstName}" class="form-control input-md" >
                              </div>
                            </div>

                            <!-- LastName input (optional)-->
                            <div class="form-group row justify-content-center">
                              <label class="col-md-4 control-label" for="lastNameInput">Last name (optional)</label>
                              <div class="col-md-6">
                              <input id="lastNameInput" name="lastNameInput" type="text" placeholder="" value="${authorizedUser.lastName}" class="form-control input-md" >
                              </div>
                            </div>

                            <!-- Button -->
                            <div class="form-group row justify-content-center">
                              <label class="control-label" for="userUserData"></label>
                              <div class="col-md-4">
                                <button id="userUserData" name="userUserData" type="submit" form="userForm" class="btn btn-primary">Update profile</button>
                              </div>
                            </div>

                        </fieldset>
                    </form>
                  </div>

                  <div class="tab-pane fade" id="password" role="tabpanel" aria-labelledby="password-tab">
                    <br/>
                    <form class="form-horizontal col-lg-6 offset-lg-3" id="userPasswordForm" action="q?command=UserProfile" method="post">
                        <fieldset>
                            <input name="updatePassword" value="true" hidden/>
                            <!-- Old password input-->
                            <div class="form-group row justify-content-center">
                              <label class="col-md-4 control-label" for="oldpasswordInput">Old password:</label>
                              <div class="col-md-6">
                                <input id="oldpasswordInput" name="oldpasswordInput" type="password" placeholder="" value="" class="form-control input-md" required="true">
                              </div>
                            </div>

                            <!-- New password input-->
                            <div class="form-group row justify-content-center">
                              <label class="col-md-4 control-label" for="newpasswordInput">New password:</label>
                              <div class="col-md-6">
                                <input id="newpasswordInput" name="newpasswordInput" type="password" placeholder="" value="" class="form-control input-md" required="true">
                              </div>
                            </div>

                            <div class="form-group row justify-content-center">
                              <label class="control-label" for="userUserPassword"></label>
                              <div class="col-md-5">
                                <button id="userUserPassword" name="userUserPassword" type="submit" form="userPasswordForm" class="btn btn-primary">Update password</button>
                              </div>
                            </div>
                        </fieldset>
                    </form>
                  </div>
            </div>

        </div>
    </div>
</body>
</html>