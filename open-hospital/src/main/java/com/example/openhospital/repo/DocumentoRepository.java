package com.example.openhospital.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.openhospital.model.Documento;

public interface DocumentoRepository extends CrudRepository<Documento, Long> {
	List<Documento> findByTipologia(String tipologia);
}
