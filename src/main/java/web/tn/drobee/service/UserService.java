package web.tn.drobee.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import web.tn.drobee.entity.ERole;
import web.tn.drobee.entity.Role;
import web.tn.drobee.entity.User;
import web.tn.drobee.payload.request.SignupRequest;
import web.tn.drobee.repo.RoleRepository;
import web.tn.drobee.repo.UserRepository;

@Service
public class UserService implements IUserService{
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	PasswordEncoder encoder;
	
	private static final Logger l = LogManager.getLogger(UserService.class);
	@Override
	public List<User> Listusers() {

		List<User> Users = (List<User>) userRepository.findAll();
		l.info("fetching list of users");
		for (User ac : Users) {
			l.info("username  : " + ac.getUsername());
		}
		return Users;
	}

	@Override
	public User Adduser(SignupRequest signUpRequest) {
		User user = new User(signUpRequest.getUsername(), signUpRequest.getFirstname(), signUpRequest.getLastname() ,signUpRequest.getPhone(), signUpRequest.getAddress(), signUpRequest.getEmail(),
				encoder.encode(signUpRequest.getPassword()));
		Set<Role> roles = new HashSet<>();
		String role = signUpRequest.getRole();
		switch (role) {
		case "admin":   Role Roleadmin = roleRepository.findByName(ERole.ROLE_ADMIN)
				.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
				roles.add(Roleadmin);
			break;
	    case "client":  Role Roleclient = roleRepository.findByName(ERole.ROLE_CLIENT)
	    		.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
	    		roles.add(Roleclient);  
			
			break;

		default: Role Roleuser = roleRepository.findByName(ERole.ROLE_USER)
	    		.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
	    		roles.add(Roleuser);
			break;
		}
		user.setRoles(roles);
		l.info("Adding user with ID: " + user.getId());
		return userRepository.save(user);
		
	}

	@Override
	public void Deleteuser(Long id) {
		l.info("Deleting user with ID: " + id);
		userRepository.deleteById(id);
		
	}
	//update still not finished
	@Override
	public User Updateuser(User a, Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User getuserbyusername(String username) {
		return userRepository.findByUsername(username).orElseThrow(
				() -> new UsernameNotFoundException("User Not Found with username: " + username));
	}

	@Override
	public User addroletouser(String username, String rolename) {
		User u = getuserbyusername(username);
		u.setRoles( roleRepository.findByName(ERole.ROLE_CLIENT). orElseThrow(() -> new RuntimeException("Error: Role is not found.")));
		
		return userRepository.save(u);
	}
}
