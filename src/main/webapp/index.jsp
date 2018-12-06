<!DOCTYPE html>
<html>

<head>
    <link rel="stylesheet" type="text/css" href="content/css/style.css" />
    <title>Google Docs Ripoff</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script>
        function appendWorkItem() {
            var workItem = "<span class=\"workItem\"><div id=\"titleDiv\"><p id=\"titleText\">Testing Title Overflow, is the Title Long Enough Yet? </p></div><div id=\"contentDiv\"><p id=\"contentText\">Here I am testing how far the text will go and still continue to display. Will it overflow correctly or not? This block of text still is not long enough for me to freaaking test! AAAAAA Here I go again typing out a bunch of useless text... I suppose the only solution is to keep typing until my fingers bleed and then continue typing.</p></div><img id = \"lock\" src = \"content/images/lock.png\" alt = \"lock\" style = \"width:32px;height:32px;\" ><button type=\"submit\" formmethod=\"post\" class=\"buttonDark\" id=\"viewButton\">view</button><button type=\"submit\" formmethod=\"post\" class=\"buttonDark\" id=\"editButton\">edit</button></span >"
            $("body").append(workItem);
        }
    </script>
</head>

<body>
<div id="top"></div>
<div id="sideBar">
    <div class="dropdown">
        <button class="dropButton">Groups</button>
        <div class="dropContent">
            <a href="notes.jsp">Group 1</a>
            <a href="#">Group 2</a>
            <a href="#">Overflow T e s t asdf asdf asdf</a>
            <a href="#" class="newGroupButton">New Group</a>
        </div>
    </div>
    <button class="buttonPlus" onclick="appendWorkItem()">+</button>
</div>
</body>
</html>