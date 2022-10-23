package web.tn.drobee.payload.response;

import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import web.tn.drobee.entity.FileDB;

public class OfferResponse {
	
	private Long id;

	
	private String name;
	
	private double prixunit;
	
	private String description ;
	
	private ResponseFile image ;

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
