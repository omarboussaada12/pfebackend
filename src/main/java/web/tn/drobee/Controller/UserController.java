package web.tn.drobee.Controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	@GetMapping("/get-all-users")
	@ResponseBody
	public List<UserResponse> getusers() {
		List<UserResponse> list = userService.Listusers();
		return list;
	}
	@GetMapping("/get-user/{username}")
	@ResponseBody
	public UserResponse getuser(@PathVariable("username") String username) {
		return userService.getuserbyusername(username);
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
	@PutMapping("/update-user-image/{user-name}")
	@ResponseBody
	public void updateUserimage(@Valid @PathVariable("user-name") String username, @RequestParam("file") MultipartFile file ) {
		
		
	userService.Updateuserimage(username,file);
	}
	
	@PutMapping("/update-user-info/{user-name}")
	@ResponseBody
	public ResponseEntity<MessageResponse> updateUserinfo(@Valid @PathVariable("user-name") String username, @RequestBody SignupRequest user ) {
		
	 if( userService.Updateuserinfo(username,user))
	 {
		 return ResponseEntity.ok().body(new MessageResponse(" ok : user info updated  "));
	 }else
	 {
		 return ResponseEntity.ok().body(new MessageResponse(" Error :  could not update this user info")); 
	 }
	}
	
	@GetMapping("/get-user-admin")
	@ResponseBody
	public List<UserResponse> getalladmin() {
		List<UserResponse> list =userService.ListusersAdmin();
		return list;
	}
}
