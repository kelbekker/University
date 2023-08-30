package com.foxminded.universityapp.config;

import com.foxminded.universityapp.model.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.modelmapper.ModelMapper;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class ApplicationConfig {

    public ApplicationConfig() {
        super();
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails teacher = User.withUsername("Isabella")
                .password(passwordEncoder().encode("teacherPass"))
                .roles(Role.TEACHER.name())
                .build();
        UserDetails student = User.withUsername("Alice")
                .password(passwordEncoder().encode("studentPass"))
                .roles(Role.STUDENT.name())
                .build();
        UserDetails admin = User.withUsername("admin")
                .password(passwordEncoder().encode("adminPass"))
                .roles(Role.ADMIN.name())
                .build();
        UserDetails anonymous = User.withUsername("anonymous")
                .password(passwordEncoder().encode("password"))
                .build();
        return new InMemoryUserDetailsManager(teacher, student, admin, anonymous);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize ->
                        authorize
                                .requestMatchers("/user-management/teachers/**").hasAnyRole(Role.TEACHER.name(), Role.ADMIN.name())
                                .requestMatchers("/user-management/students/**").hasAnyRole(Role.STUDENT.name(), Role.ADMIN.name())
                                .requestMatchers("/user-management/admin/**").hasRole(Role.ADMIN.name())
                                .requestMatchers("/user-management/courses/all-courses/**").hasAnyRole(Role.
                                        ADMIN.name(), Role.TEACHER.name(), Role.STUDENT.name())
                                .anyRequest().authenticated()
                )
                .formLogin(formLogin ->
                        formLogin
                                .defaultSuccessUrl("/welcome", true)
                );

        return http.build();
    }

}