window.addEventListener('load', doFirst, false);

function doFirst() {
    var acc = document.getElementsByClassName("accordionMenu");
    var i;
    for (i = 0; i < acc.length; i++) {
        acc[i].onclick = function() {
            this.classList.toggle("active");
            var panel = this.nextElementSibling;
            $(".accordionMenu").siblings().css('background-color', '#eee');

            if (panel.style.display === "block") {
                panel.style.display = "none";
            } else {
                $(".btn-group-vertical").css('display', 'none');
                panel.style.display = "block";
                $(this).css('background-color', '#ddd');
            }
        }
    }

        //購物車
    $(".addButton").on('click', function() {
        var mmList = $(this).parent().parent().parent();
        var amount = mmList.find(".count").val();
        var modtype = mmList.find(".modtype").text().trim();
        var motno = mmList.find(".motno");
        addMotor(amount, modtype, motno);
    });
    
    (function(){
    	$('.checkout').on('click', function(){
    		$.colorbox({
    			html : "<h3 style='margin-left:60px;'>車輛調度申請最後確認</h3>"+ $("#dispatchListContent").html(),	//在燈箱中要顯示的html字段
    			width : 400,	//燈箱中間區塊的寬度
    			height : 600,	//燈箱中間區塊的高度
    		});
    	});
    })();

}

function addMotor(amount, modtype, motno) {
    if (amount == 0 || amount == null || amount == "") {
        $("#" + modtype).remove();
        for(var i = 0; i < motno.length; i++)
        	$("#" + motno.eq(i).val()).remove();
    } else {
        $("#cartImage").attr("src", "/BA101G2/backend/motor_model/mmReader.do?modtype=" + modtype);
        $("#spanName").text(modtype);
        $("#motorAmt").text(amount + " 輛");
        
        var content_len = $(".contentTr").length;
        //先建立一個boolean變數來做以下判斷
        var boolean = true;

        for (var i = 0; i < content_len; i++) {
            var trId = $(".contentTr").eq(i).attr("id");
            if (trId == modtype) {
                //將boolean轉為false，就不會跑下方的if(boolean==true)
                
                boolean = false;
                $("#" + modtype).remove();
                $("#dispatchListContent table").prepend("<tr class='contentTr' id = '" + modtype + "'><td class = 'contentTd'><div class='form-group'><label class='contentName control-label col-sm-3' for='modtype'>" + modtype + "</label><input type='hidden' name='modtype' value='" + modtype + "'><div class='col-sm-9'><input readonly type='text' class='contentCount form-control' name='amount' value='" + amount + "' /></div></div></td></tr>");
                
                for(var i = 0; i < amount; i++){
                    $("#dispatchListContent table").prepend("<input type='hidden' name='motno' id = '" + motno.eq(i).val() + "' value='" + motno.eq(i).val() + "'")
                }
            }
        }
        if (boolean == true) { //boolean==true表示trId!=modtype
            $("#dispatchListContent table").prepend("<tr class='contentTr' id = '" + modtype + "'><td class = 'contentTd'><div class='form-group'><label class='contentName control-label col-sm-3' for='modtype'>" + modtype + "</label><input type='hidden' name='modtype' value='" + modtype + "'><div class='col-sm-9'><input readonly type='text' class='contentCount form-control' name='modtype' value='" + amount + "' /></div></div></td></tr>");
            for(var i = 0; i < amount; i++){
                $("#dispatchListContent table").prepend("<input type='hidden' name='motno' id = '" + motno.eq(i).val() + "' value='" + motno.eq(i).val() + "'>")
            }
        }
    }
    var total = 0;
    for (var i = 0; i < $(".contentCount").length; i++) {
        var numCount = parseInt($(".contentCount").eq(i).val());

        if (isNaN(numCount)) {
            numCount = 0;
        }
        total = total + numCount;

    }

    $(".contentTotal").text("總計: " + total + " 輛 ");
}


$(function() {
    // 預設顯示第一個 Tab
    var _showTab = 0;
    $('ul.switch-button li').eq(_showTab).addClass('turnon');
    $('ul.switch-button li').click(function() {
        var $color = $(this)
        $color.addClass('turnon').siblings('.turnon').removeClass('turnon');
    });
    $('#sw_01').click(function() {

        $('.content-wrapper dl').addClass('vw_01').removeClass('vw_02 vw_03');
    });
    $('#sw_02').click(function() {

        $('.content-wrapper dl').addClass('vw_02').removeClass('vw_01 vw_03');
    });
    $('#sw_03').click(function() {

        $('.content-wrapper dl').addClass('vw_03').removeClass('vw_01 vw_02');
    });

});

