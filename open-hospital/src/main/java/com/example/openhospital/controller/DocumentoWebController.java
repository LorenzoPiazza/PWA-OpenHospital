package com.example.openhospital.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
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

@RestController
public class DocumentoWebController {
	@Autowired
	DocumentoRepository repository;
	@Autowired
	PazienteRepository pazienteRepository;
	@Autowired
	MedicoRepository medicoRepository;
	
	//CREATE
	@RequestMapping(value="/documenti/create", method=RequestMethod.POST, consumes= {"application/json"} )
	public Documento postDocumento(@RequestBody Documento documento) {
		Optional<Medico> fullDataMedico=medicoRepository.findById(documento.getMedico().getId());
		Optional<Paziente> fullDataPaziente=pazienteRepository.findById(documento.getPaziente().getId());
		if(fullDataMedico.isPresent() && fullDataPaziente.isPresent()) {
			Medico fullMedico=fullDataMedico.get();
			Paziente fullPaziente=fullDataPaziente.get();
			Documento _documento= repository.save(new Documento(documento.getData(), documento.getDescrizione(),
																	documento.getTipologia(), fullPaziente, fullMedico));
			return _documento;
		}else {
			try{
				if(!fullDataMedico.isPresent()) {
					System.out.println("Medico with ID = " + documento.getMedico().getId() + " non trovato");
					throw new Exception("BAD_REQUEST: Medico with ID = " + documento.getMedico().getId() + " non trovato");
				}
				if(!fullDataPaziente.isPresent()) {
					System.out.println("Paziente with ID = " + documento.getPaziente().getId() + " non trovato");
					throw new Exception("BAD_REQUEST: Paziente with ID = " + documento.getPaziente().getId() + " non trovato");
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return null;	
	}
	
	//INITIALIZE
	@RequestMapping("/documenti/initialize")
	public String initialize() {
		//salva un solo documento
		//assegnandogli il primo paziente della lista
		Paziente paziente = pazienteRepository.findAll().iterator().next();
		Medico medico=medicoRepository.findAll().iterator().next();
		Calendar calendar=Calendar.getInstance(TimeZone.getTimeZone("Europe/Rome"),Locale.ITALY);
		repository.save(new Documento(calendar.getTime(), "Pillole compresse 50mg", Documento.Tipologia.PRESCRIZIONE, paziente, medico));
		
		//salva una lista di nuovi documenti
		calendar.add(Calendar.DAY_OF_MONTH, 2);
		repository.saveAll(Arrays.asList(new Documento(calendar.getTime(), "Voltaren", Documento.Tipologia.PRESCRIZIONE, paziente, medico),
											new Documento(calendar.getTime(), "Esami del sangue", Documento.Tipologia.PRENOTAZIONE, paziente, medico)));
		return "Inizializzazione del db-tabella documenti terminata";
	}

	
	//RETRIEVE	
	@GetMapping("/documenti")
	public List<Documento> getAllDocumenti(){
		System.out.println("Get all Documenti...");
		 
		List<Documento> documenti = new ArrayList<>();
		repository.findAll().forEach(documenti::add); //per ogni elemento n della repository.findAll() invoca il metodo documenti.add(elemento n)
 
		return documenti;
	}
	
	@RequestMapping("/documenti/findbycodicefiscale")
	public String findByCodiceFiscale(@RequestParam("codiceFiscale") String codFisc) {
		String result="";
		Paziente p = pazienteRepository.findByCodiceFiscale(codFisc);
		for (Documento d : p.getDocumenti())
			result=result+d.toString()+"<br>";
		
		return result;
	
	}
	
	@RequestMapping("/documenti/findbycodicefiscale_tipologia")
	public String findByTipologia(@RequestParam("tipologia") String tipologia, @RequestParam("codiceFiscale") String codFisc) {
		String result="";
		Paziente p = pazienteRepository.findByCodiceFiscale(codFisc);
		for (Documento d : p.getDocumenti()) {
			if(d.getTipologia().toString().equalsIgnoreCase(tipologia))
				result=result+d.toString()+"<br>";
		}
		return result;
	}
	
	//UPDATE
	@PutMapping("/documenti/{id}")
	public ResponseEntity<Documento> updateDocumento(@PathVariable("id") long id, @RequestBody Documento documento) {
		Optional<Documento> documentoData = repository.findById(id);
 
		if (documentoData.isPresent()) {
			System.out.println("Update Documento with ID = " + id + "...");
			Documento _documento= documentoData.get();
			_documento.setData(documento.getData());
			_documento.setDescrizione(documento.getDescrizione());
			_documento.setTipologia(documento.getTipologia());
			_documento.setPaziente(documento.getPaziente());
			return new ResponseEntity<>(repository.save(_documento), HttpStatus.OK);
		} else {
			System.out.println("Documento with ID = " + id + " non trovato");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	//DELETE
	@DeleteMapping("/documenti/{id}")
	public ResponseEntity<String> deleteDocumento(@PathVariable("id") long id) {
		System.out.println("Delete Documento with ID = " + id + "...");

		repository.deleteById(id);

		return new ResponseEntity<>("Il documento indicato e' stato cancellato dal db!", HttpStatus.OK);
	}

	@DeleteMapping("/documenti/delete")
	public ResponseEntity<String> deleteAllDocumenti() {
		System.out.println("Delete All Documenti...");

		repository.deleteAll();

		return new ResponseEntity<>("Tutti i documenti sono stati eliminati dal db!", HttpStatus.OK);
	}
}
	

