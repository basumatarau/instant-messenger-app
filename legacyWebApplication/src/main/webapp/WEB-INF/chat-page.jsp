<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<head>
    <%@ include file="header.html" %>
    <style>
        <%@ include file="css/chat-page.css" %>
    </style>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.css"
        type="text/css" rel="stylesheet">
</head>
<body>
<div class="frame_container">
<%@ include file="menu.jsp" %>

<div class="container">

    <div class="messaging">
        <div class="inbox_msg">
            <div class="inbox_people">
                <div class="headind_srch">
                    <div class="recent_heading">
                        <h4>Recent</h4>
                    </div>
                </div>

                <div class="inbox_chat">

                    <c:forEach items="${contactsForUser}" var="contact">
                        <div class="chat_list active_chat">
                            <div class="chat_people">
                                <div class="chat_img"> <img src="https://ptetutorials.com/images/user-profile.png" alt="${contact.person.nName}"> </div>
                                <div class="chat_ib">
                                    <h5>${contact.person.fName} ${contact.person.lName}<span class="chat_date">[date last msg]</span></h5>
                                    <p> test (last sent message) </p>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
<%--
                    <div class="chat_list">
                        <div class="chat_people">
                            <div class="chat_img"> <img src="https://ptetutorials.com/images/user-profile.png" alt="sunil"> </div>
                            <div class="chat_ib">
                                <h5>Sunil Rajput <span class="chat_date">[date last msg]</span></h5>
                                <p>Test, which is a new approach to have all solutions
                                    astrology under one roof.</p>
                            </div>
                        </div>
                    </div>
--%>
                </div>
            </div>

            <div class="mesgs">
                <div class="msg_history">
                    <c:forEach items="${conversation}" var="message">
                        <c:choose>
                            <c:when test="${message.author.id == sessionScope.user.id}">
                                <div class="incoming_msg">
                                    <div class="incoming_msg_img"> <img src="https://ptetutorials.com/images/user-profile.png" alt="${message.author.nName}"> </div>
                                    <div class="received_msg">
                                        <div class="received_withd_msg">
                                            <p>${message.body}</p>
                                            <span class="time_date"> ${message.timesent} </span></div>
                                    </div>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div class="outgoing_msg">
                                    <div class="sent_msg">
                                        <p>${message.body}</p>
                                        <span class="time_date"> ${message.timesent} </span> </div>
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </div>
                <div class="type_msg">
                    <div class="input_msg_write">
                        <input type="text" class="write_msg" placeholder="Type a message" />
                        <button class="msg_send_btn" type="button"><i class="fa fa-paper-plane-o" aria-hidden="true"></i></button>
                    </div>
                </div>
            </div>

        </div>

    </div>

</div>
</div>
</body>
</html>