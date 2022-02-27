package ocho;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import lombok.extern.slf4j.Slf4j;
import ocho.entity.User;
import ocho.service.UserService;

@SpringBootApplication
@Slf4j
public class OchoApplication {

	public static void main(String[] args) {
		SpringApplication.run(OchoApplication.class, args);
	}

	@Bean
	CommandLineRunner run(UserService userService, @Value("${app.email}") String email, @Value("${app.password}") String password) {
		return args -> {
			var res = userService.findByEmail(email);

			if(res.isPresent()){
				var current = res.get();
				log.info("found admin: {}", current);
				return;
			}

			var admin = new User();
			
			admin.setEmail(email);
			admin.setPassword(password);
			admin.setRole("ROLE_ADMIN");
			admin.setUrlAvatar("/admin.png");
			admin.setName("SuperUsuario Sistema");

			userService.save(admin);
			
			log.info("save admin: {}", admin);
		};
	}
}
