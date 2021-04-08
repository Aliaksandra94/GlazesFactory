function myFunction() {
    var x = document.getElementById("typeID").value;
    document.getElementById("price").innerHTML = "You selected: " + x;
}


function getValue() {
    var select = document.getElementById("typeID");
    var value = select.options[select.selectedIndex].value;
    alert(value);
}

