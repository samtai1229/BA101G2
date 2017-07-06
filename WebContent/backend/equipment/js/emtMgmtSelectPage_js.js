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
}
window.addEventListener('load', doFirst, false);
