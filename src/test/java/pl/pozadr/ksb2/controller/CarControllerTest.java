package pl.pozadr.ksb2.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import pl.pozadr.ksb2.dao.CarDao;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(CarController.class)
class CarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarDao carDao;



    @Test
    void getCars() {
    }

    @Test
    void getCarById() {
    }

    @Test
    void getCarsByColor() {
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