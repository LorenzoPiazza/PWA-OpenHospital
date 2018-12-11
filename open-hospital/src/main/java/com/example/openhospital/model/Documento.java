package com.example.openhospital.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@Entity
@Table(name = "documenti")
public class Documento implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO) //GenerationType.AUTO: Hibernate decides which generator type to use, based on the database’s support for primary key generation.
	private Long id;
	
	@Column(name="data")
	@Temporal(TemporalType.DATE)
	private Date data;
	
	@Column(name="descrizione")
	private String descrizione;
	
	public enum Tipologia {
		REFERTO,
		PRESCRIZIONE,
		PRENOTAZIONE
	};
	
	@Column(name="tipologia")	//Referto,Prescrizione,Prenotazione...
	private Tipologia tipologia;
	
	@ManyToOne
	@JoinColumn (name="paziente_id")
	@JsonIgnoreProperties({"nome", "cognome", "luogoNascita", "dataNascita", "residenza", "codiceFiscale", "email", "telefono", "medici"})
	private Paziente paziente;
	
	@ManyToOne
	@JoinColumn (name="medico_id")
	@JsonIgnoreProperties({"ambulatorio", "nome", "cognome", "telefono", "pazienti"})
	private Medico medico;
	
	//Costruttori (uno protected che verrà usato da SpringJPA e uno pubblico per creare nuove istanze)
	
	protected Documento() {
		
	}
		
	public Documento(Date data, String descrizione, Tipologia tipologia, Paziente paziente, Medico medico) {
		super();
		this.data = data;
		this.descrizione = descrizione;
		this.tipologia = tipologia;
		this.paziente = paziente;
		this.medico = medico;
	}

	//metodi Getter e Setter
	public Long getId() {
		return id;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public Tipologia getTipologia() {
		return tipologia;
	}

	public void setTipologia(Tipologia tipologia) {
		this.tipologia = tipologia;
	}
	
	public Paziente getPaziente() {
		return paziente;
	}

	public void setPaziente(Paziente paziente) {
		this.paziente = paziente;
	}
	

	public Medico getMedico() {
		return medico;
	}

	public void setMedico(Medico medico) {
		this.medico = medico;
	}

	@Override
	public String toString() {
		return "Documento [id=" + id + ", data=" + data + ", descrizione=" + descrizione + ", tipologia=" + tipologia
				+ ", idPaziente=" + paziente.getId() + " idMedico=" + medico.getId() +"]";
	}
	
	 @Override
	    public boolean equals(Object o) {
	        if (this == o) return true;
	        if (!(o instanceof Documento)) return false;
	        return id != null && id.equals(((Documento) o).id);
	    }
	
	
	
	
}
