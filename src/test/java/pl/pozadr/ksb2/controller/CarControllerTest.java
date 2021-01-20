package pl.pozadr.ksb2.controller;

import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.pozadr.ksb2.dao.CarDaoImpl;
import pl.pozadr.ksb2.model.Car;
import pl.pozadr.ksb2.model.Color;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(CarController.class)
class CarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarDaoImpl carDao;


    @Test
    void shouldReturnListOfAllCars() throws Exception {
        List<Car> cars = List.of(
                new Car(1L, "VW", "T5", Color.BROWN,
                        LocalDate.of(2011, 2, 20)),
                new Car(2L, "VW", "T4", Color.WHITE,
                        LocalDate.of(1995, 5, 11)),
                new Car(3L, "Volvo", "V70", Color.BLACK,
                        LocalDate.of(2003, 2, 6)),
                new Car(4L, "Mercedes", "V-Class", Color.BLUE,
                        LocalDate.of(2020, 3, 4)));

        when(carDao.findAllCars()).thenReturn(cars);

        mockMvc.perform(MockMvcRequestBuilders.get("/car-main"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(view().name("car-main"))
                .andExpect(model().attributeExists("cars"));
    }

    @Test
    void shouldReturnCarById() throws Exception {
        Car testCar = new Car(1L, "VW", "T5", Color.BROWN,
                LocalDate.of(2011, 2, 20));
        when(carDao.getOneCar(testCar.getId())).thenReturn(Optional.of(testCar));

        mockMvc.perform(MockMvcRequestBuilders.get("/get-car-by-id")
                .param("id", String.valueOf(testCar.getId())))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Is.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.mark", Is.is("VW")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.model", Is.is("T5")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.color", Is.is("BROWN")));
    }

    @Test
    void shouldReturnListOfCarsInColor() throws Exception {
        List<Car> cars = List.of(
                new Car(1L, "VW", "T5", Color.BROWN,
                        LocalDate.of(2011, 2, 20)),
                new Car(2L, "VW", "T4", Color.BROWN,
                        LocalDate.of(1995, 5, 11)),
                new Car(3L, "Volvo", "V70", Color.BROWN,
                        LocalDate.of(2003, 2, 6)),
                new Car(4L, "Mercedes", "V-Class", Color.BROWN,
                        LocalDate.of(2020, 3, 4)));
        when(carDao.findCarsByColor(Color.BROWN)).thenReturn(cars);

        mockMvc.perform(MockMvcRequestBuilders.get("/get-car-by-color"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(view().name("filtered-car-list"))
                .andExpect(model().attributeExists("cars"));
    }

    @Test
    void getCarsByDate() {
    }

    @Test
    void addCar() {
    }

    @Test
    void deleteCar() {
    }

    @Test
    void modifyCar() {
    }

    @Test
    void goToHomePage() {
    }
}