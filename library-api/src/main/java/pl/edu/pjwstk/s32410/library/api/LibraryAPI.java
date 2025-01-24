package pl.edu.pjwstk.s32410.library.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
@EnableCaching
@EntityScan(basePackages = {"pl.edu.pjwstk.s32410.library.shared.model"})
public class LibraryAPI extends SpringBootServletInitializer {
	
	@PostConstruct
	public void init() {
		
	}
	
	public static void main(String[] args) {
		SpringApplication.run(LibraryAPI.class, args);
	}
}
