function mostra(id){
	//AZIONE 1: GET PAZIENTI
	if(id.localeCompare("getPazienti_button")==0){
		//2.Nascondi i div relativi a getDocumentiPaziente
		document.getElementById("getDocumentiPaziente").style.display="none";
		document.getElementById("getDocumentiPazienteResult").style.display="none";
		//document.getElementById("getDocumentiPazienteResult").innerHTML="";
		//3.Nascondi i div relativi a addPaziente
		document.getElementById("addPaziente").style.display="none";
		document.getElementById("addPazienteResult").style.display="none";
		//document.getElementById("addPazienteResult").innerHTML="";		
		//4.Nascondi i div relativi a addDocumento
		document.getElementById("addDocumento").style.display="none";
		document.getElementById("addDocumentoResult").style.display="none";
		//document.getElementById("addDocumentoResult").innerHTML="";

		//1.Mostra i div di getPazienti
		document.getElementById("getPazienti").style.display="block";
	}
	
	//AZIONE 2: GET DOCUMENTI
	if(id.localeCompare("getDocumenti_button")==0){
		//1.Nascondi i div relativi a getPazienti
		document.getElementById("getPazienti").style.display="none";
		//3.Nascondi i div relativi a addPaziente
		document.getElementById("addPaziente").style.display="none";
		document.getElementById("addPazienteResult").style.display="none";
		//document.getElementById("addPazienteResult").innerHTML="";		
		//4.Nascondi i div relativi a addDocumento
		document.getElementById("addDocumento").style.display="none";
		document.getElementById("addDocumentoResult").style.display="none";
		//document.getElementById("addDocumentoResult").innerHTML="";
		
		//2.Mostra i div relativi a getDocumentiPaziente
		document.getElementById("getDocumentiPaziente").style.display="block";
		document.getElementById("getDocumentiPazienteResult").style.display="block";
	}
	
	//AZIONE 3: ADD PAZIENTE
	if(id.localeCompare("addPaziente_button")==0){
		//1.Nascondi i div relativi a getPazienti
		document.getElementById("getPazienti").style.display="none";
		//2.Nascondi i div relativi a getDocumentiPaziente
		document.getElementById("getDocumentiPaziente").style.display="none";
		document.getElementById("getDocumentiPazienteResult").style.display="none";
		//document.getElementById("getDocumentiPazienteResult").innerHTML="";
		//4.Nascondi i div relativi a addDocumento
		document.getElementById("addDocumento").style.display="none";
		document.getElementById("addDocumentoResult").style.display="none";
		//document.getElementById("addDocumentoResult").innerHTML="";
		
		//Mostra i div di addPaziente
		document.getElementById("addPaziente").style.display="block";
		document.getElementById("addPazienteResult").innerHTML="";
		document.getElementById("addPazienteResult").style.display="block";
	}
	//AZIONE 4: ADD DOCUMENTO
	if(id.localeCompare("addDocumento_button")==0){
		//1.Nascondi i div relativi a getPazienti
		document.getElementById("getPazienti").style.display="none";
		//2.Nascondi e resetta i div relativi a getDocumentiPaziente
		document.getElementById("getDocumentiPaziente").style.display="none";
		document.getElementById("getDocumentiPazienteResult").style.display="none";
		//document.getElementById("getDocumentiPazienteResult").innerHTML="";
		//3.Nascondi i div relativi a addPaziente
		document.getElementById("addPaziente").style.display="none";
		document.getElementById("addPazienteResult").style.display="none";
		//document.getElementById("addPazienteResult").innerHTML="";		
		
		//Mostra i div di addDocumento
		document.getElementById("addDocumento").style.display="block";
		document.getElementById("addDocumentoResult").innerHTML="";
		document.getElementById("addDocumentoResult").style.display="block";
	}

}