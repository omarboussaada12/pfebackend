package web.tn.drobee.Controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import web.tn.drobee.entity.User;
import web.tn.drobee.payload.request.SignupRequest;
import web.tn.drobee.payload.response.MessageResponse;
import web.tn.drobee.payload.response.UserResponse;
import web.tn.drobee.repo.UserRepository;
import web.tn.drobee.service.IUserService;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class UserController {
	
	@Autowired
	IUserService userService;
	@Autowired
	UserRepository userRepository;
	
	
	// list of all the users
	@GetMapping("/get-all-users")
	@ResponseBody
	public List<UserResponse> getusers() {
		List<UserResponse> list = userService.Listusers();
		return list;
	}
	
	//fetching a user based on his username
	@GetMapping("/get-user/{username}")
	@ResponseBody
	public UserResponse getuser(@PathVariable("username") String username) {
		return userService.getuserbyusername(username);
	}
    
	// need more work
	@DeleteMapping("/delete-user/{username}")
	@ResponseBody
	public void deleteUser(@Valid @PathVariable("username") String username) {
		userService.Deleteuser(username);
	}
	
	
	//update user role 
	@PutMapping("/update-user-role/{user-name}/{role-name}")
	@ResponseBody
	public void updateUserroles(@Valid @PathVariable("user-name") String username, @PathVariable("role-name") String rolename   ) {
		
		
		 userService.changeroletouser(username, rolename);
	}
	
	// update user image
	@PutMapping("/update-user-image/{user-name}")
	@ResponseBody
	public void updateUserimage(@Valid @PathVariable("user-name") String username, @RequestParam("file") MultipartFile file ) {
		
		
	userService.Updateuserimage(username,file);
	}
	
	@PutMapping("/update-user-info/{user-name}")
	@ResponseBody
	public ResponseEntity<?> updateUserinfo(@Valid @PathVariable("user-name") String username, @RequestBody SignupRequest user ) {
	 return userService.Updateuserinfo(username,user);
	}
	
	// list off all the users with ROLE_ADMIN
	@GetMapping("/get-user-admin")
	@ResponseBody
	public List<UserResponse> getalladmin() {
		List<UserResponse> list =userService.ListusersAdmin();
		return list;
	}
}
