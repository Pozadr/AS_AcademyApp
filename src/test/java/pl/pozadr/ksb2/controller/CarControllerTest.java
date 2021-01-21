package pl.pozadr.ksb2.controller;

import org.hamcrest.Matchers;
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
import pl.pozadr.ksb2.dto.AddCar;
import pl.pozadr.ksb2.model.Car;
import pl.pozadr.ksb2.model.Color;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
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
                .andExpect(model().attributeExists("cars"))
                .andExpect(model().attribute("cars", Matchers.hasSize(4)))
                .andExpect(model().attribute("cars", Matchers.hasItem(Matchers.allOf(
                        Matchers.<Car>hasProperty("id", Is.is(1L)),
                        Matchers.<Car>hasProperty("mark", Is.is("VW")),
                        Matchers.<Car>hasProperty("model", Is.is("T5")),
                        Matchers.<Car>hasProperty("color", Is.is(Color.BROWN)),
                        Matchers.<Car>hasProperty("productionDate",
                                Is.is(LocalDate.of(2011, 2, 20)))))))

                .andExpect(model().attribute("cars", Matchers.hasItem(Matchers.allOf(
                        Matchers.<Car>hasProperty("id", Is.is(4L)),
                        Matchers.<Car>hasProperty("mark", Is.is("Mercedes")),
                        Matchers.<Car>hasProperty("model", Is.is("V-Class")),
                        Matchers.<Car>hasProperty("color", Is.is(Color.BROWN)),
                        Matchers.<Car>hasProperty("productionDate",
                                Is.is(LocalDate.of(2020, 3, 4)))
                ))));

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

        mockMvc.perform(MockMvcRequestBuilders.get("/get-car-by-color")
                .param("color", "BROWN"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(view().name("filtered-car-list"))
                .andExpect(model().attributeExists("cars"))
                .andExpect(model().attribute("cars", Matchers.hasSize(4)))

                .andExpect(model().attribute("cars", Matchers.hasItem(Matchers.allOf(
                        Matchers.<Car>hasProperty("id", Is.is(1L)),
                        Matchers.<Car>hasProperty("color", Is.is(Color.BROWN))))))

                .andExpect(model().attribute("cars", Matchers.hasItem(Matchers.allOf(
                        Matchers.<Car>hasProperty("id", Is.is(2L)),
                        Matchers.<Car>hasProperty("color", Is.is(Color.BROWN))))))

                .andExpect(model().attribute("cars", Matchers.hasItem(Matchers.allOf(
                        Matchers.<Car>hasProperty("id", Is.is(3L)),
                        Matchers.<Car>hasProperty("color", Is.is(Color.BROWN))))))

                .andExpect(model().attribute("cars", Matchers.hasItem(Matchers.allOf(
                        Matchers.<Car>hasProperty("id", Is.is(4L)),
                        Matchers.<Car>hasProperty("color", Is.is(Color.BROWN))))));
        }

    @Test
    void shouldReturnListOfCarsByDate() throws Exception {
        List<Car> cars = List.of(
                new Car(1L, "VW", "T5", Color.BROWN,
                        LocalDate.of(2011, 2, 20)),
                new Car(2L, "VW", "T4", Color.BROWN,
                        LocalDate.of(2005, 5, 11)),
                new Car(3L, "Volvo", "V70", Color.BROWN,
                        LocalDate.of(2003, 2, 6)),
                new Car(4L, "Mercedes", "V-Class", Color.BROWN,
                        LocalDate.of(2019, 3, 4)));
        String from = "2004-01-01";
        String to = "2020-01-01";
        when(carDao.findCarsByDate(LocalDate.parse(from), LocalDate.parse(to))).thenReturn(cars);

        mockMvc.perform(MockMvcRequestBuilders.get("/get-car-by-date")
                .param("from", "2004-01-01")
                .param("to", "2020-01-01"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(view().name("filtered-car-list"))
                .andExpect(model().attributeExists("cars"))
                .andExpect(model().attribute("cars", Matchers.hasSize(4)))

                .andExpect(model().attribute("cars", Matchers.hasItem(Matchers.allOf(
                        Matchers.<Car>hasProperty("id", Is.is(1L)),
                        Matchers.<Car>hasProperty("productionDate",
                                Is.is(LocalDate.of(2011, 2, 20)))))))

                .andExpect(model().attribute("cars", Matchers.hasItem(Matchers.allOf(
                        Matchers.<Car>hasProperty("id", Is.is(2L)),
                        Matchers.<Car>hasProperty("productionDate",
                                Is.is(LocalDate.of(2005, 5, 11)))))))

                .andExpect(model().attribute("cars", Matchers.hasItem(Matchers.allOf(
                        Matchers.<Car>hasProperty("id", Is.is(3L)),
                        Matchers.<Car>hasProperty("productionDate",
                                Is.is(LocalDate.of(2003, 2, 6)))))))

                .andExpect(model().attribute("cars", Matchers.hasItem(Matchers.allOf(
                        Matchers.<Car>hasProperty("id", Is.is(4L)),
                        Matchers.<Car>hasProperty("productionDate",
                                Is.is(LocalDate.of(2019, 3, 4)))))));
    }

    @Test
    void shouldAddCarAndRedirectToMainPage() throws Exception {
        AddCar newCar = new AddCar("VW", "Polo", Color.BLUE, LocalDate.of(2019, 3, 4));
        when(carDao.saveCar(any(), any(), any(), any())).thenReturn(1);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/add-car")
                .param("mark", newCar.getMark())
                .param("model", newCar.getModel())
                .param("color", String.valueOf(newCar.getColor()))
                .param("productionDate", String.valueOf(newCar.getProductionDate())))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(view().name("redirect:/car-main"));
    }

    @Test
    void shouldDeleteCarAndRedirectToMainPage() throws Exception {
        when(carDao.deleteCar(anyLong())).thenReturn(1);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/delete-car")
                .param("id", "1"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(view().name("redirect:/car-main"));
    }

    @Test
    void shouldModifyCarAndRedirectToMainPage() {
    }

    @Test
    void shouldRedirectToMainPage() {
    }
}