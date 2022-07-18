package web.tn.drobee.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(	name = "Offer") 
public class Offer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Size(max = 20)
	private String name;
	
	private double prixunit;

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

	public Offer(@NotBlank @Size(max = 20) String name, double prixunit) {
		this.name = name;
		this.prixunit = prixunit;
	}
	

}
