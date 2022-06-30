package web.tn.drobee;

import java.util.ArrayList;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import web.tn.drobee.entity.Role;
import web.tn.drobee.entity.User;
import web.tn.drobee.service.UserService;

@SpringBootApplication
public class DrobeeApplication {

	public static void main(String[] args) {
		SpringApplication.run(DrobeeApplication.class, args);
	}
	@Bean
	PasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder();
	}
	@Bean
	CommandLineRunner run(UserService userService){
	    return args->{
	        userService.saveRole(new Role(null,"ROLE_USER"));
	        userService.saveRole(new Role(null,"ROLE_MANAGER"));
	        userService.saveRole(new Role(null,"ROLE_ADMIN"));
	        userService.saveRole(new Role(null,"ROLE_SUPER_ADMIN"));
	        userService.saveUser(new  User(null,"John Travolta","john","1234",new ArrayList<>()));
	        userService.saveUser(new  User(null,"omar boussaada","omar","1234",new ArrayList<>()));
	        userService.addRoleToUser("omar", "ROLE_ADMIN");
	    };
	}

}
