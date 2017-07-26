function doFirst(){
	document.getElementById('myFileInsert').onchange = fileChangeForInsert;
	document.getElementById('myFileUpdate').onchange = fileChangeForUpdate;
}

function fileChangeForInsert() {
	var file = document.getElementById('myFileInsert').files[0];
	var message = '';

	message += 'File Name: '+file.name+'\n';
	message += 'File Type: '+file.type+'\n';
	message += 'File Size: '+file.size+' byte(s)\n';
	message += 'Last modified: '+file.lastModifiedDate+'\n';
	document.getElementById('fileInfoInsert').innerHTML = message;

	var readFile = new FileReader();
	readFile.readAsDataURL(file);
	readFile.addEventListener('load',function(){
		var image = document.getElementById('imageInsert');
		image.src = this.result;
		image.style.maxWidth = '300px';
		image.style.maxHeight = '300px';
	},false);
}

function fileChangeForUpdate() {
	var file = document.getElementById('myFileUpdate').files[0];
	var message = '';

	message += 'File Name: '+file.name+'\n';
	message += 'File Type: '+file.type+'\n';
	message += 'File Size: '+file.size+' byte(s)\n';
	message += 'Last modified: '+file.lastModifiedDate+'\n';
	document.getElementById('fileInfoUpdate').innerHTML = message;

	var readFile = new FileReader();
	readFile.readAsDataURL(file);
	readFile.addEventListener('load',function(){
		var image = document.getElementById('imageUpdate');
		image.src = this.result;
		image.style.maxWidth = '300px';
		image.style.maxHeight = '300px';
	},false);
}
window.addEventListener('load',doFirst,false);