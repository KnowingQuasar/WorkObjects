<%@ page import="java.util.ArrayList" %>
<%@ page import="model.IndexViewModel" %>
<%@ page import="model.WorkObject" %>
<!DOCTYPE html>
<html>

<head>
    <link rel="stylesheet" type="text/css" href="content/css/style.css"/>
    <title>Google Docs Ripoff</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script>
        function appendWorkItem() {
            var workItem = "<span class=\"workItem\"><div id=\"titleDiv\"><p id=\"titleText\">Title</p></div><div id=\"contentDiv\"><p id=\"contentText\"></p></div>" +
                "<button type=\"submit\" formmethod=\"post\" class=\"buttonDark\" " +
                "id=\"viewButton\">view</button><button type=\"submit\" formmethod=\"post\" class=\"buttonDark\" id=\"editButton\">edit</button></span >"
            $("body").append(workItem);
        }

        function sendCreate() {
            $.post("/index", appendWorkItem());
        }
    </script>
</head>

<body>
<div id="top"></div>
<div id="sideBar">
    <div class="dropdown">
        <button class="dropButton">Groups</button>
        <div class="dropContent">
            <%
                for (String grp :
                        ((IndexViewModel) request.getAttribute("viewModel")).getGroups()) {
            %>
            <a href="#"><%=grp%>
            </a>
            <%
                }
            %>
            <a href="#" class="newGroupButton">New Group</a>
        </div>
    </div>
    <button class="buttonPlus" onclick="sendCreate()">+</button>
</div>
<%
    for (WorkObject wobj :
            ((IndexViewModel) request.getAttribute("viewModel")).getWobjs()) {
%>
<span class="workItem"><div id="titleDiv"><p id="TitleText"><%=wobj.getTitle()%></p></div><div id="contentDiv"><p
        id="contentText"><%=wobj.getContent()%></p></div>
    <%
        if (wobj.isLocked()) {
    %><img id="lock" src="content/images/lock.png" alt="locked" style="width:32px;height:32px;">
    <%
        }
    %>
    <button type="submit" formmethod="post" class="buttonDark" id="viewButton">View</button><button type="submit"
                                                                                                    formmethod="post"
                                                                                                    class="buttonDark"
                                                                                                    id="editButton">edit</button></span>
<%
    }
%>
</body>
</html>