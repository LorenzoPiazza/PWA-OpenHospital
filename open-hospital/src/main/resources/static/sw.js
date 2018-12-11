/*
 * E' bene usare un nome di cache contentenente un numero di versione, e poi cambiarlo quando viene cambiato il service worker.
 * In questo modo l'handler della funzione activate provvederà ad eliminare la vecchia cache per evitare che intererisca.
 */
var cacheName='OH_cacheMemory_v1';


/* 
 * MEMORIZZAZIONE DELLE RISORSE IN CACHE: pattern "on installation":
 * Al momento dell' installazione del sw gli facciamo salvare in cache alcune risorse effettuando un pre-caching.  
 */

self.addEventListener ('install', function(event){
	console.log("SW Installed!");
	event.waitUntil(
			//Apro la cache. Se non esiste ancora la crea e la apre.
			caches.open(cacheName)
			.then(function(cache){
				cache.addAll([
					'.', 'index.html',	'/css/style.css', '/css/style-medico.css',
					'/js/jquery-3.3.1.js', '/js/login.js', '/js/medicoAjax.js', '/registerSw.js',
					'/js/showAndHide.js', '/js/utils.js', '/js/json.js', '/img/paziente.jpg',
					'/img/medico3.png', '/img/logo_OH_small.png', '/img/logoOH192x192.png',
					'/img/showcase.jpg', '/manifest.json'
					]);
			}).catch(function(err) {
				console.log("SW: Errore nell'apertura della cache quando volevo aggiungere dei file");
			})
	);
});
/* 
 * All'attivazione il sw provvede ad eliminare altre eventuali cache che non fanno parte della PWA attuale
 * (Cache che si chiamano diversamente dalla versione attuale:
 * Ad esempio vecchie versioni di cache usate con service worker precedenti)
 */
self.addEventListener('activate', function(event){
	console.log('SW Activated!');
	/*
	 * Dal momento che il service worker non è costantemente attivo ma può entrare in uno stato di idle,
	 * e dal momento che la funzione caches.open viene eseguita in modo asincrono restituendo una Promise
	 * la poniamo dentro al metodo waitUntil.
	 * In questo modo siamo certi che il sw aspetterà fino a che non verrà eseguita completamente prima di
	 * entrare in stato di idle.
	 */
	event.waitUntil(
			//Remove unwanted caches
			caches.keys().then(keyList => {
				return Promise.all(keyList.map(key => {
					if(key !== cacheName) {
						console.log('SW!');
						return caches.delete(key);
					}
				}));
			}));
	//The claim() method of the Clients allows an active service worker to set itself as
	//the controller for all clients within its scope. 
	return self.clients.claim();
});


/*
 * PRELEVARE LE RISORSE DALLA MEMORIA CACHE: pattern 'cache falling back to the network':
 * Quando il sw intercetta una richiesta http, prova a caricare i contenuti prima dalla cache,
 * se non vi sono inoltra la richiesta alla rete.
 */	

self.addEventListener('fetch', function(event){
	//se non scrivessi nulla il browser di default farebbe la richiesta di fetch al server Web
	//Aggiungiamo il meccanismo di 'cache falling back to the network'
		return event.respondWith(
				//controlliamo se i contenuti nelle cache (in tutte quelle esistenti) fanno match con
				//la richiesta dell'evento di fetch catturato
				//NOTA: "caches" is a global read-only variable, which is an instance of CacheStorage interface
				caches.open(cacheName).then(function(cache) {
					return cache.match(event.request)
					//il metodo match restituisce sempre un result. Se non lo trova result sarà null.
					//Quindi questo .then sarà sempre eseguito poichè match non fallisce mai
					.then(function(res){
						if(res && event.request.headers.get('myRequestType') !== 'forceRefresh'){
							console.log('Found ', event.request.url, ' in cache');
							return res; //ritorna il contenuto dalla cache
						} else{
							//Caso in cui viene richiesto il refresh dei contenuti dall'utente
							if(event.request.headers.get('myRequestType') === 'forceRefresh'){
								console.log('Refreshing cache for content ', event.request.url);
								return fetch(event.request).then(function(response){
									cache.put(event.request.url, response.clone());
									return response;
								})
							}else{
								console.log('Network request for ', event.request.url);
								//ritorna una Promise che caricherà il contenuto dal network
								return fetch(event.request);
							}							
						}
					})
				}).catch(function(err) { // fallback mechanism if the sw can't open the cache specified
					return new Response("Network error happened",
							{"status" : 408, "headers" : {"Content-Type" : "text/plain"}}
					);
				})
		);
});
