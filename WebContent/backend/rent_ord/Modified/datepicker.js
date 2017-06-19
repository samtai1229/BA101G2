$(function() {
	$(".datepicker").datepicker({
		changeMonth : true,
		changeYear : true,
		minDate: "+0d"
	}); //第一組

	$(".datepicker2").datepicker({
		changeMonth : true,
		changeYear : true
	});	//第二組

	//下面是成對的第三組 from & to
    var dateFormat = "mm/dd/yy",
      from = $( ".from" )
        .datepicker({
          defaultDate: "+1d",
          changeMonth: true,
          numberOfMonths: 1,
          minDate: "+1d",
          maxDate: "+2m"
        })
        .on( "change", function() {
          to.datepicker( "option", "minDate", getDate( this ) );
        }),
      to = $( ".to" ).datepicker({
        defaultDate: "+3d",
        changeMonth: true,
        numberOfMonths: 1,
        minDate: "+1d"
      })
      .on( "change", function() {
        from.datepicker( "option", "maxDate", getDate( this ) );
      });
 
    function getDate( element ) {
      var date;
      try {
        date = $.datepicker.parseDate( dateFormat, element.value );
      } catch( error ) {
        date = null;
      }
 
      return date;
    }
  
});
