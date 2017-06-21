
function loadMotor() {
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			document.getElementById("demo").innerHTML = this.responseText;
		}
	};
	xhttp.open("GET", "motor/backendMotor.jsp", true);
	xhttp.send();
}


function loadMotorModel() {
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			document.getElementById("demo").innerHTML = this.responseText;
		}
	};
	xhttp.open("GET", "motor_model/backendMotorModel.jsp", true);
	xhttp.send();
}



function queryMotorByModtype(str) {
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
	xhttp.open("GET", "/BA101G2/backend/motor/motor.do?action=get_motors_by_modtype&modtype="+str, true);
	xhttp.send(null);
}




function queryMotorByMotorPK(str) {
	console.log("str="+str);
	var xhttp;
	if (str == "") {
		document.getElementById("showSingleQueryResult").innerHTML = "";
		return;
	}
	xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		console.log("readyState: "+this.readyState);
		if (this.readyState == 4 && this.status == 200) {
			document.getElementById("showSingleQueryResult").innerHTML = this.responseText;
		}
	};
	xhttp.open("GET", "/BA101G2/backend/motor/motor.do?action=query&motno="+str, true);
	xhttp.send(null);
}



function queryMotorTypeByModtype(str) {
	console.log("str="+str);
	var xhttp;
	if (str == "") {
		document.getElementById("showSingleQueryResult").innerHTML = "";
		return;
	}
	xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		console.log("readyState: "+this.readyState);
		if (this.readyState == 4 && this.status == 200) {
			document.getElementById("showSingleQueryResult").innerHTML = this.responseText;
		}
	};
	xhttp.open("GET", "/BA101G2/backend/motor_model/motorModel.do?action=query&modtype="+str, true);
	xhttp.send(null);
}

