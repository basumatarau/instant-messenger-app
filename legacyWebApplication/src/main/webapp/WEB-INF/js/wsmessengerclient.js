$.when().then(
    function() {
        establishWsConnection.apply(
            null,
            ["ws",
            "localhost",
            8080,
            "/legacyWebApplication/messaging/${currentContact.owner.id}"]
        );
    }
).then(
    function() {
        $("#send_message").click(onSendClick);
    }
);

var webSocket = null;
var ws_protocol = null;
var ws_hostname = null;
var ws_port = null;
var ws_endpoint = null;

function establishWsConnection() {
    ws_protocol = arguments[0];
    ws_hostname = arguments[1];
    ws_port     = arguments[2];
    ws_endpoint = arguments[3];

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

                    document.getElementById("inbox_chat").value += "message: " + wsMsg + "\r\n";
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
}

