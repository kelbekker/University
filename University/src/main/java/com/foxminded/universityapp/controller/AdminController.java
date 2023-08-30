package com.foxminded.universityapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.Arrays;
import java.util.List;

@Controller
@PreAuthorize("hasRole(Role.ADMIN.name())")
public class AdminController {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserDetailsManager userDetailsManager;

    @GetMapping("user-management/admin")
    public String adminPage(Model model) {
        List<String> users = Arrays.asList("Student", "Teacher");
        model.addAttribute("users", users);
        return "adminPage";
    }

    public String addNewRoleToUser(@RequestParam("username") String userName, @RequestParam("roleName") String roleName) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(userName);

        UserDetails updatedUserDetails = User.builder()
                .username(userDetails.getUsername())
                .password(userDetails.getPassword())
                .authorities(new SimpleGrantedAuthority(roleName))
                .build();

        userDetailsManager.updateUser(updatedUserDetails);
        return "adminPage";
    }
}