package web.tn.drobee.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Entity
@Table(	name = "users", 
		uniqueConstraints = { 
			@UniqueConstraint(columnNames = "username"),
			@UniqueConstraint(columnNames = "email") 
		})
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

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

	@NotBlank
	@Size(max = 120)
	private String password;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(	name = "user_roles", 
				joinColumns = @JoinColumn(name = "user_id"), 
				inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();
	
	
	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "image_id")
	private FileDB image;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public void setRoles(Role role) {
		this.roles.clear(); 
		this.roles.add(role);
	}

	public FileDB getImage() {
		return image;
	}

	public void setImage(FileDB image) {
		this.image = image;
	}
	
	public User() {
	}

	public User(String username, String firstname, String lastname, String phone, String address , String email, String password ) {
		this.username = username;
		this.Firstname = firstname;
		this.lastname = lastname ;
		this.phone = phone ;
		this.address = address ;
		this.email = email;
		this.password = password;
	}
	
	
	
}
