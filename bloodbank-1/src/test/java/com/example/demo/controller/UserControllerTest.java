package com.example.demo.controller;
import com.example.demo.configuration.CustomUserDetail;
import com.example.demo.service.UserService;

import jakarta.servlet.http.HttpSession;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;



import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private UserService userService;

    @Autowired
    private WebApplicationContext webApplicationContext;


    @Test
    public void testViewProfileDetail() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        // Mocking a user
        CustomUserDetail mockUser = mock(CustomUserDetail.class);
        when(mockUser.getEmail()).thenReturn("test@example.com");

        // Set the mock user in the session
        HttpSession session = mockMvc.perform(MockMvcRequestBuilders.get("/user/viewProfileDetail")
                .sessionAttr("user", mockUser)) // Set the user directly in the session attributes
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getRequest().getSession();

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/user/viewProfileDetail").session((MockHttpSession) session))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        // Add assertions as needed
        assertEquals("userProfile", result.getModelAndView().getViewName());
    }

    // Add more tests for other controller methods as needed
}
