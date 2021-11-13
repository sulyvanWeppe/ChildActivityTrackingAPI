package com.sulwep7.childactivitytracking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan
public class ChildActivityTrackingApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChildActivityTrackingApplication.class, args);
	}


}
