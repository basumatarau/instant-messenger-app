$.when(
    $.ready
).then(
    function() {
        webSocketHandler.establishWsConnection.apply(
            null,
            ["ws",
            "${hostIpAddress}",
            8080,
            "<%=request.getContextPath()%>/messaging/${currentContact.owner.id}",
            ${sessionScope.user.id},
            ${currentContact.person.id}]
        );
    }
).then(
    function() {
        $("#send_message").click(webSocketHandler.onSendClick);
    }
).then(
    function() {
        $("#message_input").keypress(
            function (event){
                var keycode = (event.keyCode ? event.keyCode : event.which);
                if(keycode == '13'){
                    webSocketHandler.onSendClick();
                }
                event.stopPropagation();
            }
        );
    }
).then(
    function() {
        let messages_div = $("#messages");
        messages_div.scrollTop(messages_div.prop("scrollHeight"));
    }
 );

var webSocketHandler = new function() {
    this.webSocket = null;

    this.establishWsConnection = function() {
        var ws_protocol = null;
        var ws_hostname = null;
        var ws_port = null;
        var ws_endpoint = null;
        var userId = null;
        var interlocutorId = null;

        ws_protocol = arguments[0];
        ws_hostname = arguments[1];
        ws_port     = arguments[2];
        ws_endpoint = arguments[3];
        userId = arguments[4];
        interlocutorId = arguments[5];

        openWSConnection(ws_protocol, ws_hostname, ws_port, ws_endpoint);

        function openWSConnection(protocol, hostname, port, endpoint) {
            var webSocketURL = null;
            webSocketURL = protocol + "://" + hostname + ":" + port + endpoint;
            console.log("openWSConnection::Connecting to: " + webSocketURL);
            try {
                webSocket = new WebSocket(webSocketURL);
                webSocket.onopen = function(openEvent) {
                    console.log("WebSocket OPEN: " + JSON.stringify(openEvent, null, 4));
                };
                webSocket.onclose = function (closeEvent) {
                    console.log("WebSocket CLOSE: " + JSON.stringify(closeEvent, null, 4));
                };
                webSocket.onerror = function (errorEvent) {
                    console.log("WebSocket ERROR: " + JSON.stringify(errorEvent, null, 4));
                };
                webSocket.onmessage = function (messageEvent) {
                    var wsMsg = messageEvent.data;
                    console.log("WebSocket MESSAGE: " + wsMsg);
                    if (wsMsg.indexOf("error") > 0) {

                        var err_para = document.createElement('p');
                        err_para.setAttribute('class','item-fixed');
                        err_para.innerHTML = 'error: ' + wsMsg.error + "\r\n" + 'network issues (try reloading the page)';
                        var util_div = document.getElementById('util_message_div');

                        while(util_div.firstChild){
                            util_div.removeChild(util_div.firstChild);
                        }
                        util_div.appendChild(err_para);

                    } else {

                        var msg = JSON.parse(messageEvent.data);

                        if(interlocutorId == msg.author.id){
                            var incoming_div = document.createElement('div');
                            incoming_div.setAttribute('class', "incoming_msg");

                            var received_div_img = document.createElement('div');
                            received_div_img.setAttribute('class', "incoming_msg_img");

                            var img = document.createElement('img');
                            img.setAttribute('src', "https://ptetutorials.com/images/user-profile.png");
                            img.setAttribute('alt', msg.author.nName);

                            received_div_img.appendChild(img);
                            incoming_div.appendChild(received_div_img);

                            var received_div_msg = document.createElement('div');
                            received_div_msg.setAttribute('class', "received_msg");

                            var received_div_withd_msg = document.createElement('div');
                            received_div_withd_msg.setAttribute('class', "received_withd_msg");
                            received_div_msg.appendChild(received_div_withd_msg);

                            var msg_para = document.createElement('p');
                            msg_para.innerHTML = msg.body;

                            var msg_span = document.createElement('span');
                            msg_span.setAttribute('class', "time_date");
                            msg_span.innerHTML = msg.timesent;
                            received_div_withd_msg.appendChild(msg_para);
                            received_div_withd_msg.appendChild(msg_span);

                            incoming_div.appendChild(received_div_msg);
                            document.getElementById("messages").appendChild(incoming_div);
                        }else if(userId == msg.author.id){
                            var outgoing_div = document.createElement('div');
                            outgoing_div.setAttribute('class', "outgoing_msg");

                            var sent_message = document.createElement('div');
                            sent_message.setAttribute('class', "sent_msg");

                            outgoing_div.appendChild(sent_message);

                            var msg_para = document.createElement('p');
                            msg_para.innerHTML = msg.body;

                            var msg_span = document.createElement('span');
                            msg_span.setAttribute('class', "time_date");
                            msg_span.innerHTML = msg.timesent;

                            sent_message.appendChild(msg_para);
                            sent_message.appendChild(msg_span);

                            document.getElementById("messages").appendChild(outgoing_div);
                        }else{
                            var contacts = document.getElementsByClassName("chat_people");
                            for (contact in contacts){
                                  if(contacts[contact].id == '' + msg.author.id){
                                        contacts[contact].getElementsByClassName("chat_date")[0].innerHTML = msg.timesent;
                                        if(msg.author.id == userId){
                                            contacts[contact].getElementsByClassName("message_para")[0].textContent = 'you wrote: ' + msg.body;
                                        }else{
                                            contacts[contact].getElementsByClassName("message_para")[0].textContent = msg.author.nName + ' wrote: ' + msg.body;
                                        }
                                        var cloned = contacts[contact].getElementsByClassName("you_wrote_para")[0].clone;

                                    break;
                                }
                            }

                            console.log('new contact has sent a message...');
                            var inbox_contacts = document.getElementById("inbox_contacts");
                        }

                        var messages_div = $("#messages");
                        messages_div.scrollTop(messages_div.prop("scrollHeight"));
                    }
                };

            } catch (exception) {
                console.error(exception);
            }
        }
    }

    this.onSendClick = function() {
        if (webSocket.readyState != WebSocket.OPEN) {
            console.error("webSocket is not open: " + webSocket.readyState);
            return;
        }
        var newMessage = {};
        newMessage.body = document.getElementById("message_input").value || "";
        newMessage.contactId = document.getElementById("contactId").value;
        webSocket.send(JSON.stringify(newMessage));

        $("#message_input").val("");
    }
};



