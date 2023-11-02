package web.tn.drobee.payload.response;

import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import web.tn.drobee.entity.FileDB;
import web.tn.drobee.entity.Offer;
import web.tn.drobee.repo.FileDBRepository;
import web.tn.drobee.repo.OfferRepository;
import web.tn.drobee.service.FileStorageService;
import web.tn.drobee.service.IOfferService;

public class OfferResponse {
	@Autowired
	FileStorageService storageService;
	
	@Autowired
	FileDBRepository fileDBRepository ;
	
	@Autowired
	OfferRepository offerRepository;
	
	@Autowired
	IOfferService offerService;
	
	private Long id;

	
	private String name;
	
	private double prixunit;
	
	private String description ;
	
	private ResponseFile image ;

	

	public OfferResponse(Long id, String name, double prixunit, String description, ResponseFile image) {
		super();
		this.id = id;
		this.name = name;
		this.prixunit = prixunit;
		this.description = description;
		this.image = image;
	}

	public OfferResponse() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrixunit() {
		return prixunit;
	}

	public void setPrixunit(double prixunit) {
		this.prixunit = prixunit;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ResponseFile getImage() {
		return image;
	}

	public void setImage(ResponseFile image) {
		this.image = image;
	}

}
