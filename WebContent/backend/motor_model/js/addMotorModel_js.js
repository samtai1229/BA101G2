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

    var adt = document.getElementsByClassName("accordionDispTable");
    var j;

    for (j = 0; j < adt.length; j++) {
        adt[j].onclick = function() {
            this.classList.toggle("active");
            var panel = this.nextElementSibling;
            if (panel.style.display === "block") {
                panel.style.display = "none";
            } else {
                panel.style.display = "block";
            }
        }
    }

    //上傳圖片
    document.getElementById('filePic').onchange = fileChange;
}
window.addEventListener('load', doFirst, false);

function fileChange() {
    var file = document.getElementById('filePic').files[0];
    var readFile = new FileReader();
    readFile.readAsDataURL(file);
    readFile.addEventListener('load', function() { //function(event)
        // alert(event.target+' : ' + event.type);
        var image = document.getElementById('imgPic');
        
        image.removeAttribute("src");
        image.src = this.result;
    }, false);
}