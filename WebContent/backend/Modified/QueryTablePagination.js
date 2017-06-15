/*		=================================
**		==== Simple Table Controller ====
**		=================================
**			With Pure JavaScript .. 
**		No Libraries or Frameworks needed!
**				fb.com/bastony
**	
*/

/*2017 summer, modified by voka1031 */

//用get id 取得table element
var table = document.getElementById("QueryTable"),

//指定每頁顯示的資料筆數
rowPerPage = 10,

//顯示總筆數，如果有設定th的話也要算在內，即 1筆 th + n筆資料
rowCount = table.rows.length,

// 取firstRow tagname, 有th就取得th, 沒有就取得tr
firstRow = table.rows[0].firstElementChild.tagName,

// 布林驗證是否有table head, th , 目前first row 是th, 所以為true
hasHead = (firstRow === "TH"), 

// an array to hold each row
trArr = [],

// loop counters, to start count from rows[1] (2nd row) if the first row has a head tag

i, 

ii, 

boolean_j = (hasHead)?1:0,

// holds the first row if it has a (<TH>) & nothing if (<TD>)
	
//outerHTML: 跑含整個row(0) tag, = <tr class="QueryTable_TR">.....</tr>
th = (hasHead?table.rows[0].outerHTML:"");


// count the number of pages
// cell: 無條件進位
var pageCount = Math.ceil(rowCount / rowPerPage);
// if we had one page only, then we have nothing to do ..

if (pageCount > 1) { //走訪 td所有元素 (去掉th)
	// assign each row outHTML (tag name & innerHTML) to the array ==>生出所有data rwow
	
	for (i = boolean_j,ii = 0; i < rowCount; i++, ii++){
		trArr[ii] = table.rows[i].outerHTML;
	}

	// create a div block to hold the PaginationDiv
	// eq. element.insertAdjacentHTML(position, text);
	//where 'afterend':  After the element itself.
	
	table.insertAdjacentHTML("afterend","<div id='PaginationDiv'></div>");
	
	// the first sort, default page is the first one


	sort(1);
}

// (pageNumber) is the selected page number. it will be generated when a user clicks a button
function sort(pageNumber) {
	
	/* create (rows) a variable to hold the group of rows
	** to be displayed on the selected page,
	** (startPoint) the start point .. the first row in each page, Do The Math
	*/
	
	// remainingTr: 10 rowperpage - (83row -1 th)%10 rowperpage, 
	// remainingTr:還有多少TR欄位需要補齊才不會讓table高度變形。
	
	var remainingTr =  rowPerPage-((rowCount - boolean_j)%rowPerPage);
	
	var rows = th;
	var startPoint = ((rowPerPage * pageNumber)- rowPerPage);
	
	//顯示nth分頁
	for (i = startPoint; i < (startPoint + rowPerPage) && i < trArr.length; i++){
		rows += trArr[i];		
	}
	// remainingTr:空元素補齊，維持table高度。
	if(remainingTr>0&&pageNumber==pageCount){
		
		for (i = 0; i < remainingTr; i++){			
			rows += '<tr class="QueryTable_TR">';
			rows += '<td></td><td></td><td></td><td></td>';
			rows += '<td></td><td></td><td></td><td></td>';
			rows += '<td></td><td></td><td></td>';
/*			有衝到，先寫固定TD
 * //取得th數量後再決定補多少空白td
			for(j = 0; j<$('th').length;j++){			
				rows +='<td></td>';
			}*/			
			rows+='</tr>';
		}			
	}

	// now the table has a processed group of rows ..
	table.innerHTML = rows;
	// create the pagination PaginationDiv
	document.getElementById("PaginationDiv").innerHTML = pagePaginationDiv(pageCount, pageNumber);
	// CSS Stuff
	document.getElementById("id"+pageNumber).setAttribute("class","pgBtn")
	document.getElementById("id"+pageNumber).style.backgroundColor ="#665544";

}

// (pCount) : number of pages,(nthPage) : nthPage, the selected one ..
function pagePaginationDiv(pCount,nthPage) {
	
	/* this variables will disable the "Prev" button on 1st page
	   and "next" button on the last one */
	//disable = 禁用.
	
	var	prevDis = (nthPage == 1)?"disabled":"";	
	if(rowCount%rowPerPage!=boolean_j){
		var nextDis = (nthPage == pCount)?"disabled":"";
	}else{
		var nextDis = (nthPage == pCount-1)?"disabled":"";			
	}
	
		/* this (PaginationDiv) will hold every single button needed
		** it will creates each button and sets the onclick attribute
		** to the "sort" function with a special (pageNumber) number..
		*/
	PaginationDiv = "<input type='button' value='&lt;&lt;'  class='pgBtn2' onclick='sort(1)' "+prevDis+">";		
	
	PaginationDiv += "<input type='button' value='&lt;'  class='pgBtn2' onclick='sort("+(nthPage - 1)+")' "+prevDis+">";
	
	//pCount2: 有新row要在新分頁放置時再建立新分頁
	pCount2=(rowCount%rowPerPage==boolean_j)?(pCount-1):(pCount);
	if(pCount<=9){
			for (i=1; i <=pCount2;i++){
				PaginationDiv += "<input type='button' id='id"+i+"'value='"+i+"' class='pgBtn' onclick='sort("+i+")'>";													
			}
	}else{
		if(nthPage<=5){
			for (i=1; i <=9;i++){
				PaginationDiv += "<input type='button' id='id"+i+"'value='"+i+"' class='pgBtn' onclick='sort("+i+")'>";													
			}
		}else if((pCount - nthPage<5)){
			for (i=(pCount2-8); i <=pCount2;i++){
				PaginationDiv += "<input type='button' id='id"+i+"'value='"+i+"' class='pgBtn' onclick='sort("+i+")'>";															
			}	
		}else{
			for (i=(nthPage-4); i <=(parseInt(nthPage, 10)+4); i++){ //nthPage會變成字串?，所以先強制轉為int
				PaginationDiv += "<input type='button' id='id"+i+"'value='"+i+"' class='pgBtn' onclick='sort("+i+")'>";
			}		
		}
	}
																						//nthPage會變成字串?，所以先強制轉為int
	PaginationDiv += "<input type='button' value='&gt;' class='pgBtn2' onclick='sort("+(parseInt(nthPage, 10) + 1)+")' "+nextDis+">";
	PaginationDiv += "<input type='button' value='&gt;&gt;' class='pgBtn2' onclick='sort("+(pCount2)+")' "+nextDis+">";
	
	//試加select tag:
		PaginationDiv +="<select id='selectBox' onchange='sort(value);'>"		
		for(i=1;i<=pCount2;i++){
			if(i==nthPage){
				PaginationDiv +="<option value="+i+" selected>page"+i+"</option>";
			}else{
				PaginationDiv +="<option value="+i+">page"+i+"</option>";
			}				
		}
		PaginationDiv +="</select>";
	return PaginationDiv;	
}
