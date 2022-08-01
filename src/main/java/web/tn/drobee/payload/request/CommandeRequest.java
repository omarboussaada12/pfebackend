package web.tn.drobee.payload.request;

public class CommandeRequest {
	
	private String username ;
	
	private String offername ;
	
    private Integer nbrunit ;
	
	private String region;

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

}
