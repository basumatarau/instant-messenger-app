$.when(

).then(
    function() {
        establishWsConnection.apply(
            null,
            ["ws",
            "192.168.0.86",
            8080,
            "/legacyWebApplication/messaging/${currentContact.owner.id}",
            ${sessionScope.user.id}]
        );
    }
).then(
    function() {
        $("#send_message").click(onSendClick);
    }
).then(
    function() {
        $("#message_input").keypress(
            function (event){
                var keycode = (event.keyCode ? event.keyCode : event.which);
                if(keycode == '13'){
                    onSendClick();
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




var webSocket = null;
var ws_protocol = null;
var ws_hostname = null;
var ws_port = null;
var ws_endpoint = null;
var userId = null;

function establishWsConnection() {
    ws_protocol = arguments[0];
    ws_hostname = arguments[1];
    ws_port     = arguments[2];
    ws_endpoint = arguments[3];
    userId = arguments[4];

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

                    document.getElementById("incomingMsgOutput").value += "error: " + wsMsg.error + "\r\n";
                } else {

                    var msg = JSON.parse(messageEvent.data);

                    if(userId != msg.author.id){
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
                    }else{
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
                    }
                }
            };

        } catch (exception) {
            console.error(exception);
        }
    }
}

function onSendClick() {
    if (webSocket.readyState != WebSocket.OPEN) {
        console.error("webSocket is not open: " + webSocket.readyState);
        return;
    }
    var newMessage = {};
    newMessage.body = document.getElementById("message_input").value || "";
    newMessage.contactId = document.getElementById("contactId").value;
    webSocket.send(JSON.stringify(newMessage));

    $("#message_input").val("");
    var messages_div = $("#messages");
    messages_div.scrollTop(messages_div.prop("scrollHeight"));
}

