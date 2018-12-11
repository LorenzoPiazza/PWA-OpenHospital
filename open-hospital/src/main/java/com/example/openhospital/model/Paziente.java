package com.example.openhospital.model;

import java.io.Serializable;

import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.persistence.*;

import com.example.openhospital.repo.MedicoRepository;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "pazienti")
public class Paziente implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name="nome")
	private String nome;
	
	@Column(name="cognome")
	private String cognome;
	
	@Column(name="luogoNascita")
	private String luogoNascita;
	
	@Column(name="dataNascita")
	@Temporal(TemporalType.DATE)
	private Date dataNascita;
	
	@Column(name="residenza")
	private String residenza;
	
	@Column(name="codiceFiscale", unique=true)
	private String codiceFiscale;
	
	@Column(name="email")
	private String email;	
	
	@Column(name="telefono")
	private String telefono;
	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name="medici_pazienti", joinColumns= @JoinColumn(name="paziente_id", referencedColumnName="id"),
				inverseJoinColumns=@JoinColumn(name="medico_id", referencedColumnName="id"))
	@JsonIgnoreProperties({"ambulatorio", "telefono", "pazienti"})
	private Set<Medico> medici=new HashSet<>(); 

	@OneToMany(mappedBy="paziente")
	@JsonIgnore
	private Set<Documento> documenti=new HashSet<>();
	
	
	//Costruttori (uno protected che verrà usato da SpringJPA e uno pubblico per creare nuove istanze)
	protected Paziente() {
		
	}

	//COSTRUTTORE DI UN PAZIENTE SENZA LISTA DI MEDICI NE DOCUMENTI
	public Paziente(String nome, String cognome, String luogoNascita, Date dataNascita, String residenza,
			String codiceFiscale, String email, String telefono) {
		
		super();
		this.nome = nome;
		this.cognome = cognome;
		this.luogoNascita = luogoNascita;
		this.dataNascita = dataNascita;
		this.residenza = residenza;
		this.codiceFiscale = codiceFiscale;
		this.email = email;
		this.telefono = telefono;
	}

	
	//COSTRUTTORE DI UN PAZIENTE SENZA LISTA DI DOCUMENTI, passando una lista di medici con il solo id
	public Paziente(String nome, String cognome, String luogoNascita, Date dataNascita, String residenza,
			String codiceFiscale, String email, String telefono, Set<Medico> medici, MedicoRepository medicoRepository) {
		super();
		this.nome = nome;
		this.cognome = cognome;
		this.luogoNascita = luogoNascita;
		this.dataNascita = dataNascita;
		this.residenza = residenza;
		this.codiceFiscale = codiceFiscale;
		this.email = email;
		this.telefono = telefono;
		this.medici=new HashSet<>();
		for(Medico m : medici) {
			Medico medico;
			Optional<Medico> medico_data;
			medico_data=medicoRepository.findById(Long.valueOf(m.getId()));
			if(medico_data.isPresent()) {
				medico=medico_data.get();
				this.medici.add(medico);
			}
		}

	}


	
	//COSTRUTTORE DI UN PAZIENTE CON LISTA DI MEDICI E DI DOCUMENTI
	public Paziente(String nome, String cognome, String luogoNascita, Date dataNascita, String residenza,
			String codiceFiscale, String email, String telefono, Set<Medico> medici, Set<Documento> documenti) {
		super();
		this.nome = nome;
		this.cognome = cognome;
		this.luogoNascita = luogoNascita;
		this.dataNascita = dataNascita;
		this.residenza = residenza;
		this.codiceFiscale = codiceFiscale;
		this.email = email;
		this.telefono = telefono;
		this.medici = medici;
		this.documenti = documenti;
	}


	public Long getId() {
		return id;
	}

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


	public String getLuogoNascita() {
		return luogoNascita;
	}


	public void setLuogoNascita(String luogoNascita) {
		this.luogoNascita = luogoNascita;
	}


	public Date getDataNascita() {
		return dataNascita;
	}


	public void setDataNascita(Date dataNascita) {
		this.dataNascita = dataNascita;
	}


	public String getResidenza() {
		return residenza;
	}


	public void setResidenza(String residenza) {
		this.residenza = residenza;
	}


	public String getCodiceFiscale() {
		return codiceFiscale;
	}


	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getTelefono() {
		return telefono;
	}


	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public Set<Medico> getMedici() {
		return medici;
	}

	public void setMedici(Set<Medico> medici) {
		this.medici = medici;
	}

	
	public Set<Documento> getDocumenti() {
		return documenti;
	}


	public void setDocumenti(Set<Documento> documenti) {
		this.documenti = documenti;
	}


	@Override
	public String toString() {
		return "Paziente [id=" + id + ", nome=" + nome + ", cognome=" + cognome + ", luogoNascita=" + luogoNascita
				+ ", dataNascita=" + dataNascita + ", residenza=" + residenza + ", codiceFiscale=" + codiceFiscale
				+ ", email=" + email + ", telefono=" + telefono + "]";
	}
	
	 @Override
	    public boolean equals(Object o) {
	        if (this == o) return true;
	        if (!(o instanceof Paziente)) return false;
	        return id != null && id.equals(((Paziente) o).id);
	    }
	
	
	
}
