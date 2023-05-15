package web.tn.drobee.payload.request;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class CommandeRequest {
	
	private Long  id ;

	private String username ;
	
	private String offername ;
	
    private int nbrunit ;
	
	private String region;
	
	@DateTimeFormat(pattern = ("dd/MM/yyyy"))
	private Date datec;
	
	

	

	public Date getDatec() {
		return datec;
	}

	public void setDatec(Date datec) {
		this.datec = datec;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getOffername() {
		return offername;
	}

	public void setOffername(String offername) {
		this.offername = offername;
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
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
