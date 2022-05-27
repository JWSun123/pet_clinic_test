$(document).ready(function(){
    $(".sidebar-dropdown > a").click(function (){
        $(".collapse").toggle();
    });
})

document.getElementById('addNew').onclick = addNew;
function addNew() {
    document.getElementById('test').style.color = "red";
}