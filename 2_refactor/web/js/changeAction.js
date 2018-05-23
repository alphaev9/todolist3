function changeAction() {
    var target=event.target;
    var formId="#"+target.getAttribute("parentId");
    var form=document.querySelector(formId);
    form.action=target.getAttribute("change");
}

buttons = document.querySelectorAll("button[change]");
len = buttons.length;
for (i = 0; i < len; i++) {

    buttons[i].addEventListener("click", changeAction)
}


