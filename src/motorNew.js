function queryByModtype(str) {
	console.log("str="+str);
	var xhttp;
	if (str == "") {
		document.getElementById("block3").innerHTML = "";
		return;
	}
	xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		console.log("readyState: "+this.readyState);
		if (this.readyState == 4 && this.status == 200) {
			document.getElementById("block3").innerHTML = this.responseText;
		}
	};
	xhttp.open("GET", "/BA101G2/backend/motor.do?action=get_motors_by_modtype&modtype="+str, true);
	xhttp.send(null);
}