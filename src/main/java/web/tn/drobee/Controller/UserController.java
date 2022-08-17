package web.tn.drobee.Controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import web.tn.drobee.entity.User;
import web.tn.drobee.payload.request.SignupRequest;
import web.tn.drobee.payload.response.MessageResponse;
import web.tn.drobee.repo.UserRepository;
import web.tn.drobee.service.IUserService;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class UserController {
	
	@Autowired
	IUserService userService;
	@Autowired
	UserRepository userRepository;
	
	@GetMapping("/get-all-users")
	@ResponseBody
	public List<User> getusers() {
		List<User> list = userService.Listusers();
		return list;
	}
	@GetMapping("/get-user/{username}")
	@ResponseBody
	public User getuser(@PathVariable("username") String username) {
		return userService.getuserbyusername(username);
	}
	@PostMapping("/add-user")
	@ResponseBody
	public ResponseEntity<?> adduser(@Valid @RequestBody SignupRequest user) {
		if (userRepository.existsByUsername(user.getUsername())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error:  username is already taken!"));
		}
		userService.Adduser(user);
		return ResponseEntity.ok(new MessageResponse("user add successfully!"));
	}

	@DeleteMapping("/delete-user/{user-id}")
	@ResponseBody
	public void deleteUser(@PathVariable("user-id") Long UserId) {
		userService.Deleteuser(UserId);
	}
	@PutMapping("/update-user")
	@ResponseBody
	public ResponseEntity<?> updateUser(@Valid @RequestBody User user) {
		
		return ResponseEntity.ok(new MessageResponse("user updated successfully!"));
	}
	@PutMapping("/update-user-role/{user-name}/{role-name}")
	@ResponseBody
	public User updateUserroles(@Valid @PathVariable("user-name") String username, @PathVariable("role-name") String rolename   ) {
		
		
		return userService.addroletouser(username, rolename);
	}
}
