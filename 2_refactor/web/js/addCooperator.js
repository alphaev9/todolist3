$().ready(function () {
    $("#addCooperator").click(function (e) {
        e.preventDefault();
        var addButton = $(this).clone(true);
        var index = Number(addButton.attr("index")) + 1;
        addButton.attr("index", index);

        var email = $(this).prev().clone(true);
        email.attr("name", "cooperators[" + index + "].email");
        var name = $(this).prev().prev().clone(true);
        name.attr("name", "cooperators[" + index + "].name");

        $(this).next().after(name, email, addButton, "<br/>");
        $(this).remove();
    });
})