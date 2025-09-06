package com.projectsugarglider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;


//TODO : Bootstrap 라이센스 표시
@EnableScheduling
@EnableCaching
@SpringBootApplication
public class ProjectsugargliderApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectsugargliderApplication.class, args);
	}

}
