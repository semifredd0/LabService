function abilitaDisabilita(element){
    var x = element.id;

    if(x === "molecolare") {
        if (element.checked==true) {
            document.getElementById("prezzoMolecolare").disabled = false;
        }
        else{
            document.getElementById("prezzoMolecolare").disabled = true;
        }
    }
    if(x === "antigenico"){
        if (element.checked==true) {
            document.getElementById("prezzoAntigenico").disabled = false;
        }
        else{
            document.getElementById("prezzoAntigenico").disabled = true;
        }
    }
    if(x === "sierologico"){
        if (element.checked==true) {
            document.getElementById("prezzoSierologico").disabled = false;
        }
        else{
            document.getElementById("prezzoSierologico").disabled = true;
        }
    }
}