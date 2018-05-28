(function () {
        var ws = new WebSocket("ws://localhost:8080/wsChannel");
        window.wschannel=ws;
        ws.onmessage = function (ev) {
            var message = JSON.parse(ev.data);
            console.log(message.messageType);
            var notificationText;
            switch (message.messageType) {
                case "kickOff":
                    notificationText = message.messageBody;
                    notification(notificationText);
                    ws.close();
                    window.location.reload();
                    break;
                case "advent":
                    notificationText = "you have " + message.messageBody.length + " backlog";
                    notification(notificationText);
                    warn(message.messageBody);
                    break;
            }


        }
        ws.onopen = function () {
            console.log("websocket is opened")
        }
    }
)();

function warn(backlogs) {
    var form = document.querySelector("#pending");
    var checkboxes = form.querySelectorAll("input[type=checkbox]");
    for (var i = 0; i < checkboxes.length; i++) {
        if (isBacklog(checkboxes[i].value, backlogs)) {
            var span = checkboxes[i].nextElementSibling;
            span.className="warn";
            console.log(checkboxes[i].nextElementSibling.className);
        }
    }

}

function isBacklog(event, backlogs) {
    for (var i = 0; i < backlogs.length; i++) {
        if (event == backlogs[i].event) {
            return true;
        }
    }
    return false;
}
function notification(notificationText) {
    if (Notification.permission = "granted") {
        var notification = new Notification(notificationText);
    } else {
        if (Notification.permission !== 'denied') {
            Notification.requestPermission(
                function (permission) {
                    // 如果用户同意，就可以向他们发送通知
                    if (permission === "granted") {
                        var notification = new Notification(notificationText);
                    }
                })
        }
    }
}

window.onunload=function () {
    console.log("unload showlist");
    window.wschannel.close();
}