package edu.ucsb.cs156.example.controllers;

import edu.ucsb.cs156.example.repositories.UserRepository;
import edu.ucsb.cs156.example.testconfig.TestConfig;
import edu.ucsb.cs156.example.ControllerTestCase;
import edu.ucsb.cs156.example.entities.UCSBDate;
import edu.ucsb.cs156.example.entities.UCSBDiningCommonsMenuItems;
import edu.ucsb.cs156.example.repositories.UCSBDiningCommonsMenuItemsRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import java.time.LocalDateTime;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@WebMvcTest(controllers = UCSBDiningCommonsMenuItemController.class)
@Import(TestConfig.class)
public class UCSBDiningCommonsMenuItemControllerTests extends ControllerTestCase {

    @MockBean
    UCSBDiningCommonsMenuItemsRepository ucsbDiningCommonsMenuItemsRepository;

    @MockBean
    UserRepository userRepository;

        
    @Test
    public void logged_out_users_cannot_get_all() throws Exception {
            mockMvc.perform(get("/api/ucsbDiningCommonsMenuItems/all"))
                            .andExpect(status().is(403)); // logged out users can't get all
    }

    @WithMockUser(roles = { "USER" })
    @Test
    public void logged_in_users_can_get_all() throws Exception {
            mockMvc.perform(get("/api/ucsbDiningCommonsMenuItems/all"))
                            .andExpect(status().is(200)); // logged
    }

    @WithMockUser(roles = { "USER" })
    @Test
    public void logged_in_user_can_get_all_ucsbdiningcommonsmenuitems() throws Exception {

            // arrange

            UCSBDiningCommonsMenuItems ucsbDiningCommonsMenuItems1 = UCSBDiningCommonsMenuItems.builder()
                            .diningCommonsCode("diningCommonsCode")
                            .name("name")
                            .station("station")
                            .build();


            UCSBDiningCommonsMenuItems ucsbDiningCommonsMenuItems2 = UCSBDiningCommonsMenuItems.builder()
                            .diningCommonsCode("diningCommonsCode")
                            .name("name")
                            .station("station")
                            .build();


            ArrayList<UCSBDiningCommonsMenuItems> expectedDates = new ArrayList<>();
            expectedDates.addAll(Arrays.asList(ucsbDiningCommonsMenuItems1, ucsbDiningCommonsMenuItems2));

            when(ucsbDiningCommonsMenuItemsRepository.findAll()).thenReturn(expectedDates);

            // act
            MvcResult response = mockMvc.perform(get("/api/ucsbDiningCommonsMenuItems/all"))
                            .andExpect(status().isOk()).andReturn();

            // assert

            verify(ucsbDiningCommonsMenuItemsRepository, times(1)).findAll();
            String expectedJson = mapper.writeValueAsString(expectedDates);
            String responseString = response.getResponse().getContentAsString();
            assertEquals(expectedJson, responseString);
    }




    @Test
    public void logged_out_users_cannot_post() throws Exception {
            mockMvc.perform(post("/api/ucsbDiningCommonsMenuItems/post"))
                            .andExpect(status().is(403));
    }

    @WithMockUser(roles = { "USER" })
    @Test
    public void logged_in_regular_users_cannot_post() throws Exception {
            mockMvc.perform(post("/api/ucsbDiningCommonsMenuItems/post"))
                            .andExpect(status().is(403)); // only admins can post
    }

    @WithMockUser(roles = { "ADMIN" })
    @Test
    public void an_admin_user_can_post_a_new_ucsbmenuitem() throws Exception {
            // arrange


            UCSBDiningCommonsMenuItems ucsbDiningCommonsMenuItems1 = UCSBDiningCommonsMenuItems.builder()
                    .diningCommonsCode("diningCommonsCode")
                    .name("name")
                    .station("station")
                    .build();


            when(ucsbDiningCommonsMenuItemsRepository.save(eq(ucsbDiningCommonsMenuItems1))).thenReturn(ucsbDiningCommonsMenuItems1);

            // act
            MvcResult response = mockMvc.perform(
                            post("/api/ucsbDiningCommonsMenuItems/post?id=0&diningCommonsCode=diningCommonsCode&name=name&station=station")
                                            .with(csrf()))
                            .andExpect(status().isOk()).andReturn();

            // assert
            verify(ucsbDiningCommonsMenuItemsRepository, times(1)).save(ucsbDiningCommonsMenuItems1);
            String expectedJson = mapper.writeValueAsString(ucsbDiningCommonsMenuItems1);
            String responseString = response.getResponse().getContentAsString();
            assertEquals(expectedJson, responseString);
    }

// Tests for GET /api/ucsbdates?id=...

        @Test
        public void logged_out_users_cannot_get_by_id() throws Exception {
                mockMvc.perform(get("/api/ucsbDiningCommonsMenuItems?id=123"))
                                .andExpect(status().is(403)); // logged out users can't get by id
        }

        @WithMockUser(roles = { "USER" })
        @Test
        public void test_that_logged_in_user_can_get_by_id_when_the_id_exists() throws Exception {

                // arrange

                UCSBDiningCommonsMenuItems ucsbDining = UCSBDiningCommonsMenuItems.builder()
                        .diningCommonsCode("diningCommonsCode")
                        .name("name")
                        .station("station")
                        .build();

                when(ucsbDiningCommonsMenuItemsRepository.findById(eq(123L))).thenReturn(Optional.of(ucsbDining));

                // act
                MvcResult response = mockMvc.perform(get("/api/ucsbDiningCommonsMenuItems?id=123"))
                                .andExpect(status().isOk()).andReturn();

                // assert

                verify(ucsbDiningCommonsMenuItemsRepository, times(1)).findById(eq(123L));
                String expectedJson = mapper.writeValueAsString(ucsbDining);
                String responseString = response.getResponse().getContentAsString();
                assertEquals(expectedJson, responseString);
        }

        @WithMockUser(roles = { "USER" })
        @Test
        public void test_that_logged_in_user_can_get_by_id_when_the_id_does_not_exist() throws Exception {

                // arrange

                when(ucsbDiningCommonsMenuItemsRepository.findById(eq(123L))).thenReturn(Optional.empty());

                // act
                MvcResult response = mockMvc.perform(get("/api/ucsbDiningCommonsMenuItems?id=123"))
                                .andExpect(status().isNotFound()).andReturn();

                // assert

                verify(ucsbDiningCommonsMenuItemsRepository, times(1)).findById(eq(123L));
                Map<String, Object> json = responseToJson(response);
                assertEquals("EntityNotFoundException", json.get("type"));
                assertEquals("UCSBDiningCommonsMenuItems with id 123 not found", json.get("message"));
        }
}
