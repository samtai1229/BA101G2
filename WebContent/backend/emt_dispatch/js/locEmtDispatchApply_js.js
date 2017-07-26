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
        var ecList = $(this).parent().parent().parent();
        var amount = ecList.find(".count").val();
        var ecno = ecList.find(".ecno").text().trim();
        var emtno = ecList.find(".emtno");
        addEmt(amount, ecno, emtno);
    });
    
    (function(){
    	$('.checkout').on('click', function(){
    		$.colorbox({
    			html : "<h3 style='margin-left:60px;'>裝備調度申請最後確認</h3>"+ $("#dispatchListContent").html(),	//在燈箱中要顯示的html字段
    			width : 400,	//燈箱中間區塊的寬度
    			height : 600,	//燈箱中間區塊的高度
    		});
    	});
    })();
}

function addEmt(amount, ecno, emtno) {
    if (amount == 0 || amount == null || amount == "") {
        $("#" + ecno).remove();
        for(var i = 0; i < emtno.length; i++)
        	$("#" + emtno.eq(i).val()).remove();
    } else {
        $("#cartImage").attr("src", "/BA101G2/backend/emt_cate/ecReader.do?ecno=" + ecno);
        $("#spanName").text(ecno);
        $("#emtAmt").text(amount + " 件");
        
        var content_len = $(".contentTr").length;
        //先建立一個boolean變數來做以下判斷
        var boolean = true;

        for (var i = 0; i < content_len; i++) {
            var trId = $(".contentTr").eq(i).attr("id");
            if (trId == ecno) {
                //將boolean轉為false，就不會跑下方的if(boolean==true)
                
                boolean = false;
                $("#" + ecno).remove();
                $("#dispatchListContent table").prepend("<tr class='contentTr' id = '" + ecno + "'><td class = 'contentTd'><div class='form-group'><label class='contentName control-label col-sm-3' for='ecno'>" + ecno + "</label><input type='hidden' name='ecno' value='" + ecno + "'><div class='col-sm-9'><input readonly type='text' class='contentCount form-control' name='amount' value='" + amount + "' /></div></div></td></tr>");
                
                for(var i = 0; i < amount; i++){
                    $("#dispatchListContent table").prepend("<input type='hidden' name='emtno' id = '" + emtno.eq(i).val() + "' value='" + emtno.eq(i).val() + "'")
                }
            }
        }
        if (boolean == true) { //boolean==true表示trId!=ecno
            $("#dispatchListContent table").prepend("<tr class='contentTr' id = '" + ecno + "'><td class = 'contentTd'><div class='form-group'><label class='contentName control-label col-sm-3' for='ecno'>" + ecno + "</label><input type='hidden' name='ecno' value='" + ecno + "'><div class='col-sm-9'><input readonly type='text' class='contentCount form-control' name='ecno' value='" + amount + "' /></div></div></td></tr>");
            for(var i = 0; i < amount; i++){
                $("#dispatchListContent table").prepend("<input type='hidden' name='emtno' id = '" + emtno.eq(i).val() + "' value='" + emtno.eq(i).val() + "'>")
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

    $(".contentTotal").text("總計: " + total + " 件 ");
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
