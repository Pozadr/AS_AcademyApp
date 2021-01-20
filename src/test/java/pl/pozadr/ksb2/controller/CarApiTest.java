package pl.pozadr.ksb2.controller;

import org.hamcrest.core.Is;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.pozadr.ksb2.model.Color;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class CarApiTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Should list all Cars when making GET request to endpoint - /cars")
    void shouldListAllCars() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/cars"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", Is.is(6)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Is.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].mark", Is.is("VW")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].model", Is.is("Polo")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].color", Is.is("BLUE")));
    }

    @Test
    void getCarById() {
    }

    @Test
    void getCarsByColor() {
    }

    @Test
    void addCar() {
    }

    @Test
    void handleValidationExceptions() {
    }

    @Test
    void modifyCar() {
    }

    @Test
    void testModifyCar() {
    }

    @Test
    void modifyCarProperty() {
    }
}