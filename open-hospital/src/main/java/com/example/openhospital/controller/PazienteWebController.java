package com.example.openhospital.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;
import java.util.TimeZone;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.openhospital.model.Documento;
import com.example.openhospital.model.Medico;
import com.example.openhospital.model.Paziente;
import com.example.openhospital.repo.DocumentoRepository;
import com.example.openhospital.repo.MedicoRepository;
import com.example.openhospital.repo.PazienteRepository;
import com.google.gson.Gson;

@RestController
public class PazienteWebController {
	@Autowired
	PazienteRepository repository;
	@Autowired
	MedicoRepository medicoRepository;
	@Autowired
	DocumentoRepository documentoRepository;
	
	Gson gson = new Gson();
	
	//INITIALIZE
	@RequestMapping("/pazienti/initialize")
	public String initialize() {
		//salva un solo paziente
		//assegnandogli il primo medico dell'elenco
		Medico medico=medicoRepository.findAll().iterator().next();
		Set<Medico> medici=new HashSet<>();
		medici.add(medico);
		Calendar calendar=Calendar.getInstance(TimeZone.getTimeZone("Europe/Rome"),Locale.ITALY);
		calendar.set(1996, 9, 25);
		
		repository.save(new Paziente("Pippo", "Rossi", "Cspt", calendar.getTime(), "Via Nera", "PPPRSS9888", "pippo@gmail", "3344567889", medici, medicoRepository));
		
		//salva una lista di nuovi pazienti... TODO
		
		return "Inizializzazione del db-tabella pazienti terminata";
	}

	//CREATE
	@RequestMapping(value="/pazienti/create", method=RequestMethod.POST, consumes= {"application/json"} )
	public Paziente postPaziente(@RequestBody Paziente paziente) {
		Paziente _paziente= repository.save(new Paziente(paziente.getNome(), paziente.getCognome(), paziente.getLuogoNascita(), paziente.getDataNascita(), paziente.getResidenza(), paziente.getCodiceFiscale(), paziente.getEmail(), paziente.getTelefono(), paziente.getMedici(), medicoRepository));
		return _paziente;
	}
	
	//RETRIEVE	
	@RequestMapping(value="/pazienti", method=RequestMethod.GET, produces= {"application/json"} )
	@CrossOrigin
	public List<Paziente> getAllPazienti(){
		System.out.println("Get all Pazienti...");
		 
		List<Paziente> pazienti = new ArrayList<>();
		repository.findAll().forEach(pazienti::add); //per ogni elemento n della repository.findAll() invoca il metodo pazienti.add(elemento n)
 
		return pazienti;
	}
	
	@RequestMapping("/pazienti/findbylastname")
	public String findByLastName(@RequestParam("cognome") String cognome) {
		String result="";
		for(Paziente m : repository.findByCognome(cognome)) {
			result=result+m.toString()+"<br>";
		}
		
		return result;
	}
	
	@RequestMapping("/pazienti/findbycodicefiscale")
	public String findByCodiceFiscale(@RequestParam("codFisc") String codFisc) {
		Paziente p = null;
		p=repository.findByCodiceFiscale(codFisc);
		return p.toString()+"<br>";
	}
	
	@RequestMapping("/pazienti/mieidocumenti")
	public Set<Documento> findMyDocuments(@RequestParam("paziente_id") String paziente_id) {
		Paziente p;
		Optional<Paziente> pazienteData=repository.findById(Long.parseLong(paziente_id));
		if(pazienteData.isPresent()) {
			p=pazienteData.get();
			return p.getDocumenti();
		}else {
			System.out.println("Paziente with ID = " + paziente_id + " non trovato");
			return new HashSet<Documento>();
		}
	}
	
	//UPDATE
	@PutMapping("/pazienti/{id}")
	public ResponseEntity<Paziente> updatePaziente(@PathVariable("id") long id, @RequestBody Paziente paziente) {
		Optional<Paziente> pazienteData = repository.findById(id);
 
		if (pazienteData.isPresent()) {
			System.out.println("Update Paziente with ID = " + id + "...");
			Paziente _paziente = pazienteData.get();
			_paziente.setNome(paziente.getNome());
			_paziente.setCognome(paziente.getCognome());
			_paziente.setDataNascita(paziente.getDataNascita());
			_paziente.setLuogoNascita(paziente.getLuogoNascita());
			_paziente.setResidenza(paziente.getResidenza());
			_paziente.setEmail(paziente.getEmail());
			_paziente.setCodiceFiscale(paziente.getCodiceFiscale());
			_paziente.setTelefono(paziente.getTelefono());
			_paziente.setMedici(paziente.getMedici());
			_paziente.setDocumenti(paziente.getDocumenti());
			return new ResponseEntity<>(repository.save(_paziente), HttpStatus.OK);
		} else {
			System.out.println("Paziente with ID = " + id + " non trovato");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	//DELETE
	@DeleteMapping("/pazienti/{id}")
	public ResponseEntity<String> deletePaziente(@PathVariable("id") long id) {
		System.out.println("Delete Paziente with ID = " + id + "...");

		repository.deleteById(id);

		return new ResponseEntity<>("Il paziente indicato e' stato cancellato dal db!", HttpStatus.OK);
	}

	@DeleteMapping("/pazienti/delete")
	public ResponseEntity<String> deleteAllPazienti() {
		System.out.println("Delete All Pazienti...");

		repository.deleteAll();

		return new ResponseEntity<>("Tutti i pazienti sono stati eliminati dal db!", HttpStatus.OK);
	}
}
	

