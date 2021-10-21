function iconaMenu() {
    var x = document.getElementById("menu");
    if (x.style.right === "0px") {
        x.style.right = "1000px";
        x.style.textAlign = "right";
    } else {
        x.style.right = "0px";
        x.style.textAlign = "center";
    }
    document.getElementById("demo").innerHTML = x.style.right;
}

// Get the modal
var modal = document.getElementById('id01');

// When the user clicks anywhere outside of the modal, close it
window.onclick = function(event) {
    if (event.target === modal) {
    modal.style.display = "none";
    }
}

function changeTextCard(element){
    var x = element.id;

    if(x === "voce1"){
        document.getElementById("testoCard").style.display = "block";
        document.getElementById("testoCard1").style.display = "none";
    }
    else{
        document.getElementById("testoCard").style.display = "none";
        document.getElementById("testoCard1").style.display = "block";
        document.getElementById("testoCard1").style.color = "black";
    }
}