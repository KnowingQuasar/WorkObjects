<%@ page import="model.NotesViewModel" %>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="java.nio.charset.StandardCharsets" %>
<!DOCTYPE html>
<html>

<head>
    <link rel="stylesheet" type="text/css" href="content/css/notesStyle.css">
    <link href="https://fonts.googleapis.com/css?family=Roboto" rel="stylesheet">
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0">
    <title>Notes</title>
</head>

<body>
<header class="header">
    <h1 class="header__h1">Online Collab Edit</h1>
</header>
<div class="doc">
    <div class="doc__background-ribbon"></div>
    <div class="doc__text-editor hidden">
        <%
            NotesViewModel vm = (NotesViewModel) request.getAttribute("viewModel");
            if (vm.isEdit()) {
        %>
        <input type="text" id="titleText" value="<%=vm.getTitle()%>" style="color:black">
        <br>
        <textarea id="contentText" style="color:black"><%=vm.getContent()%></textarea>
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
    <button id="exit" onclick="location.href='/index?group=<%=URLEncoder.encode(request.getParameter("group"), StandardCharsets.UTF_8.toString())%>';">Exit</button>
</div>
<br>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="app.js"></script>
</body>
</html>