<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html lang="en">
<head>
    <%@ include file="header.html" %>
    <style>
        <%@ include file="css/search-bar.css" %>
    </style>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.css"
        type="text/css" rel="stylesheet">
</head>
<body>
<div class="frame_container">
    <%@ include file="menu.jsp" %>
    <div class="container">
        <p>${error}</p>
        <p>${message}</p>

        <h2>Contact list</h2>

        <form class="search-form" action="q?command=ContactList" method="post">
          <div class="input-group">
            <input type="text" class="form-control" name="like" placeholder="Search for contacts">
            <div class="input-group-append">
              <button class="btn btn-primary" type="submit">
                <i class="fa fa-search"></i>
              </button>
            </div>
          </div>
        </form>
        </div>

        <div class="container">
        <% if (request.getAttribute("userSearchResults") != null) { %>
            <c:choose>
                 <c:when test="${not empty userSearchResults}">
                    <h3>search results:</h3>
                    <div class="search-result-table">
                        <table class="table">
                            <thead>
                                <tr>
                                    <th scope="col">nickname</th>
                                    <th scope="col">email</th>
                                    <th scope="col">name</th>
                                    <th scope="col">status</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${userSearchResults}" var="user">
                                    <form class="contact-form" action="q?command=ContactList" method="post">
                                        <tr>
                                            <td>${user.nName}</td>
                                            <td>${user.email}</td>
                                            <td>${user.fName} ${user.lName}</td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${user.status eq 'MY_FRIEND'}">
                                                         <input name="unfriend" type="hidden" value="${user.id}">
                                                         <input type="submit" value="unfriend" class="btn btn-danger"/>
                                                    </c:when>
                                                    <c:when test="${user.status eq 'PENDING'}">
                                                        <p>friend request has been sent</p>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <input name="sendRequest" type="hidden" value="${user.id}">
                                                        <input type="submit" value="friend request" class="btn btn-success"/>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                        </tr>
                                    </form>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                 </c:when>
                 <c:otherwise>
                    <h3>no users found...</h3>
                 </c:otherwise>
            </c:choose>

        <% } else {%>

            <c:choose>
                 <c:when test="${not empty userContacts}">
                    <h3>User contacts:</h3>
                    <div class="user-contacts-table">
                        <table class="table">
                            <thead>
                                <tr>
                                    <th scope="col">nickname</th>
                                    <th scope="col">email</th>
                                    <th scope="col">name</th>
                                    <th scope="col"></th>
                                    <th scope="col">status</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${userContacts}" var="contact">

                                        <tr>
                                            <td>${contact.person.nName}</td>
                                            <td>${contact.person.email}</td>
                                            <td>${contact.person.fName} ${contact.person.lName}</td>
                                            <td>
                                                <form class="contact-form" action="q?command=Chat" method="post">
                                                    <c:choose>
                                                        <c:when test="${contact.confirmed}">
                                                            <input name="messageToContactId" type="hidden" value="${contact.id}">
                                                            <input type="submit" name="startConversation" value="message" class="btn btn-info"/>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <p>confirm to start chatting</p>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </form>
                                            </td>
                                            <td>
                                            <form class="contact-form" action="q?command=ContactList" method="post">
                                                <c:choose>
                                                    <c:when test="${contact.confirmed}">
                                                        <input name="deleteContact" type="hidden" value="${contact.id}">
                                                        <input type="submit" value="delete contact" class="btn btn-danger"/>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <input  type="hidden" name="contactId" value="${contact.id}">
                                                        <div class="row">
                                                        <input type="submit" name="confirmRequest" value="confirm" class="btn btn-success"/>
                                                        <input type="submit" name="declineRequest" value="decline" class="btn btn-warning"/>
                                                        </div>
                                                    </c:otherwise>
                                                </c:choose>
                                                </form>
                                            </td>
                                        </tr>

                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                 </c:when>
                 <c:otherwise>
                    <h3>no contacts so far...</h3>
                 </c:otherwise>
            </c:choose>
        <% } %>
        </div>


    </div>
</div>
</body>
</html>