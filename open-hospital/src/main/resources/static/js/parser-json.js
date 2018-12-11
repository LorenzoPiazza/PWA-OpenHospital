/*Lorenzo Piazza 0000766393*/

/*INSIEME DI FUNZIONI UTILI PER PARSARE UNA RISPOSTA RICEVUTA IN FORMATO JSON:*/


/* ParsificaJson va invocata nella funzione di callback al readystate==4 passando il ajax.reponseText
 * Vedi esempio seguente:


  if ( theXhr.readyState === 4 ) {

		// verifica della risposta da parte del server
	        if ( theXhr.status === 200 ) {

	        	// operazione avvenuta con successo
			if ( theXhr.responseText ) {
			        theElement.innerHTML = parsificaJSON(theXhr.responseText);  <-------
			}// if XML
 */




// funzione che, ricevuta una stringa JSON, la trasforma in un oggetto Javascript e poi restituisce la stampa dello stesso
// TODO:(ATTENZIONE ai nomi degli attributi!!)
function parsificaJSON( jsonText ) {
	var mioOggetto = JSON.parse(jsonText);
	var risultato = mioOggetto.nomeAttributo1+" "+mioOggetto.nomeAttributo2+" "+mioOggetto.nomeAttributo3+"<br />" ;
    return risultato;
}// parsificaJSON()


// funzione che, ricevuta una stringa JSON, la trasforma in un array Javascript e poi restituisce la stampa dello stesso
// TODO:(ATTENZIONE ai nomi degli attributi!!)
function parsificaJSONArray(jsonText) {
	var something = JSON.parse(jsonText);
	var risultato = "<ul>";
	for (var i = 0; i < something.length; i++) {
		if (something[i] != null)
			risultato += "<li>" + something[i].message + "</li>";
	}
	risultato += "</ul>";

	return risultato;
}



/*PARSER PAZIENTI*/

function parsificaJSONPaziente( jsonText ) {
	var paziente = JSON.parse(jsonText);
	var risultato = paziente.nome+" "+paziente.cognome+" "+paziente.luogoNascita+" "+paziente.dataNascita+" "+paziente.residenza+" "+paziente.codiceFiscale+" "+paziente.email+" "+paziente.telefono+"<br />" ;
    return risultato;
}// parsificaJSONPaziente()

function parsificaJSONPazienti(jsonText) {
	var pazienti = JSON.parse(jsonText);
	var risultato = "<ul>";
	for (var i = 0; i < pazienti.length; i++) {
		if (pazienti[i] != null)
			risultato += "<li>" + parsificaJSONPaziente(pazienti[i]) + "</li>";
	}
	risultato += "</ul>";

	return risultato;
}
