package br.com.alura.Literalura;

import br.com.alura.Literalura.Principal.Principal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner {

	private final Principal principal;

	public LiteraluraApplication(Principal principal) {
		this.principal = principal;

	}

	public static void main(String[] args) {
		SpringApplication.run(LiteraluraApplication.class, args);
	}

	@Override
	public void run(String... args) {
			principal.exibeMenu();
	}
}
