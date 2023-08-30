package com.foxminded.universityapp.controller;

import org.springframework.ui.Model;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.validation.support.BindingAwareModelMap;

@ExtendWith(SpringExtension.class)
@WebMvcTest(SimpleController.class)
class SimpleControllerTest {

    @InjectMocks
    SimpleController simpleController;

    @Test
    void welcomePageTest() {
        String viewName = simpleController.welcomePage();
        assertEquals("welcome", viewName);
    }

    @Test
    void loginTest() {
        String viewName = simpleController.login();
        assertEquals("login", viewName);
    }

    @Test
    void loginErrorTest() {
        Model model = new BindingAwareModelMap();
        String viewName = simpleController.loginError(model);

        assertEquals("error", viewName);
        assertTrue(model.containsAttribute("loginError"));
        assertTrue((boolean) model.getAttribute("loginError"));
    }

}