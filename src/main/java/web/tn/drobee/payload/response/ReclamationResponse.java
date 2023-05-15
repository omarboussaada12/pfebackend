package web.tn.drobee.payload.response;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class ReclamationResponse {

	private Long  id ;
	
	private String message;

	private String username ;
	
	@DateTimeFormat(pattern = ("dd/MM/yyyy"))
	private Date date;

	private String status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	public ReclamationResponse(Long id, String message, String username, Date date, String status) {
		
		this.id = id;
		this.message = message;
		this.username = username;
		this.date = date;
		this.status = status;
	}

	public ReclamationResponse() {
		
	}
	
}
