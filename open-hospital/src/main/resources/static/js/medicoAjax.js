/*Lorenzo Piazza 0000766393*/


/*AJAX with JQuery*/

/*GET PAZIENTI*/

function getPazienti() {  
    //$(document).ready(function(){
    	$.ajax({  
    		async: true,  // Async by default is set to “true” load the script asynchronously
    		url: '/medici/findpazienti?medico_id='+getCookie("id"),
    		method: 'GET',  //Specifies the operation to fetch the list item
    		crossDomain: true,
    		accepts: 'application/x-www-form-urlencoded, json',
    		headers: {  
    			'accept': 'application/json',   //It defines the Data format
    			'content-type': 'application/json'   //It defines the content type as JSON
    		},  
    		success: function(data, textStatus, request) {  
    			if(data.length==0){
    				$('#getPazienti').html("<p>Non hai ancora nessun paziente, clicca su aggiungi Paziente per aggiungerne uno!</p>");
    			}else{
    				$('#tabellaPazienti').html("<tr> <th>Id</th> <th>Cognome</th> <th>Nome</th> <th>Data di Nascita</th> <th>Luogo di Nascita</th> <th>Residenza</th> <th>Telefono</th> <th>Codice Fiscale</th> </tr>");
    				//Iterate the data
    				$.each(data, function(index, value) {  
    					var html = "<tr><td>" + value.id + "</td><td>" + value.cognome + "</td><td>" + value.nome + "</td><td>" + value.dataNascita + "</td>" +
    					"<td>" + value.luogoNascita + "</td><td>" + value.residenza + "</td>" +
    					"<td>" + value.telefono + "</td><td>" + value.codiceFiscale + "</td>" +
    					"</tr> </table>";
    					$('#tabellaPazienti').append(html);  //Append the HTML
    				});
    				$('#tabellaPazienti').append('<p> Contenuti aggiornati al: '+request.getResponseHeader('Date')+'</p> <br> <input class="addButton" type="button" value="Aggiorna i contenuti" onclick="refreshPazienti()">');
    			}

        },  
        error: function(error) {  
            console.log((error));  
  
        }  
    })  
  
  
   // });
}

function refreshPazienti() {  
    //$(document).ready(function(){
    	$.ajax({  
    		async: true,  // Async by default is set to “true” load the script asynchronously
    		url: '/medici/findpazienti?medico_id='+getCookie("id"),
    		method: 'GET',  //Specifies the operation to fetch the list item
    		crossDomain: true,
    		accepts: 'application/x-www-form-urlencoded, json',
    		headers: {  
    			'accept': 'application/json',   //It defines the Data format
    			'content-type': 'application/json',   //It defines the content type as JSON
    			'myRequestType' : 'forceRefresh'	
    		},  
    		success: function(data, textStatus, request) {  
    			if(data.length==0){
    				$('#getPazienti').html("<p>Non hai ancora nessun paziente, clicca su aggiungi Paziente per aggiungerne uno!</p>");
    			}else{
    				$('#tabellaPazienti').html("<tr> <th>Id</th> <th>Cognome</th> <th>Nome</th> <th>Data di Nascita</th> <th>Luogo di Nascita</th> <th>Residenza</th> <th>Telefono</th> <th>Codice Fiscale</th> </tr>");
    				//Iterate the data
    				$.each(data, function(index, value) {  
    					var html = "<tr><td>" + value.id + "</td><td>" + value.cognome + "</td><td>" + value.nome + "</td><td>" + value.dataNascita + "</td>" +
    					"<td>" + value.luogoNascita + "</td><td>" + value.residenza + "</td>" +
    					"<td>" + value.telefono + "</td><td>" + value.codiceFiscale + "</td>" +
    					"</tr> </table>";  
    					$('#tabellaPazienti').append(html);  //Append the HTML
    				});
    				$('#tabellaPazienti').append('<p> Contenuti aggiornati al: '+request.getResponseHeader('Date')+'</p> <br> <input class="addButton" type="button" value="Aggiorna i contenuti" onclick="refreshPazienti()">');
    			}

        },  
        error: function(error) {  
            console.log((error));  
  
        }  
    })  
  
  
   // });
}

function getDocumentiPaziente(){	
	$(document).ready(function(){
		$.ajax({  
			async: true,  // Async by default is set to “true” load the script asynchronously
			url: '/pazienti/mieidocumenti?paziente_id='+$('input:text' && "[name='id_paziente_doc']").val(),
			method: 'GET',  //Specifies the operation to fetch the list item
			crossDomain: true,
			accepts: 'application/x-www-form-urlencoded, json',
			headers: {  
				'accept': 'application/json',   //It defines the Data format
				'content-type': 'application/json'   //It defines the content type as JSON
			},  
			success: function(data) {  
				if(data.length==0){
					$('#getDocumentiPazienteResult').html("<p>Il paziente richiesto non ha ancora documenti oppure potrebbe non esistere un paziente con l'id specificato.<br>Clicca su aggiungi Documento per aggiungerne uno!</p>");
				}else{
					$('#tabellaDocumenti').html("Ecco i documenti del paziente con ID="+$('input:text' && "[name='id_paziente_doc']").val()+": <br><tr> <th>Id Documento</th> <th>Descrizione</th> <th>Tipologia</th> <th>Data di Emissione</th> <th>Id Medico emittente</th> </tr>");
					//Iterate the data
					$.each(data, function(index, value) {  
						var html = "<tr><td>" + value.id + "</td><td>" + value.descrizione + "</td><td>" + value.tipologia + "</td><td>" + value.data + "</td>" + "<td>" + value.medico.id + "</td>" + "</tr> </table>";  
						$('#tabellaDocumenti').append(html);  //Append the HTML
					});  
				}

			},  
			error: function(error) {  
				console.log((error));  

			}  
		})  


	});
}
    
function addPaziente(){
	$(document).ready(function(){
		$.ajax({  
			async: true,  // Async by default is set to “true” load the script asynchronously
			url: '/pazienti/create',
			method: 'POST',  //Specifies the operation to fetch the list item
			accepts: 'application/x-www-form-urlencoded, json',
			headers: {  
				'accept': 'application/json',   //It defines the Data format
				'content-type': 'application/json'   //It defines the content type as JSON
			}, 
			data: JSON.stringify({
				'nome': $('input:text' && "[name='nome']").val(),
				'cognome': $('input:text' && "[name='cognome']").val(),
				'luogoNascita': $('input:text' && "[name='luogo_nascita']").val(),
				'dataNascita': $('input:date' && "[name='data_nascita']").val(),
				'residenza': $('input:text' && "[name='residenza']").val(),
				'codiceFiscale': $('input:text' && "[name='codiceFiscale']").val(),
				'email': $('input:email' && "[name='email']").val(),
				'telefono': $('input:text' && "[name='telefono']").val(),
				'medici': [{
					"id": getCookie("id")
				}]
			}),
			success: function(data) { 
				$('#addPaziente').hide();
    			if(data.length==0){
    				$('#addPazienteResult').html("<p>Errore nell'inserimento del paziente. Può darsi che esista già un Codice Fiscale analogo nel db o che l'id del medico non sia stato trovato nel database!</p>");
    			}else{
        			$('#addPazienteResult').html("<p>Il Paziente è stato aggiunto correttamente!</p>");
        			console.log("Paziente aggiunto correttamente");
    			}
    		},  
				
			error: function(error) {  
				console.log((error));  

			}  
		}); 
	}); 
}

function addDocumento(){
	 $(document).ready(function(){
	    	$.ajax({  
	    		async: true,  // Async by default is set to “true” load the script asynchronously
	    		url: '/documenti/create',
	    		method: 'POST',  //Specifies the operation to fetch the list item
	    		crossDomain: true,
	    		accepts: 'application/x-www-form-urlencoded, json',
	    		headers: {  
	    			'accept': 'application/json',   //It defines the Data format
	    			'content-type': 'application/json'  //It defines the content type as JSON	
	    		}, 
	    		data:  JSON.stringify(
	    	    {
	    	        "data": $('input:date' && "[name='data_emissione']").val(),
	    	        "descrizione": $('textarea' && "[name='descrizione']").val(),
	    	        "tipologia": $('input:text' && "[name='tipologia']").val(),
	    	        "paziente": {
	    	        	"id": $('input:text' && "[name='idPaziente']").val()
	    	        },
	    	    	"medico":{
	    	    		"id": getCookie("id")
	    	    		
	    	    	}
	    	    }),
	    		success: function(data) {  
	    			$('#addDocumento').hide();
	    			if(data.length==0){
	    				$('#addDocumentoResult').html("<p>Errore nell'inserimento del documento, id del paziente o id del medico non trovati nel database!</p>");
	    			}else{
	        			$('#addDocumentoResult').html("<p>Il Documento è stato aggiunto correttamente!</p>");
	        			console.log("Documento aggiunto correttamente");
	    			}
	    		},  
	            error: function(error) {  
	                console.log((error));  
	      
	            }  
	        })  
	      });
}


/*TTL
'use strict';
var config={version:'versione1::',blacklistCacheItems:['/service-worker-dynamic-cache.js'],ttl_html:7200,ttl_static:7200,debug:!0};
var cacheName=config.version;
function cacheName(key,opts){return `${opts.version}${key}`}

function fetchTTL(event,resourceType){
	var url=new URL(event.request.url);
	return caches.open(cacheName).then(function(cache){return cache.match(url).then(function(response){
		if(config.debug)
			console.log(url+" in cache");
		if(response.headers.has("sw-cache-timestamp")){
			var millis=Date.now()-response.headers.get("sw-cache-timestamp");
			var cache_expired=!1;
			var current_ttl=Math.floor(millis/1000);
			if(resourceType=="content"&&current_ttl>config.ttl_html)
				cache_expired=!0;
			else if(current_ttl>config.ttl_static)
				cache_expired=!0;
			if(config.debug)
				console.log(url+" TTL = "+current_ttl);
			if(cache_expired){
				if(config.debug)
					console.log(url+" TTL superato ");
				return cache.delete(url).then(function(){
					if(config.debug)
						console.log(url+" eliminato dalla cache ");
					return fetch(event.request).then(function(response){
						if(response.headers.has("sw-cache-timestamp")){
							if(config.debug)
								console.log(url+" inserito in cache con timestamp "+response.headers.get("sw-cache-timestamp"));
							cache.put(url,new_response.clone());
							return response
						}
						else{
							var new_response=new Response(response.body,{headers:response.headers});
							if(!new_response.headers.has("sw-cache-timestamp")){
								new_response.headers.append("sw-cache-timestamp",Date.now())}
							if(config.debug)
								console.log(url+" inserito in cache con timestamp "+new_response.headers.get("sw-cache-timestamp"));cache.put(url,new_response.clone());
								return response}})})}
			else{
				return response
			}
		}
		else{
			if(response){
				return response
			}
			throw Error('The cached response that was expected is missing.')}})}).catch(function(e){return fetch(event.request).then(function(response){if(config.debug)
				console.log(url+" NON in cache");
			if(response.headers.has("sw-cache-timestamp")){
				if(config.debug)
					console.log(url+" inserito in cache con timestamp "+response.headers.get("sw-cache-timestamp"));
				return caches.open(cacheName).then(function(cache){cache.put(url,response.clone());
				return response
				})
			}
			else{
				var new_response=new Response(response.body,{headers:response.headers});
				if(!new_response.headers.has("sw-cache-timestamp")){
					new_response.headers.append("sw-cache-timestamp",Date.now())
				}
				if(config.debug)
					console.log(url+" inserito in cache con timestamp "+new_response.headers.get("sw-cache-timestamp"));
				return caches.open(cacheName).then(function(cache){cache.put(url,new_response.clone());
				return new_response
				})
			}
			})
			})
}

self.addEventListener('install',function(event){
	self.skipWaiting()});
self.addEventListener('activate',function(event){
	event.waitUntil(caches.keys().then(function(cacheKeys){
		var oldCacheKeys=cacheKeys.filter(key=>key.indexOf(config.version)!==0);
		var deletePromises=oldCacheKeys.map(oldKey=>caches.delete(oldKey));
		return Promise.all(deletePromises)}).then(()=>self.clients.claim()))});
self.addEventListener('fetch',function(event){
	var request=event.request;
	var acceptHeader=request.headers.get('Accept');
	var url=new URL(request.url);
	var resourceType='static';
	if(request.method!=='GET'){return}
	if(url.origin!==self.location.origin){return}
	if(config.blacklistCacheItems.length>0&&config.blacklistCacheItems.indexOf(url.pathname)>=0){return}
	if(acceptHeader.indexOf('text/html')!==-1){resourceType='content'}
	event.respondWith(fetchTTL(event,resourceType))})*/
