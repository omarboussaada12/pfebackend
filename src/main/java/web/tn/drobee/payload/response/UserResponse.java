package web.tn.drobee.payload.response;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import web.tn.drobee.entity.Role;

public class UserResponse {
	
    private long id ;
    
    private ResponseFile image ;
    
    @NotBlank
	@Size(max = 20)
	private String username;
	
	@NotBlank
	@Size(max = 20)
	private String Firstname;
	
	@NotBlank
	@Size(max = 20)
	private String lastname;

	@NotBlank
	@Size(max = 15)
	private String phone;
	
	@NotBlank
	@Size(max = 50)
	private String address;

	@NotBlank
	@Size(max = 50)
	@Email
	private String email;
	
	private Set<Role> roles = new HashSet<>();
	

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstname() {
		return Firstname;
	}

	public void setFirstname(String firstname) {
		Firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public ResponseFile getImage() {
		return image;
	}

	public void setImage(ResponseFile image) {
		this.image = image;
	}
	
}
