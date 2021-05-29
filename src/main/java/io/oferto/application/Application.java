package io.oferto.application;

import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		// list all environment variables
		/*Map<String, String> env = System.getenv();
		for (String envName : env.keySet()) {
		    System.out.format("%s=%s%n", envName, env.get(envName));
		}*/
		
		SpringApplication.run(Application.class, args);
	}
}
