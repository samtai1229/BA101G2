/**
 * 
 */
/*jQuery test*/
$(document).ready(function(){	
	$(".rangeBar").change(function(){			
		var R = $("#rangeBarR").val();
		var G = $("#rangeBarG").val();
		var B = $("#rangeBarB").val();
		
		$("legend, .click, .pgBtn, .pgBtn2, #selectBox")
		.css("background-color","RGB("+R+","+G+","+B+")");		
		$("tr, td, th, table,fieldset").css("border-color","RGB("+R+","+G+","+B+")");
		$("#colorID").html("RGB("+R+","+G+","+B+")");
	});	
	

	$("#colorBtn").change(function(){	
		var favcolor = $("#colorBtn").val();
		
		$("legend, .click, .pgBtn, .pgBtn2, #selectBox")
		.css("background-color",favcolor );		
		$("tr, td, th, table,fieldset").css("border-color",favcolor);
		$("#colorID").html("RGB:"+favcolor);			
	});
	
	/*
	$("#testButton").click(function(){			
		setInterval(function(){//interval
			
			var R = Math.floor((Math.random() * 255)).toString();
			var G = Math.floor((Math.random() * 255)).toString(); 
			var B = Math.floor((Math.random() * 255)).toString(); 
			$("legend, .click, .pgBtn, .pgBtn2, #selectBox")
			.css("background-color","RGB("+R+","+G+","+B+")");
			
			$("tr, td, th, table,fieldset").css("border-color","RGB("+R+","+G+","+B+")");
			
			$("#colorID").html("RGB("+R+","+G+","+B+")");		
		
		}, 3000);//interval end
	});
	 */	
		
});