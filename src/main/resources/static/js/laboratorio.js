function abilitaDisabilita(element){
    var x = element.id;

    if(x === "molecolare") {
        if (element.checked==true) {
            document.getElementById("prezzoMolecolare").disabled = false;
        }
        else {
            //document.getElementById("prezzoMolecolare").value = "";
            document.getElementById("prezzoMolecolare").disabled = true;
        }
    }
    if(x === "antigenico") {
        if (element.checked==true) {
            document.getElementById("prezzoAntigenico").disabled = false;
        }
        else {
            //document.getElementById("prezzoAntigenico").value = "";
            document.getElementById("prezzoAntigenico").disabled = true;
        }
    }
    if(x === "sierologico") {
        if (element.checked==true) {
            document.getElementById("prezzoSierologico").disabled = false;
        }
        else {
            //document.getElementById("prezzoSierologico").value = "";
            document.getElementById("prezzoSierologico").disabled = true;
        }
    }

    if(x === "lunedi") {
        if (element.checked==true) {
            document.getElementById("aperturaMattina0").disabled = false;
            document.getElementById("chiusuraMattina0").disabled = false;
            document.getElementById("aperturaPomeriggio0").disabled = false;
            document.getElementById("chiusuraPomeriggio0").disabled = false;
        }
        else {
            document.getElementById("aperturaMattina0").disabled = true;
            document.getElementById("chiusuraMattina0").disabled = true;
            document.getElementById("aperturaPomeriggio0").disabled = true;
            document.getElementById("chiusuraPomeriggio0").disabled = true;
        }
    }
    if(x === "martedi") {
        if (element.checked==true) {
            document.getElementById("aperturaMattina1").disabled = false;
            document.getElementById("chiusuraMattina1").disabled = false;
            document.getElementById("aperturaPomeriggio1").disabled = false;
            document.getElementById("chiusuraPomeriggio1").disabled = false;
        }
        else {
            document.getElementById("aperturaMattina1").disabled = true;
            document.getElementById("chiusuraMattina1").disabled = true;
            document.getElementById("aperturaPomeriggio1").disabled = true;
            document.getElementById("chiusuraPomeriggio1").disabled = true;
        }
    }
    if(x === "mercoledi") {
        if (element.checked==true) {
            document.getElementById("aperturaMattina2").disabled = false;
            document.getElementById("chiusuraMattina2").disabled = false;
            document.getElementById("aperturaPomeriggio2").disabled = false;
            document.getElementById("chiusuraPomeriggio2").disabled = false;
        }
        else {
            document.getElementById("aperturaMattina2").disabled = true;
            document.getElementById("chiusuraMattina2").disabled = true;
            document.getElementById("aperturaPomeriggio2").disabled = true;
            document.getElementById("chiusuraPomeriggio2").disabled = true;
        }
    }
    if(x === "giovedi") {
        if (element.checked==true) {
            document.getElementById("aperturaMattina3").disabled = false;
            document.getElementById("chiusuraMattina3").disabled = false;
            document.getElementById("aperturaPomeriggio3").disabled = false;
            document.getElementById("chiusuraPomeriggio3").disabled = false;
        }
        else {
            document.getElementById("aperturaMattina3").disabled = true;
            document.getElementById("chiusuraMattina3").disabled = true;
            document.getElementById("aperturaPomeriggio3").disabled = true;
            document.getElementById("chiusuraPomeriggio3").disabled = true;
        }
    }
    if(x === "venerdi") {
        if (element.checked==true) {
            document.getElementById("aperturaMattina4").disabled = false;
            document.getElementById("chiusuraMattina4").disabled = false;
            document.getElementById("aperturaPomeriggio4").disabled = false;
            document.getElementById("chiusuraPomeriggio4").disabled = false;
        }
        else {
            document.getElementById("aperturaMattina4").disabled = true;
            document.getElementById("chiusuraMattina4").disabled = true;
            document.getElementById("aperturaPomeriggio4").disabled = true;
            document.getElementById("chiusuraPomeriggio4").disabled = true;
        }
    }
    if(x === "sabato") {
        if (element.checked==true) {
            document.getElementById("aperturaMattina5").disabled = false;
            document.getElementById("chiusuraMattina5").disabled = false;
            document.getElementById("aperturaPomeriggio5").disabled = false;
            document.getElementById("chiusuraPomeriggio5").disabled = false;
        }
        else {
            document.getElementById("aperturaMattina5").disabled = true;
            document.getElementById("chiusuraMattina5").disabled = true;
            document.getElementById("aperturaPomeriggio5").disabled = true;
            document.getElementById("chiusuraPomeriggio5").disabled = true;
        }
    }
    if(x === "domenica") {
        if (element.checked==true) {
            document.getElementById("aperturaMattina6").disabled = false;
            document.getElementById("chiusuraMattina6").disabled = false;
            document.getElementById("aperturaPomeriggio6").disabled = false;
            document.getElementById("chiusuraPomeriggio6").disabled = false;
        }
        else {
            document.getElementById("aperturaMattina6").disabled = true;
            document.getElementById("chiusuraMattina6").disabled = true;
            document.getElementById("aperturaPomeriggio6").disabled = true;
            document.getElementById("chiusuraPomeriggio6").disabled = true;
        }
    }
}