<!DOCTYPE html>
<html>

<head>
    <link rel="stylesheet" type="text/css" href="content/css/style.css" />
    <title>Google Docs Ripoff</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script>
        function appendWorkItem() {
            var workItem = "<span class=\"workItem\"><img id = \"lock\" src = \"content/images/lock.png\" alt = \"lock\" style = \"width:32px;height:32px;\" ><button class=\"buttonDark\" id=\"viewButton\">view</button><button class=\"buttonDark\" id=\"editButton\">edit</button></span >"
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
            <a href="#">Group 1</a>
            <a href="#">Group 2</a>
            <a href="#">Overflow T e s t asdf asdf asdf</a>
            <a href="#" class="newGroupButton">New Group</a>
        </div>
    </div>
    <button class="buttonPlus" onclick="appendWorkItem()">+</button>
</div>
</body>
</html>