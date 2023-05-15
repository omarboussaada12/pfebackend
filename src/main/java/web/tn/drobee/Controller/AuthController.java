package web.tn.drobee.Controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import web.tn.drobee.entity.ERole;
import web.tn.drobee.entity.Role;
import web.tn.drobee.entity.User;
import web.tn.drobee.payload.request.LoginRequest;
import web.tn.drobee.payload.request.SignupRequest;
import web.tn.drobee.payload.response.JwtResponse;
import web.tn.drobee.payload.response.MessageResponse;
import web.tn.drobee.repo.FileDBRepository;
import web.tn.drobee.repo.RoleRepository;
import web.tn.drobee.repo.UserRepository;
import web.tn.drobee.jwt.JwtUtils;
import web.tn.drobee.service.FileStorageService;
import web.tn.drobee.service.UserDetailsImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	FileDBRepository filedbrepo;
	
	@Autowired
	FileStorageService fsService;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;
	
	String exceptionrole ="Role is not found";
	@Value("${app.defaultpicturename}")
	private String defaultPictureName;
	
	
	private static final Logger l = LogManager.getLogger(AuthController.class);
	

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return ResponseEntity.ok(
				new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles));
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		if (Boolean.TRUE.equals(userRepository.existsByUsername(signUpRequest.getUsername()))) {
			l.info("Error: Username is already taken!");
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
		}

		if (Boolean.TRUE.equals(userRepository.existsByEmail(signUpRequest.getEmail()))) {
			l.info("Error: Email is already in use!");
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
			
		}

		// Create new user's account
		User user = new User(signUpRequest.getUsername(), signUpRequest.getFirstname(), signUpRequest.getLastname() ,signUpRequest.getPhone(), signUpRequest.getAddress(), signUpRequest.getEmail(),
				encoder.encode(signUpRequest.getPassword()));
		Set<Role> roles = new HashSet<>();
		String role = signUpRequest.getRole();
		switch (role) {
		case "admin":
			Role roleadmin = roleRepository.findByName(ERole.ROLE_ADMIN)
					.orElseThrow(() -> new RuntimeException(exceptionrole));
			roles.add(roleadmin);
			break;
		case "client":
			Role roleclient = roleRepository.findByName(ERole.ROLE_CLIENT)
					.orElseThrow(() -> new RuntimeException(exceptionrole));
			roles.add(roleclient);

			break;

		default:
			Role roleuser = roleRepository.findByName(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException(exceptionrole));
			roles.add(roleuser);
			break;
		}
		user.setRoles(roles);
		user.setImage(fsService.getFile(filedbrepo.getidofimagepardefaut(defaultPictureName)));
		userRepository.save(user);

		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}
}
