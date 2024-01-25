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
    private WebApplicationContext webApplicationContext;


    @Test
    public void testViewProfileDetail() throws Exception {
    	MockMvc mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

        // Mocking a user
        CustomUserDetail mockUser = mock(CustomUserDetail.class);
        when(mockUser.getEmail()).thenReturn("test@gmail.com");

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
    
//    @Mock
//    private UserService userService;

    
    @Mock
    private UserService loginService;

    @InjectMocks
    private UserController userController;

    @Test
    void testUpdateProfile() throws Exception {
        // Mock the service method calls
        RegistrationDetails received = new RegistrationDetails();
        received.setEmail("test@example.com");
        List<RegistrationDetails> profileDetails = Collections.singletonList(received);

        when(loginService.getProfileDetails("test@example.com")).thenReturn(profileDetails);

        // Set up MockMvc and perform the request
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        mockMvc.perform(post("/updateUserDetails")
                .flashAttr("received", received))
                .andExpect(status().isOk())
                .andExpect(view().name("userProfile"))
                .andExpect(model().attributeExists("userData", "userUpdate", "user"))
                .andExpect(model().attribute("userData", received))
                .andExpect(model().attribute("userUpdate", "Details updated successfully"))
                .andExpect(model().attribute("user", profileDetails));

        // Verify that the service method was called with the correct argument
        verify(loginService).updateProfile(received);
        verify(loginService).getProfileDetails("test@example.com");
    }
    


	public RegistrationDetails createRegistrationDetails(String firstname, String lastname, String email, String password, String bloodGroup) {
    	RegistrationDetails user = new RegistrationDetails();
    	user.setBloodGroup(bloodGroup);
    	user.setEmail(email);
    	user.setFirstname(firstname);
    	user.setLastname(lastname);
    	user.setPassword(password);
    	return user;
    }
	
	
    

    
	

//	private UserController userController;
    private MockMvc mockMvc;
    
    @Autowired
    RegistrationDetailsService registerService;

    @Test
    public void testUpdateProfile1() throws Exception {
        RegistrationDetails detail = new RegistrationDetails();
        detail.setBloodGroup("O+");
        detail.setFirstname("Rajasekhar");
        detail.setLastname("Bodeddula");
        detail.setEmail("test@gmail.com");
        List<RegistrationDetails> saved = registerService.getRegistrationDetailsByEmail(detail.getEmail());
        when(((OngoingStubbing<String>) registerService.getRegistrationDetailsByEmail(detail.getEmail())).thenReturn((RegistrationDetails)detail));
        mockMvc.perform(post("/user/updateUserDetails")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
