package com.example.openhospital.repo;

import java.util.Set;

import org.springframework.data.repository.CrudRepository;

import com.example.openhospital.model.Medico;

public interface MedicoRepository extends CrudRepository<Medico, Long> {
	Set<Medico> findByCognome(String cognome);
}
