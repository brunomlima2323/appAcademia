package br.com.bml.appAcademia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AppAcademiaApplication {

	public static void main(String[] args) {
		System.out.println("=================================");
		System.out.println("Iniciando aplicação");
		System.out.println("DATABASE_URL: jdbc:postgresql://" +  System.getenv("PGHOST") + ":" +  System.getenv("PGPORT") + "/" + System.getenv("PGDATABASE"));
		System.out.println("PGHOST: " + System.getenv("PGHOST"));
		System.out.println("PGPORT: " + System.getenv("PGPORT"));
		System.out.println("PGDATABASE: " + System.getenv("PGDATABASE"));
		System.out.println("PGUSER: " + System.getenv("PGUSER"));
		System.out.println("PGPASSWORD: " + System.getenv("PGPASSWORD"));
		System.out.println("=================================");
		
		SpringApplication.run(AppAcademiaApplication.class, args);
	}

}
