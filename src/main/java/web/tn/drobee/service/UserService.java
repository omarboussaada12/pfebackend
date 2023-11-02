package web.tn.drobee.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import web.tn.drobee.entity.Commande;
import web.tn.drobee.entity.ERole;
import web.tn.drobee.entity.FileDB;
import web.tn.drobee.entity.Role;
import web.tn.drobee.entity.User;
import web.tn.drobee.payload.request.SignupRequest;
import web.tn.drobee.payload.response.CommandeReponse;
import web.tn.drobee.payload.response.MessageResponse;
import web.tn.drobee.payload.response.ReclamationResponse;
import web.tn.drobee.payload.response.ResponseFile;
import web.tn.drobee.payload.response.UserResponse;
import web.tn.drobee.repo.RoleRepository;
import web.tn.drobee.repo.UserRepository;

@Service
public class UserService implements IUserService {
	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	FileStorageService storageService;

	@Autowired
	PasswordEncoder encoder;
	
	@Autowired
	CommandeService commandeService ;
	@Autowired
	ReclamationService reclamationService;

	private static final Logger l = LogManager.getLogger(UserService.class);

	// list user back-end
	@Override
	public List<UserResponse> Listusers() {

		List<UserResponse> lur = new ArrayList<UserResponse>();
		List<User> Users = userRepository.findAll();
		l.info("fetching list of users");
		for (User ac : Users) {
			UserResponse ur = new UserResponse();
			l.info("username image  : " + ac.getImage().getName());
			FileDB dbFile = storageService.getFile(ac.getImage().getId());
			String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/files/")
					.path(dbFile.getId()).toUriString();
			ur.setImage(new ResponseFile(dbFile.getName(), fileDownloadUri, dbFile.getType(), dbFile.getData().length));
			ur.setId(ac.getId());
			ur.setUsername(ac.getUsername());
			ur.setFirstname(ac.getFirstname());
			ur.setLastname(ac.getLastname());
			ur.setPhone(ac.getPhone());
			ur.setEmail(ac.getEmail());
			ur.setAddress(ac.getAddress());
			ur.setRoles(ac.getRoles());
			lur.add(ur);
		}
		return lur;
	}

	// delete all user order before deleting a user
	@Override
	public void Deleteuser(String  username) {
		l.info("Deleting user with username: " + username);
		List<CommandeReponse> cr = new ArrayList<CommandeReponse>();
		cr = this.commandeService.Listcommandesbyuser(username);
		l.info("Deleting all commande for username =  " + username);
		for (CommandeReponse ac : cr) {
			commandeService.Deletecommande(ac.getId());
		}
		List<ReclamationResponse> list = reclamationService.Listreclamationsbyuser(username);
		for (ReclamationResponse ac : list) {
			reclamationService.Deletereclamation(ac.getId());
		}
		userRepository.deleteById(getuserbyusername(username).getId());

	}

	// update info for user
	@Override
	public ResponseEntity<MessageResponse> Updateuserinfo(String username, SignupRequest a) {
		User u = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username :" + username));
		if (userRepository.existsByEmail(a.getEmail())&&( u.getEmail().toUpperCase().equals(a.getEmail().toUpperCase())))
		{
			u.setEmail(a.getEmail());
		}else
		{
			 return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use! "));
		}
		u.setFirstname(a.getFirstname());
		u.setLastname(a.getLastname());
		u.setAddress(a.getAddress());
		u.setPhone(a.getPhone());
		 if (!u.getRoles().stream().map(Role::getName).anyMatch(roleName -> roleName.equals(a.getRole()))) {
		        changeroletouser(username, a.getRole());
		    }
		if (userRepository.save(u) != null) {
			return ResponseEntity.ok(new MessageResponse("account information updated successfully!"));
		} else {
			 return ResponseEntity.badRequest().body(new MessageResponse("Error: something went wrong "));
		}
	}

	// update image for user
	@Override
	public User Updateuserimage(String username, MultipartFile file) {
		User u = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

		try {
			u.setImage(storageService.store(file));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return userRepository.save(u);
	}

	// get a single user by his username
	@Override
	public UserResponse getuserbyusername(String username) {
		User u = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
		UserResponse ur = new UserResponse();
		l.info("username image  : " + u.getImage().getName());
		FileDB dbFile = storageService.getFile(u.getImage().getId());
		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/files/")
				.path(dbFile.getId()).toUriString();
		ur.setImage(new ResponseFile(dbFile.getName(), fileDownloadUri, dbFile.getType(), dbFile.getData().length));
		ur.setId(u.getId());
		ur.setUsername(u.getUsername());
		ur.setFirstname(u.getFirstname());
		ur.setLastname(u.getLastname());
		ur.setPhone(u.getPhone());
		ur.setEmail(u.getEmail());
		ur.setAddress(u.getAddress());
		ur.setRoles(u.getRoles());
		return ur;
	}

	// change user role
	@Override
	public User changeroletouser(String username, String rolename) {
		User u = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
		;
		l.info("changing role for "+ username +" from "+u.getRoles()+" to : " + rolename);
		switch (rolename) {
		case "ROLE_ADMIN":
			u.setRoles(roleRepository.findByName(ERole.ROLE_ADMIN)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found.")));
			break;
		case "ROLE_CLIENT":
			u.setRoles(roleRepository.findByName(ERole.ROLE_CLIENT)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found.")));

			break;

		default:
			u.setRoles(roleRepository.findByName(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found.")));
			break;
		}
		return userRepository.save(u);
	}

	// list admin for fontend page home
	@Override
	public List<UserResponse> ListusersAdmin() {
		List<UserResponse> lur = new ArrayList<UserResponse>();
		List<User> Users = userRepository.findAllAdmin();
		for (User ac : Users) {
			UserResponse ur = new UserResponse();
			l.info("username image  : " + ac.getImage().getName());
			FileDB dbFile = storageService.getFile(ac.getImage().getId());
			String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/files/")
					.path(dbFile.getId()).toUriString();
			ur.setImage(new ResponseFile(dbFile.getName(), fileDownloadUri, dbFile.getType(), dbFile.getData().length));
			ur.setId(ac.getId());
			ur.setUsername(ac.getUsername());
			ur.setFirstname(ac.getFirstname());
			ur.setLastname(ac.getLastname());
			ur.setPhone(ac.getPhone());
			ur.setEmail(ac.getEmail());
			ur.setAddress(ac.getAddress());
			ur.setRoles(ac.getRoles());
			lur.add(ur);
		}
		return lur;
	}
}
