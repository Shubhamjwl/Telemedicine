import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class DocumentService {

	constructor() {

	}

	downloadFileToBrowser(resp, contentType){
	//	'application/pdf'
		if(resp){
			//var mimeType = resp.split(',')[1];
			if(navigator.msSaveBlob){
				navigator.msSaveBlob(this.b64toBlob(resp, contentType), 'image');
			} else {
			const blob = this.b64toBlob(resp, contentType);
			const url = window.URL.createObjectURL(blob);
			const element = document.createElement('a');
			element.setAttribute('href',  url);
			element.setAttribute('download', 'image');
			element.click();
			window.URL.revokeObjectURL(url);
			}
		}
	}

	b64toBlob(b64Data, contentType)  {
		
		const sliceSize = 512;
		const byteCharacters = atob(b64Data);
		const byteArrays = [];
	
		for (let offset = 0; offset < byteCharacters.length; offset += sliceSize) {
		const slice = byteCharacters.slice(offset, offset + sliceSize);
	
		const byteNumbers = new Array(slice.length);
		for (let i = 0; i < slice.length; i++) {
			byteNumbers[i] = slice.charCodeAt(i);
		}
	
		const byteArray = new Uint8Array(byteNumbers);
		byteArrays.push(byteArray);
		}
	
		const blob = new Blob(byteArrays, {type: contentType});
		return blob;
	}
  
	downloadFileAndView(resp, contentType){
		if(resp){
		//var mimeType = resp.split(',')[1];
		if(navigator.msSaveBlob){
			navigator.msSaveBlob(this.b64toBlob(resp, contentType), 'image');
		} else {
			const blob = this.b64toBlob(resp, contentType);
			const url = window.URL.createObjectURL(blob);
			window.open(url);
		}
		}
	}
	convertIntoBase64(file){

		return new Promise((resolve, reject)=> {
			if(file) {
				let reader = new FileReader();
				reader.readAsDataURL(file);
				reader.onload = function () {
					resolve(reader.result);
				};
				reader.onerror = function (error) {
					reject(error);
				};

			}else {
				reject("File not present");
			}
		})
		 
	}
	Validatedocument(file){
		if(file){
			let error = file.size <= 1000000 ? true : false; //1000000
			return error;
		}else{
			return '';
		}
	}
}
