window.onload=function () {
    console.log("load");
    var state={};
    var url="/WEB-INF/view/showList.jsp";
    history.pushState(state,null,url);
}