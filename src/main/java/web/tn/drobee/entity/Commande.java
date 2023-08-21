package web.tn.drobee.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "commande")
public class Commande {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "offer_id", nullable = false)
	private Offer offer;

	private int nbrunit;

	private String region;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	//date you placed a command 
	@DateTimeFormat(pattern = ("dd/MM/yyyy"))
	private Date date;
	
	//date you choose for your command
	@DateTimeFormat(pattern = ("dd/MM/yyyy"))
	private Date datec;

	private String status;

	public Offer getOffer() {
		return offer;
	}

	public void setOffer(Offer offer) {
		this.offer = offer;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getNbrunit() {
		return nbrunit;
	}

	public void setNbrunit(int nbrunit) {
		this.nbrunit = nbrunit;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	

	public Date getDatec() {
		return datec;
	}

	public void setDatec(Date datec) {
		this.datec = datec;
	}

	public Commande(Offer offer, int nbrunit, String region, User user, Date date, String status , Date datec) {
		super();
		this.nbrunit = nbrunit;
		this.region = region;
		this.user = user;
		this.offer = offer;
		this.date = date;
		this.status = status;
		this.datec = datec;
	}

	public Commande() {

	}

	

}
