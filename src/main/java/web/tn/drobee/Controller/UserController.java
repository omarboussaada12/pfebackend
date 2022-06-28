package web.tn.drobee.Controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import lombok.Data;
import web.tn.drobee.entity.Role;
import web.tn.drobee.entity.User;
import web.tn.drobee.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
 UserService userservice;
@GetMapping("/all") 
@ResponseBody 
public ResponseEntity<List<User>> getUser() { 
return ResponseEntity.ok().body(userservice.getUsers());	
} 
@PostMapping("/add") 
@ResponseBody 
public ResponseEntity<User> saveUser(@RequestBody User user) { 
URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/user/add").toUriString());	
return ResponseEntity.created(uri).body(userservice.saveUser(user));	
} 
@PostMapping("role/add") 
@ResponseBody 
public ResponseEntity<Role> saveRole(@RequestBody Role role) { 
	URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/user/role/add").toUriString());	
return ResponseEntity.created(uri).body(userservice.saveRole(role));	
} 
@PostMapping("role/addtouser") 
@ResponseBody 
public ResponseEntity<Role> saveRoleToUser(@RequestBody RoleToUser form) { 
userservice.addRoleToUser(form.getUsername(), form.getRolename());
return ResponseEntity.ok().build();
} 

}
@Data
class RoleToUser{
	private String username;
	private String Rolename;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getRolename() {
		return Rolename;
	}
	public void setRolename(String rolename) {
		Rolename = rolename;
	}
	
}

