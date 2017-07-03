

function queryRentOrdBySlocno(str) {
	console.log("str="+str);
	console.log("rentOrdNew.js on");
	var xhttp;
	if (str == "logo") {
		document.getElementById("block3").innerHTML = "";
		return;
	}
	xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			document.getElementById("block3").innerHTML = this.responseText;
			startPageRow();
		}
	};
	xhttp.open("GET", "/BA101G2/backend/rent_ord/rentOrd.do?action=get_for_lease_view&slocno="+str, true);
	xhttp.send(null);
}


function queryRentOrdByRlocno(str) {
	console.log("str="+str);
	var xhttp;
	if (str == "") {
		document.getElementById("block3").innerHTML = "";
		return;
	}
	xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			document.getElementById("block3").innerHTML = this.responseText;
			startPageRow();
		}
	};
	xhttp.open("GET", "/BA101G2/backend/rent_ord/rentOrd.do?action=get_for_return_view&rlocno="+str, true);
	xhttp.send(null);
}