package web.tn.drobee;

import java.util.ArrayList;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import web.tn.drobee.DrobeeApplication;


@SpringBootApplication
public class DrobeeApplication {

	public static void main(String[] args) {
		SpringApplication.run(DrobeeApplication.class, args);
	}


}
