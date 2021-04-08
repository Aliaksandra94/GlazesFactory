<html>
<head><title>Hi</title><%@include file="/WEB-INF/views/header/header.jsp"%></head>
<body>

<script>
    function msg() {
        alert("List of Users");
        location.href = "/users";

    }
    function composition() {

        var stateVar = $('#state option, #state optgroup');
        stateVar.hide();
        $('#country').change(function () {
            stateVar.hide();
            $("#state optgroup[label='" + $(this).find(':selected').html() + "']")
                .children()
                .andSelf()
                .show();
        })
    };
</script>
<button onclick="msg()">Click Me!</button>
<br>
<select id="country" name="country" onchange="composition()">
    <option value="">Select Your Country</option>
    <option value="USA">India</option>
    <option value="CA">Pakisthan</option>
</select>
<p><label for="state">Select Your State<span title="required">*</span>
</label></p>
<select id="state" name="state" aria-required="true" class="required">
    <option value="" selected="">Select Your State</option>
    <optgroup label="India">
        <option value="WB">West Bengal</option>
        <option value="BH">Bihar</option>
    </optgroup>
    <optgroup label="Pakisthan">
        <option value="LH">Lahore</option>
        <option value="IB">Islamabad</option>
    </optgroup>
</select>

<br>
<html>
<body>

<p>Select a new car from the list.</p>

<select id="mySelect" onchange="myFunction()">
    <option value="Audi">Audi
    <option value="BMW">BMW
    <option value="Mercedes">Mercedes
    <option value="Volvo">Volvo
</select>

<p>When you select a new car, a function is triggered which outputs the value of the selected car.</p>

<p id="demo"></p>

<script>
    function myFunction() {
        var x = document.getElementById("mySelect").value;
        document.getElementById("demo").innerHTML = "You selected: " + x;
    }
</script>

</body>
</html>