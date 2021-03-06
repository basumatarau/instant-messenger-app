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

    this.frameScroller = function() {
        let messages_div = $("#messages");
        messages_div.scrollTop(messages_div.prop("scrollHeight"));
    }

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

                    var msg = JSON.parse(messageEvent.data);

                    if (wsMsg.indexOf("error") > 0) {

                        var err_para = document.createElement('p');
                        err_para.setAttribute('class','err_para');
                        err_para.innerHTML = msg.error;
                        var err_div = document.createElement('div');
                        err_div.setAttribute('class', 'err_div');
                        err_div.appendChild(err_para);

                        document.getElementById("messages").appendChild(err_div);

                        setTimeout(function(){
                          err_div.remove();
                        }, 2000);

                        var messages_div = $("#messages");
                        messages_div.scrollTop(messages_div.prop("scrollHeight"));
                    } else {
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

                            var contact = document.getElementById(interlocutorId);
                            contact.getElementsByClassName("chat_date")[0].innerHTML = msg.timesent;
                            contact.getElementsByClassName("you_wrote_para")[0].nextSibling.nodeValue = msg.body;
                            contact.getElementsByClassName("you_wrote_para")[0].childNodes[0].nodeValue =
                            msg.author.id == userId ? 'you wrote: ' : msg.author.nName + ' wrote: ';

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

                            var contact = document.getElementById(interlocutorId);
                            contact.getElementsByClassName("chat_date")[0].innerHTML = msg.timesent;
                            contact.getElementsByClassName("you_wrote_para")[0].nextSibling.nodeValue = msg.body;
                            contact.getElementsByClassName("you_wrote_para")[0].childNodes[0].nodeValue =
                                msg.author.id == userId ? 'you wrote: ' : msg.author.nName + ' wrote: ';

                        }else{
                            var contacts = document.getElementsByClassName("chat_people");
                            var presentInRecent = false;
                            for (contact in contacts){
                                  if(contacts[contact].id == '' + msg.author.id){
                                        contacts[contact].getElementsByClassName("chat_date")[0].innerHTML = msg.timesent;
                                        contacts[contact].getElementsByClassName("you_wrote_para")[0].nextSibling.nodeValue = msg.body;
                                        contacts[contact].getElementsByClassName("you_wrote_para")[0].childNodes[0].nodeValue =
                                        msg.author.id == userId ? 'you wrote: ' : msg.author.nName + ' wrote: ';
                                        presentInRecent = true;
                                    break;
                                    }
                            }
                            if(!presentInRecent){
                                var chat_list_div = document.createElement('div');
                                chat_list_div.setAttribute('class', 'chat_list');

                                var contact_entry_div = document.createElement('div');
                                contact_entry_div.setAttribute('class', 'chat_people');
                                contact_entry_div.setAttribute('id', msg.contact.owner.id);
                                chat_list_div.appendChild(contact_entry_div);

                                var action_form = document.createElement('form');
                                action_form.setAttribute('action', 'q?command=Chat');
                                action_form.setAttribute('method', 'post');
                                action_form.setAttribute('hidden', 'true');
                                contact_entry_div.appendChild(action_form);

                                var img_div = document.createElement('div');
                                img_div.setAttribute('class', 'chat_img');
                                var img = document.createElement('img');
                                img.setAttribute('src', 'https://ptetutorials.com/images/user-profile.png');
                                img.setAttribute('alt', msg.contact.person.nName);
                                img_div.appendChild(img);
                                contact_entry_div.appendChild(img_div);

                                var chat_ib_div = document.createElement('div');
                                chat_ib_div.setAttribute('class', 'chat_ib');
                                var h5 = document.createElement('h5');
                                h5.innerHTML = msg.contact.owner.nName;
                                var chat_date_span = document.createElement('span');
                                chat_date_span.setAttribute('class', 'chat_date');
                                chat_date_span.innerHTML = msg.timesent;
                                h5.childNodes[0].parentNode.insertBefore(chat_date_span,h5.childNodes[0].nextSibling);
                                chat_ib_div.appendChild(h5);

                                var message_para = document.createElement('p');
                                message_para.innerHTML = msg.contact.owner.nName + ' wrote: ';
                                var you_wrote_span = document.createElement('span');
                                you_wrote_span.setAttribute('class', 'you_wrote_para');
                                you_wrote_span.innerHTML = msg.author.nName;
                                message_para.innerHTML = msg.body.innerHTML;
                                message_para.childNodes[0].parentNode.insertBefore(you_wrote_span,message_para.childNodes[0].nextSibling);
                                chat_ib_div.appendChild(message_para);
                                contact_entry_div.appendChild(chat_ib_div);

                                document.getElementById('inbox_contacts').appendChild(chat_list_div);
                                console.log('new contact has sent a message...');
                            }
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

        if(newMessage.body.length <= 500){
            webSocket.send(JSON.stringify(newMessage));
            $("#message_input").val("");
        }else{
            var err_para = document.createElement('p');
            err_para.setAttribute('class','err_para');
            err_para.innerHTML = "the message is to big...";
            var err_div = document.createElement('div');
            err_div.setAttribute('class', 'err_div');
            err_div.appendChild(err_para);

            document.getElementById("messages").appendChild(err_div);
            var messages_div = $("#messages");
            messages_div.scrollTop(messages_div.prop("scrollHeight"));

            setTimeout(function(){
                err_div.remove();
            }, 2000);
        }
    }
};



