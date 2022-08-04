package web.tn.drobee.payload.response;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class CommandeReponse {

	private Long id; 
	
    private String username ;
	
	private String offername ;
	
    private Integer nbrunit ;
	
	private String region;
	
	@DateTimeFormat(pattern =("dd/MM/yyyy"))
    private Date date ;
	
	private String status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public CommandeReponse(Long id, String username, String offername, Integer nbrunit, String region, Date date,
			String status) {
		super();
		this.id = id;
		this.username = username;
		this.offername = offername;
		this.nbrunit = nbrunit;
		this.region = region;
		this.date = date;
		this.status = status;
	}
	public CommandeReponse()
	{
		
	}
	
	
}
