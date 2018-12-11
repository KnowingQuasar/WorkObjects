<%@ page import="model.IndexViewModel" %>
<%@ page import="model.WorkObject" %>
<!DOCTYPE html>
<html>

<head>
    <link rel="stylesheet" type="text/css" href="content/css/style.css"/>
    <link rel="stylesheet" type="text/css" href="content/css/modal.css"/>
    <title>Google Docs Ripoff</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script>
        function appendWorkItem() {
            var grp = "<%=((IndexViewModel)request.getAttribute("viewModel")).getSelectedGrp()%>";
            var workItem =
                "<span class=\"workItem\">" +
                    "<form id=\"viewEdit\" method=\"get\" action=\"/doc\">" +
                        "<input type=\"hidden\" name=\"group\" value=\"" + grp + "\"/>" +
                        "<div id=\"titleDiv\">" +
                            "<input type=\"text\" id=\"TitleText\" readonly name=\"wobj\" value=\"Title\"/>" +
                        "</div>" +
                        "<div id=\"contentDiv\">" +
                            "<p id=\"contentText\">Sample Content</p>" +
                        "</div>" +
                        "<button type=\"submit\" class=\"buttonDark\" id=\"viewButton\" name=\"action\" value=\"view\">View</button>" +
                        "<button type=\"submit\" class=\"buttonDark\" id=\"editButton\" name=\"action\" value=\"edit\">Edit</button>" +
                    "</form>" +
                "</span>";
            $("body").append(workItem);
        }
    </script>
</head>

<body>
<div id="addGroupModal" class="modal">
    <div class="modal-content">
        <form id="newGroupForm" method="get" action="/index">
            <input id="newGroup" type="text" name="newGroup" value=""/>
            <br>
            <input type="submit" value="Submit"/>
        </form>
    </div>
</div>
<div id="top"></div>
<div id="sideBar">
    <div class="dropdown">
        <button id="selectedGrp"
                class="dropButton"><%=((IndexViewModel) request.getAttribute("viewModel")).getSelectedGrp()%>
        </button>
        <div class="dropContent">
            <%
                for (String grp :
                        ((IndexViewModel) request.getAttribute("viewModel")).getGroups()) {
            %>
            <a href="/index?group=<%=grp%>"><%=grp%>
            </a>
            <%
                }
            %>
            <a href="#" class="newGroupButton" onclick="$('#addGroupModal').show();">New Group</a>
        </div>
    </div>
    <button class="buttonPlus" onclick="$.post('/index?group=' + $('#selectedGrp').text(), appendWorkItem());">+</button>
</div>
<%
    for (WorkObject wobj :
            ((IndexViewModel) request.getAttribute("viewModel")).getWobjs()) {
%>
<span class="workItem">
    <form id="viewEdit" method="get" action="/doc">
        <input type="hidden" name="group"
               value="<%=((IndexViewModel) request.getAttribute("viewModel")).getSelectedGrp()%>"/>
        <div id="titleDiv">
            <input type="text" id="TitleText" readonly="readonly" name="wobj" value="<%=wobj.getTitle()%>"/>
        </div>
        <div id="contentDiv">
            <p id="contentText"><%=wobj.getContent()%></p>
        </div>
    <%
        if (wobj.isLocked()) {
    %>
        <img id="lock" src="content/images/lock.png" alt="locked" style="width:32px;height:32px;">
    <%
        }
    %>
        <button type="submit" class="buttonDark" id="viewButton" name="action" value="view">View</button>
        <button type="submit" class="buttonDark" id="editButton" name="action" value="edit"
                <%if(wobj.isLocked()){%>disabled<%}%>>Edit</button>
    </form>
</span>
<%
    }
%>
</body>
</html>