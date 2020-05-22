package com.doComplaint;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("com.doComplaint")
public class DoComplaintApplication {

	public static void main(String[] args) {
		SpringApplication.run(DoComplaintApplication.class, args);
	}

}
