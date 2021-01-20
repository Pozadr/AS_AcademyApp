package pl.pozadr.ksb2.controller;

import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * CAR REPOSITORY:
 * public CarRepository() {
 *         this.carList = new ArrayList<>();
 *         carList.add(new Car(1L, "VW", "T5", Color.BLUE));
 *         carList.add(new Car(2L, "Volvo", "V60", Color.BLACK));
 *         carList.add(new Car(3L, "Mercedes", "V-Class", Color.WHITE));
 *         carList.add(new Car(4L, "Mercedes", "EQC", Color.BROWN));
 *         carList.add(new Car(5L, "Ferrari", "California", Color.BLACK));
 *         carList.add(new Car(6L, "Fiat", "125p", Color.YELLOW));
 * }
 */
@SpringBootTest
@AutoConfigureMockMvc
class CarApiTest {

    @Autowired
    private MockMvc mockMvc;


    @Test
    void shouldReturnListOfAllCars() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/cars"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", Is.is(6)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Is.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].mark", Is.is("VW")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].model", Is.is("T5")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].color", Is.is("BLUE")));
    }

    @Test
    void shouldReturnCarById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/cars/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Is.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.mark", Is.is("VW")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.model", Is.is("T5")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.color", Is.is("BLUE")));
    }

    @Test
    void shouldReturnListOfCarsByColor() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/cars/color/BLACK"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", Is.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Is.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].mark", Is.is("Volvo")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].model", Is.is("V60")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].color", Is.is("BLACK")))

                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id", Is.is(5)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].mark", Is.is("Ferrari")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].model", Is.is("California")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].color", Is.is("BLACK")));
    }

    @Test
    void shouldAddCarAndReturnAddedCar() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/cars")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{" +
                        "\"id\": 7," +
                        "\"mark\": \"VW\"," +
                        "\"model\": \"Polo\"," +
                        "\"color\": \"BLUE\"" +
                        "}"))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Is.is(7)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.mark", Is.is("VW")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.model", Is.is("Polo")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.color", Is.is("BLUE")));
    }

    @Test
    void shouldDeleteAndReturnDeletedCar() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/cars/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Is.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.mark", Is.is("VW")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.model", Is.is("T5")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.color", Is.is("BLUE")));
    }

    @Test
    void shouldModifyCarAndReturnModifiedCar() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.put("/cars")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{" +
                        "\"id\": 2," +
                        "\"mark\": \"VW\"," +
                        "\"model\": \"Polo\"," +
                        "\"color\": \"BLUE\"" +
                        "}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Is.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.mark", Is.is("VW")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.model", Is.is("Polo")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.color", Is.is("BLUE")));
    }

    @Test
    void shouldModifyCarPropertyAndReturnModifiedCar() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.patch("/cars/2/mark/FiatModified"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Is.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.mark", Is.is("FiatModified")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.model", Is.is("V60")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.color", Is.is("BLACK")));
    }
}