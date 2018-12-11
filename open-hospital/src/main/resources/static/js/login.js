function setCookie(cname, cvalue, exdays) {
    var d = new Date();
    d.setTime(d.getTime() + (exdays*24*60*60*1000));
    var expires = "expires="+ d.toUTCString();
    document.cookie = cname + "=" + cvalue + ";" + expires + ";path=/";
}

function getCookie(cname) {
    var name = cname + "=";
    var decodedCookie = decodeURIComponent(document.cookie);
    var ca = decodedCookie.split(';');
    for(var i = 0; i <ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0) == ' ') {
            c = c.substring(1);
        }
        if (c.indexOf(name) == 0) {
            return c.substring(name.length, c.length);
        }
    }
    return "";
}

function deleteCookie(cname) {
    var d = new Date(); //Create a date object
    d.setTime(d.getTime() - (1000*60*60*24)); //Set the time to the past. 1000 milliseconds = 1 second
    var expires = "expires=" + d.toGMTString(); //Compose the expiration date
    window.document.cookie = cname+"="+"; "+expires;//Set the cookie with name and the expiration date
 
}
var cacheName='OH_cacheMemory_v1';
function loginRedirector(role) {
    if(role.localeCompare("medico")==0){
    	setCookie("id", $('#id').val(), 1);
    	//Al momento del login salvo in cache alcuni contenuti che possono essere utili come la lista di pazienti del medico che si è appena loggato
    	caches.open(cacheName)
		.then(function(cache){
    		cache.add('http://localhost:8081/medici/findpazienti?medico_id='+getCookie("id"));
    		window.location.href="./medico.html";
		})
    }   
    else
      window.location.href="./paziente.html";
  }

function logout() {
	deleteCookie("id");
    window.location.href="./index.html";
  }