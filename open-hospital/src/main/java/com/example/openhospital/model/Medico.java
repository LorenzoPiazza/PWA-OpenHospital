package com.example.openhospital.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity
@Table(name = "medici")
public class Medico implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO) //GenerationType.AUTO: Hibernate decides which generator type to use, based on the database’s support for primary key generation
	private Long id;
	
	@Column(name="cognome")
	private String cognome;
	
	@Column(name="nome")
	private String nome;
	
	@Column(name="telefono")
	private String telefono;
	
	@Column(name="ambulatorio")
	private String ambulatorio;
	
	@ManyToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinTable(name="medici_pazienti", joinColumns= @JoinColumn(name="medico_id", referencedColumnName="id"), inverseJoinColumns=@JoinColumn(name="paziente_id", referencedColumnName="id"))
	@JsonIgnoreProperties({"luogoNascita", "dataNascita", "residenza", "codiceFiscale", "email", "telefono", "medici"})
	private Set<Paziente> pazienti=new HashSet<>();
	
	@OneToMany(mappedBy="medico")
	@JsonIgnore
	private Set<Documento> documenti = new HashSet<>();
	
	//Costruttori (uno protected che verrà usato da SpringJPA e uno pubblico per creare nuove istanze)
	protected Medico() {
		
	}
	
	//COSTRUTTORE DI UN MEDICO SENZA LISTA DI PAZIENTI NE DI DOCUMENTI
	public Medico(String nome, String cognome, String telefono, String ambulatorio) {
		super();
		this.cognome = cognome;
		this.nome = nome;
		this.telefono = telefono;
		this.ambulatorio = ambulatorio;
	}
	
	//COSTRUTTORE DI UN MEDICO CON LA LISTA DI PAZIENTI
	public Medico(String nome, String cognome, String telefono, String ambulatorio, Set<Paziente> pazienti) {
		super();
		this.cognome = cognome;
		this.nome = nome;
		this.telefono = telefono;
		this.ambulatorio = ambulatorio;
		this.pazienti=pazienti;
	}
	
	
	//metodi Getter e Setter
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getAmbulatorio() {
		return ambulatorio;
	}

	public void setAmbulatorio(String ambulatorio) {
		this.ambulatorio = ambulatorio;
	}

	public Long getId() {
		return id;
	}
	
	public Set<Paziente> getPazienti(){
		return this.pazienti;
	}
	
	public void setPazienti(Set<Paziente> pazienti) {
		this.pazienti = pazienti;
	}
	
	public Set<Documento> getDocumenti(){
		return this.documenti;
	}
	
	public void setDocumenti(Set<Documento> documenti) {
		this.documenti = documenti;
	}
	
	@Override
	public String toString() {
		return "Medico [id=" + id + ", nome=" + nome + ", cognome=" + cognome + ", telefono=" + telefono
				+ ", ambulatorio=" + ambulatorio + "]";
	}
	
	
	 @Override
	    public boolean equals(Object o) {
	        if (this == o) return true;
	        if (!(o instanceof Medico)) return false;
	        return id != null && id.equals(((Medico) o).id);
	    }

	
	
	
}
