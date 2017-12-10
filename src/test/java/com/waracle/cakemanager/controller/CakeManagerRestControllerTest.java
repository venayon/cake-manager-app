package com.waracle.cakemanager.controller;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CakeManagerRestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturn_OK() throws Exception {
        this.mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void cakeHomePageTest() throws Exception {
        mockMvc.perform(get("/")).andExpect(status().isOk())
                .andExpect(view().name("cakeManager"))
                .andExpect(model().attributeExists("cakes"))
                .andExpect(model().attribute("cakes", Matchers.is(Matchers.notNullValue())));
    }

    @Test
    public void addCakeTest() throws Exception {
        mockMvc.perform(post("/").contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("title", "My Cake")
                .param("description", "My Cake added")
                .param("image", "image1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/"));

        mockMvc.perform(get("/")).andExpect(status().isOk())
                .andExpect(view().name("cakeManager"))
                .andExpect(model().attributeExists("cakes"))
                .andExpect(model().attribute("cakes", hasItem(
                        allOf(
                                hasProperty("title", is("My Cake")),
                                hasProperty("description", is("My Cake added")),
                                hasProperty("image", is("image1"))
                        )
                )));


    }
}