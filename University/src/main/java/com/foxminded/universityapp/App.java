package com.foxminded.universityapp;

import com.foxminded.universityapp.controller.TeacherController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication(scanBasePackages={"com.foxminded.universityapp"})
public class App {

	public static void main(String[] args) {
	SpringApplication.run(App.class, args);
	}
}

