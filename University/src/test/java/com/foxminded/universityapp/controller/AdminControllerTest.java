package com.foxminded.universityapp.controller;

import com.foxminded.universityapp.service.CourseService;
import com.foxminded.universityapp.service.StudentService;
import com.foxminded.universityapp.service.TeacherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;
import org.junit.jupiter.api.Test;
import org.springframework.validation.support.BindingAwareModelMap;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AdminController.class)
public class AdminControllerTest {

    @MockBean
    private CourseService courseService;

    @MockBean
    private TeacherService teacherService;

    @MockBean
    private StudentService studentService;

    @MockBean
    private ModelMapper modelMapper;

    @Mock
    private Model model;

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private AdminController adminController;

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private UserDetailsManager userDetailsManager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void adminPageWhenModelShouldReturnAdminPage() {
        Model model = new BindingAwareModelMap();

        String viewName = adminController.adminPage(model);

        assertAll(
                () -> assertEquals("adminPage", viewName),
                () -> assertTrue(model.containsAttribute("users")),
                () -> {
                    List<String> users = (List<String>) model.getAttribute("users");
                    assertNotNull(users);
                    assertEquals(Arrays.asList("Student", "Teacher"), users);
                }
        );
    }

    @Test
    void addNewRoleToUserWhenNormalUserNameAndRoleNameShouldReturnAdminPage() {
        String userName = "testUser";
        String roleName = "ROLE_ADMIN";

        UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                .username(userName)
                .password("password")
                .authorities(new SimpleGrantedAuthority("ROLE_USER"))
                .build();

        when(userDetailsService.loadUserByUsername(userName)).thenReturn(userDetails);

        adminController.addNewRoleToUser(userName, roleName);

        verify(userDetailsManager).updateUser(argThat(updatedUserDetails ->
                updatedUserDetails.getUsername().equals(userName) &&
                        updatedUserDetails.getPassword().equals("password") &&
                        updatedUserDetails.getAuthorities().contains(new SimpleGrantedAuthority(roleName))
        ));
    }
}
