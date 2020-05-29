package com.omar.time;

import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

@SpringBootApplication
@EntityScan(basePackageClasses = { 
    TimeManagementApplication.class,
    Jsr310JpaConverters.class //for the Java 8 date time classes to be automatically converted to SQL date time format
})
public class TimeManagementApplication {

	//setting the default time zone for our application to UTC
    @PostConstruct
	void init() {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
	}

	public static void main(String[] args) {
		SpringApplication.run(TimeManagementApplication.class, args);
	}

}
