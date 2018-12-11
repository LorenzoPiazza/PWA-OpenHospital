package com.example.openhospital;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.filter.ShallowEtagHeaderFilter;
import org.springframework.context.annotation.Bean;

import com.example.openhospital.controller.DocumentoWebController;
import com.example.openhospital.controller.MedicoWebController;
import com.example.openhospital.controller.PazienteWebController;
import com.example.openhospital.repo.DocumentoRepository;
import com.example.openhospital.repo.MedicoRepository;
import com.example.openhospital.repo.PazienteRepository;


@SpringBootApplication
public class OpenHospitalApplication implements CommandLineRunner{

	@Autowired
	MedicoRepository repositoryM;
	@Autowired
	PazienteRepository repositoryP;
	@Autowired
	DocumentoRepository repositoryD;
	
	@Autowired
	MedicoWebController controllerM;
	@Autowired
	PazienteWebController controllerP;
	@Autowired
	DocumentoWebController controllerD;
	
	public static void main(String[] args) {
		SpringApplication.run(OpenHospitalApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// clear all record if existed before working with new data 
		repositoryM.deleteAll();
		repositoryP.deleteAll();
		repositoryD.deleteAll();
		
		//inizializza correttamente lo stato del db: nell'ordine 1)medici/initialize, 2)pazienti/initialize, 3)documenti/initialize
		controllerM.initialize();
		controllerP.initialize();
		controllerD.initialize();
	}
	
	
	//Bean che serve per inserire un ETag nell'header http delle response.
    @Bean
    public Filter filter(){
        ShallowEtagHeaderFilter filter=new ShallowEtagHeaderFilter();
        return filter;
    }
}
