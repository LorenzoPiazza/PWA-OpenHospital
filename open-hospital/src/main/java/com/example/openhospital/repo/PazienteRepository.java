package com.example.openhospital.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.openhospital.model.Paziente;

public interface PazienteRepository extends CrudRepository<Paziente, Long> {
	List<Paziente> findByCognome(String cognome);
	Paziente findByCodiceFiscale(String codiceFiscale);
}
