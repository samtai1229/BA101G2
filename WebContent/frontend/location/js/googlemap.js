var Lon,Lat;
var myMap;
var map;
var myPosition =[];
var i=0,j=0;
 	function doFirst(){
         myMap = document.getElementById('myMap');
         Lon = 24.01;
         Lat = 121.1;
         myPosition = new google.maps.LatLng(Lon,Lat);
         var a=2;
         var b=3;
         var c=6;
         var lon = new Array(1);
         var lat = new Array(1);
         map = new google.maps.Map(myMap,{
	    	 zoom: 8,
	    	 center:  myPosition,
	    	 mapTypeId: google.maps.MapTypeId.ROADMAP
	     });
         //建立地圖 marker 的集合
	     var marker_config = new Array();
	     var markers = [];
         while(($("tr td:eq("+(a+i*c)+")").text()) != ""){
console.log( "lons["+i+"]= "+$("tr td:eq("+(a+i*c)+")").text()  );
console.log( "lats["+j+"]= "+$("tr td:eq("+(b+j*c)+")").text()  );
		     lon[i] = $("tr td:eq("+(a+i*c)+")").text();
		     lat[j] = $("tr td:eq("+(b+j*c)+")").text();
		     myPosition=new google.maps.LatLng(lon[i],lat[j]);
		     console.log("myPosition="+myPosition);
		     //建立地圖 marker 的集合
		     marker_config[i] = {
		       position: myPosition,
		       map: map
		     };
	         i++;
	         j++;
         }
         //標出 marker
	     marker_config.forEach(function(e,k){
	       markers[k] = new google.maps.Marker(e);
	       markers[k].setMap(map);
	     });  

         /*var maker = []; 
         marker = new google.maps.Marker({
        	 position: myPosition[i]  ,
        	 map: map[i]},{
        	 position: myPosition[++i],
        	 map: map[i]},{
        	 position: myPosition[++i],
        	 map: map[i]},{
        	 position: myPosition[++i],
        	 map: map[i]},{
        	 position: myPosition[++i],
        	 map: map[i]},{
        	 position: myPosition[++i],
        	 map: map[i]},{ 
        	 position: myPosition[++i],
        	 map: map[i]
        	 });*/
        /* document.getElementById('address').addEventListener('click', function() {
            geocodeAddress(geocoder, map);
          }); */
	 
 	}
 	
// 	function changeLatLng(lon,lat){
// 		var count =1;
// 		 Lon = lon;
// 		 Lat = lat;
//console.log("lon= "+Lon); 			
//console.log("lat= "+Lat); 	
// 			myPosition[count] = new google.maps.LatLng(Lon,Lat);
//console.log("myPosition["+count+"]= "+myPosition[count]);
// 			 map = new google.maps.Map(myMap,{
// 	            zoom: 10,
// 	            center: myPosition[count],
// 	            mapTypeId: google.maps.MapTypeId.ROADMAP
// 	    });
// 			 
// 		var markers = 'marker'+count;	 
// 		 markers = new google.maps.Marker({
// 				position: myPosition[count],
// 				map: map,
// 				animation: google.maps.Animation.DROP
// 			});
// 			
// 		 count++;
// 		}

// 		
// 	function addr(){
//var url="https://maps.googleapis.com/maps/api/geocode/json?address=1600+Amphitheatre+Parkway,+Mountain+View,+CA&key=YOUR_API_KEY";
// 	}
// 	
// 	
// 	function startXMLRequest(){
// 	   var xmlHttp = createXMLHttpRequest();
// 	   var url = "testFile.xml";
// 	   xmlHttp.open("GET",url,true);
// 	   xmlHttp.onreadystatechange = function (){
// 	      if(xmlHttp.readyState == 4 && xmlHttp.status == 200){
// 		     drawTable(xmlHttp.responseXML);
// 	      }
//        };
//        xmlHttp.send(null);
//    }
 	
 	
 	
 	window.addEventListener('load',doFirst,false);
 	window.addEventListener('ready',doFirst,false);
