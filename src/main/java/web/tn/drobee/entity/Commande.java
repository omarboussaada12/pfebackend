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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(	name = "commande")
public class Commande {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	 
    @ManyToOne(fetch = FetchType.LAZY )
	@JoinColumn(name = "offername", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Offer offer;
	 
	private Integer nbrunit ;
	
	private String region;
	
	@ManyToOne(fetch = FetchType.LAZY )
	@JoinColumn(name = "username", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private User user;
	
	private Date date ;
	
	private String status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Offer getOffer() {
		return offer;
	}

	public void setOffer(Offer offer) {
		this.offer = offer;
	}

	public Integer getNbrunit() {
		return nbrunit;
	}

	public void setNbrunit(Integer nbrunit) {
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

	public Commande(Offer offer, Integer nbrunit, String region, User user, Date date, String status) {
		super();
		this.offer = offer;
		this.nbrunit = nbrunit;
		this.region = region;
		this.user = user;
		this.date = date;
		this.status = status;
	}
	
	
}
