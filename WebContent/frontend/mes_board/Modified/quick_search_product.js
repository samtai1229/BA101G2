

/* page 2 defender for slocno and rlocno*/
function $id(id) {
	return document.getElementById(id);
}

function $name(name) {
	return document.getElementsByName(name);
}

function checkForm(e) {

	var count = 0;

	// 檢查slocno一定要選
	var slocno = $id("slocno");
	if (slocno.selectedIndex == 0) {
		$id("slocnoAnchor").innerHTML = " (取車地點未選)";
		count++;
	} else {
		$id("slocnoAnchor").innerHTML = "";
	}

	// 檢查rlocno一定要選
	var rlocno = $id("rlocno");
	if (rlocno.selectedIndex == 0) {
		$id("rlocnoAnchor").innerHTML = " (還車地點未選)";
		count++;
	} else {
		$id("rlocnoAnchor").innerHTML = "";
	}

	if (count > 0) {
		e ? e.preventDefault() : event.returnValue = false;
		return;

	}
}

window.onload = function() {
	document.getElementById("myForm").onsubmit = checkForm;
};

/* page 2 defender for slocno and rlocno*/