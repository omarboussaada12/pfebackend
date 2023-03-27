package web.tn.drobee.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import web.tn.drobee.entity.ERole;
import web.tn.drobee.entity.FileDB;
import web.tn.drobee.entity.User;
import web.tn.drobee.payload.request.SignupRequest;
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

	// delete still not finished
	@Override
	public void Deleteuser(Long id) {
		l.info("Deleting user with ID: " + id);
		userRepository.deleteById(id);

	}

	// update info for user
	@Override
	public boolean Updateuserinfo(String username, SignupRequest a) {
		User u = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
		if (u.getEmail().equals(a.getEmail())) {
			u.setEmail(a.getEmail());
		} else if (userRepository.existsByEmail(a.getEmail())) {
			return false;
		}
		u.setFirstname(a.getFirstname());
		u.setLastname(a.getLastname());
		u.setAddress(a.getAddress());
		u.setPhone(a.getPhone());
		addroletouser(username, a.getRole());
		if (userRepository.save(u) != null) {
			return true;
		} else {
			return false;
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
	public User addroletouser(String username, String rolename) {
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
