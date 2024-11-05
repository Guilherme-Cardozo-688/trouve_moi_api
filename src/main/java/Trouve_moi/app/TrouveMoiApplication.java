package Trouve_moi.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "Trouve_moi.app.cadastro.User.domain")
@EnableJpaRepositories(basePackages = "Trouve_moi.app.cadastro.User.repository")
public class TrouveMoiApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrouveMoiApplication.class, args);
	}

}
