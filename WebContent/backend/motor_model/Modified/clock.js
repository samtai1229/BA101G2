function display_c(){
	var refresh=1000; // Refresh rate in milli seconds
	setTimeout('display_ct()',refresh);
}

function display_ct() {
	var strcount;
	document.getElementById('ct').innerHTML = new Date();
	display_c();
}