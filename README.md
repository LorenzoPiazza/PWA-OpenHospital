# PWA-OpenHospital
In questa repository è presente il mio progetto di Tesi.  
Si tratta di una PWA realtiva al software Open Hospital di Informatici senza Frontiere.    

Il progetto è così strutturato:    

______________________________________________________________________________________________________________________________________________________
  
Nel package src/main/java si trova l'implementazione di un server REST realizzato con SpringBoot.
In particolare, nei sottopackage troviamo:  
-com.example.openhospital: vi è il file con il main dell'applicazione.  
-com.example.openhospital.model: vi sono le classi del modello che sono state mappate, grazie a delle JPA annotations in un 
database postgreSQL.  
-com.example.openhospital.repo: vi sono delle interfaccie utilizzabili per interfacciarsi con i dati nel database.  
-com.example.openhospital.controller: vi sono le classi controller che implementano ed espongono online i servizi del server.  
  
______________________________________________________________________________________________________________________________________________________
  
Nel package src/main/resources invece si trova tutta l'implementazione relativa alla PWA:  
In particolare nella sottocartella /static vi sono:  
-le pagine html  
-i file css  
-i file javascript  
-il service worker (sw.js)  
-il file che viene invocato dalla index.html per registrare il sw (registerSw.js)  
-il file manifest.json  

______________________________________________________________________________________________________________________________________________________

Infine nel file application.properties vi sono alcune impostazioni di configurazione del DataSource, dell'ORM Hibernate e del server 
(numero di porta, politiche di caching...)    

Lorenzo Piazza
