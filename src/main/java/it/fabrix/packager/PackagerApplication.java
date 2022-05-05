package it.fabrix.packager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "it.fabrix.packager")
public class PackagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(PackagerApplication.class, args);
	}

}
