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
    <script type="text/javascript">
        <%@include file="js/chatPageContactSwitch.js" %>
    </script>

    <script type="text/javascript">
        <%@include file="js/wsmessengerclient.js" %>
    </script>

<div class="frame_container">
<%@ include file="menu.jsp" %>
<div id="util_message_div" class="container">
</div>
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
                    <c:forEach items="${contactsForUser}" var="contactEntry">
                        <div class="chat_list <c:if test="${(not empty currentContact) && (currentContact.id == contactEntry.key.id)}"> active_chat</c:if>">
                            <div class="chat_people">
                                <form action="q?command=Chat" method="post" hidden>
                                    <input name="messageToContactId" value="${contactEntry.key.id}" hidden>
                                </form>
                                <div class="chat_img"> <img src="https://ptetutorials.com/images/user-profile.png" alt="${contactEntry.key.person.nName}"> </div>
                                <div class="chat_ib">
                                    <h5> ${contactEntry.key.person.nName} <span class="chat_date">${contactEntry.value.timesent}</span></h5>
                                    <c:choose>
                                        <c:when test="${sessionScope.user.id == contactEntry.value.author.id}">
                                            <p> <span class="you_wrote_para">you wrote: </span>${contactEntry.value.body}</p>
                                        </c:when>
                                        <c:otherwise>
                                            <p> <span class="you_wrote_para">${contactEntry.value.author.nName} wrote: </span>${contactEntry.value.body}</p>
                                        </c:otherwise>
                                    </c:choose>

                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>

            </div>

            <div class="mesgs">
                <% if (request.getAttribute("currentContact") != null) { %>
                    <div id="messages" class="msg_history">
                        <c:forEach items="${conversation}" var="message">
                            <c:choose>
                                <c:when test="${message.author.id != sessionScope.user.id}">
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
                            <input id="message_input" name="message_input" type="text" class="write_msg" placeholder="Type a message" />
                            <input id="contactId" type="text" value=${currentContact.id} hidden/>
                            <button id="send_message" name="send_message" class="msg_send_btn" type="button"><i class="fa fa-paper-plane-o" aria-hidden="true"></i></button>
                        </div>
                    </div>
                <% } else {%>
                    <div class="container">
                        <p id="hint-para">select one of the recent conversations</p>
                        <a href="<%=request.getContextPath()%>/q?command=ContactList">
                            <p id="hint-para">(or start a new one)</p>
                        </a>
                    </div>
                <% } %>
            </div>

        </div>

    </div>

</div>
</div>
</body>
</html>