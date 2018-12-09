(function () {
    var editable_elements = document.querySelectorAll("[contentEditable=true]");
    for(var i=0; i<editable_elements.length; i++)
        editable_elements[i].setAttribute("contentEditable", false);


})();