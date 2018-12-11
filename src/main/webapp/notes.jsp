<%@ page import="model.NotesViewModel" %>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="java.nio.charset.StandardCharsets" %>
<% NotesViewModel vm = (NotesViewModel) request.getAttribute("viewModel");%>
<!DOCTYPE html>
<html>

<head>
    <link rel="stylesheet" type="text/css" href="content/css/notesStyle.css">
    <link href="https://fonts.googleapis.com/css?family=Roboto" rel="stylesheet">
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0">
    <title>Notes</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script>
        var ws = new WebSocket((document.location.protocol === "http:" ? "ws://" : "wss://") + document.location.host +
            "/docws/<%=URLEncoder.encode(request.getParameter("group"), StandardCharsets.UTF_8.toString())%>/<%=URLEncoder.encode(request.getParameter("wobj"), StandardCharsets.UTF_8.toString())%>?edit=<%=vm.isEdit()%>");
        ws.onmessage = function (ev) {
            var msg = JSON.parse(ev.data);
            switch (msg.action) {
                case "addText":
                    break;
            }
        };

        $('#contentText').keypress(function (ev) {
            ws.send(JSON.stringify({"action": "sendKey", "key": String.fromCharCode(ev.which)}));
        });
    </script>
</head>

<body>
<header class="header">
    <h1 class="header__h1">Online Collab Edit</h1>
</header>
<div class="doc">
    <div class="doc__background-ribbon"></div>
    <div class="doc__text-editor hidden">
        <%
            if (vm.getTitle() == null) {
                vm.setTitle("Title");
            }
            if (vm.getContent() == null) {
                vm.setContent("");
            }
            if (vm.isEdit()) {
        %>
        <input type="text" id="titleText" value="<%=vm.getTitle()%>">
        <br>
        <textarea id="contentText"><%=vm.getContent()%></textarea>
        <%
        } else {
        %>
        <div id="titleText"><%=vm.getTitle()%>
        </div>
        <div id="contentText"><%=vm.getContent()%>
        </div>
        <%
            }
        %>
    </div>
    <button id="exit"
            onclick="location.href='/index?group=<%=URLEncoder.encode(request.getParameter("group"), StandardCharsets.UTF_8.toString())%>';">
        Exit
    </button>
</div>
<br>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="app.js"></script>
</body>
</html>