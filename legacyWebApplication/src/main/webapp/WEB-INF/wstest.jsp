<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
      <meta http-equiv="Content-Security-Policy"  content="connect-src * 'unsafe-inline';">
        <script src="wsclient.js"></script>
        <style>
            table    { border: 2px solid black; }
            input    { width: 300px; }
            select   { width: 300px; }
            textarea { width: 513px; border: 2px solid black; }
            #btnConnect    { width: 100px; }
            #btnDisconnect { width: 100px; }
            #btnSend       { width: 100px; }
        </style>
    </head>
    <body>
        <script type="text/javascript">
            <%@include file="js/wsclient.js" %>
        </script>
        <h1>WebSocket Client</h1>
        <!-- WebSocket Connection Parameters Table -->
        <table>
            <tr>
                <td width="200px">WS Protocol</td>
                <td>
                    <select id="protocol">
                        <option value="ws" selected="selected">ws</option>
                        <option value="wss">wss</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td>WS Hostname</td>
                <td><input type="text" id="hostname"/></td>
            </tr>
            <tr>
                <td>WS Port</td>
                <td><input type="text" id="port"/></td>
            </tr>
            <tr>
                <td>WS Endpoint</td>
                <td><input type="text" id="endpoint"/></td>
            </tr>
            <tr>
                <td></td>
                <td>
                    <input id="btnConnect"    type="button" value="Connect"    onclick="onConnectClick()">&nbsp;&nbsp;
                    <input id="btnDisconnect" type="button" value="Disconnect" onclick="onDisconnectClick()" disabled="disabled">
                </td>
            </tr>
        </table><br/>
        <!-- Send Message Table -->
        <table>
            <tr>
                <td width="200px">Message</td>
                <td><input type="text" id="message"/></td>
            </tr>
            <tr>
                <td></td>
                <td>
                    <input id="btnSend" type="button" value="Send Message" disabled="disabled" onclick="onSendClick()">
                </td>
            </tr>
        </table><br/>
        <textarea id="incomingMsgOutput" rows="10" cols="20" disabled="disabled"></textarea>
    </body>
</html>