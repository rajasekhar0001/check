package com.example.demo.controller;
import com.example.demo.configuration.CustomUserDetail;
import com.example.demo.entity.DonorDetails;
import com.example.demo.entity.PatientDetails;
import com.example.demo.entity.RegistrationDetails;
import com.example.demo.service.RegistrationDetailsService;
import com.example.demo.service.UserService;

import jakarta.servlet.http.HttpSession;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.AbstractMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder;
import org.springframework.web.context.WebApplicationContext;



import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private UserService userService;
    
    @Autowired
    private UserController userController;



    @Test
    public void testViewProfileDetail() throws Exception {
    	MockMvc mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

        // Mocking a user
        CustomUserDetail mockUser = mock(CustomUserDetail.class);
        when(mockUser.getEmail()).thenReturn("rajasekhar5486@@gmail.com");

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
    

}
