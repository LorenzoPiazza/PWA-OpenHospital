package com.example.openhospital.controller;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.openhospital.model.Documento;
import com.example.openhospital.model.Medico;
import com.example.openhospital.model.Paziente;
import com.example.openhospital.repo.MedicoRepository;

@RestController
public class MedicoWebController {
	@Autowired
	MedicoRepository repository;
	
	//INITIALIZE
	@RequestMapping("/medici/initialize")
	public String initialize() {
		//salva un solo medico
		repository.save(new Medico("Giacomo", "Gaddoni", "3344567889", "OspedaleCSPT"));
		
		//salva una lista di nuovi medici
		repository.saveAll(Arrays.asList(new Medico("Matteo", "Lapadula", "321456789", "Rizzoli"), 
											new Medico("Luca", "Parenti", "321453333", "S.Orsola")));
		return "Inizializzazione del db-tabella medici terminata";
	}

	//CREATE
	//SEGUE UN ESEMPIO DI RICHIESTA POST tramite cURL da shell Windows:
	//curl localhost:8080/medici/create -X POST -H "Accept:application/json" -H "Content-Type:application/json" -d"{\"nome\": \"Lorenzo\", \"cognome\": \"Piazza\", \"telefono\": \"33334567\", \"ambulatorio\": \"stadio\"}"
	
	@PostMapping(value = "/medici/create")
	public Medico postMedico(@RequestBody Medico medico) {
 
		Medico _medico = repository.save(new Medico(medico.getNome(), medico.getCognome(), medico.getTelefono(), medico.getAmbulatorio()));
		return _medico;
	}
	
	//RETRIEVE	
	@GetMapping("/medici")
	public Set<Medico> getAllMedici(){
		System.out.println("Get all Medici...");
		 
		Set<Medico> medici = new HashSet<>();
		repository.findAll().forEach(medici::add); //per ogni elemento n della repository.findAll() invoca il metodo medici.add(elemento n)
 
		return medici;
	}
	
	@RequestMapping("/medici/findbylastname")
	public String findByLastName(@RequestParam("cognome") String cognome) {
		String result="";
		for(Medico m : repository.findByCognome(cognome)) {
			result=result+m.toString()+"<br>";
		}
		
		return result;
	}
	
	@RequestMapping(value="/medici/findpazienti", method=RequestMethod.GET)
	public Set<Paziente> findPazienti(@RequestParam("medico_id") String medico_id) {
		Optional<Medico> medicoData = repository.findById(Long.valueOf(medico_id));
		Medico m;
		if(medicoData.isPresent()) {
			m = medicoData.get();
			return m.getPazienti();
		}
		else {
			System.out.println("Medico with ID = " + medico_id + " non trovato");
			return new HashSet<Paziente>();
		}
	}
	
	@RequestMapping("/medici/documentipaziente")
	public Set<Documento> findDocumentiPazienti(@RequestParam("medico_id") String medico_id, @RequestParam("paziente_id") String paziente_id) {
		Optional<Medico> medicoData = repository.findById(Long.valueOf(medico_id));
		Medico m;
		if(medicoData.isPresent()) {
			m = medicoData.get();
			for (Paziente p : m.getPazienti()) {
				if (p.getId().equals(Long.valueOf(paziente_id)) ) {
					return p.getDocumenti();
				}
			}
			return new HashSet<Documento>();
		}
		else {
			System.out.println("Medico with ID = " + medico_id + " non trovato");
			return new HashSet<>();
		}
	}
	
	//UPDATE
	@PutMapping("/medici/{id}")
	public ResponseEntity<Medico> updateMedico(@PathVariable("id") long id, @RequestBody Medico medico) {
		
 
		Optional<Medico> medicoData = repository.findById(id);
 
		if (medicoData.isPresent()) {
			System.out.println("Update Medico with ID = " + id + "...");
			Medico _medico = medicoData.get();
			_medico.setNome(medico.getNome());
			_medico.setCognome(medico.getCognome());
			_medico.setTelefono(medico.getTelefono());
			_medico.setAmbulatorio(medico.getAmbulatorio());
			return new ResponseEntity<>(repository.save(_medico), HttpStatus.OK);
		} else {
			System.out.println("Medico with ID = " + id + " non trovato");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	//DELETE
	@DeleteMapping("/medici/{id}")
	public ResponseEntity<String> deleteMedico(@PathVariable("id") long id) {
		System.out.println("Delete Medico with ID = " + id + "...");

		repository.deleteById(id);

		return new ResponseEntity<>("Il medico indicato e' stato cancellato dal db!", HttpStatus.OK);
	}

	@DeleteMapping("/medici/delete")
	public ResponseEntity<String> deleteAllMedici() {
		System.out.println("Delete All Medici...");

		repository.deleteAll();

		return new ResponseEntity<>("Tutti i medici sono stati eliminati dal db!", HttpStatus.OK);
	}
}
	

