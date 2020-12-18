package pl.pozadr.ksb2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.pozadr.ksb2.dao.CarDao;
import pl.pozadr.ksb2.dto.AddCar;
import pl.pozadr.ksb2.model.Car;
import pl.pozadr.ksb2.model.Color;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;


@Controller
public class CarController {
    private final CarDao carDao;

    @Autowired
    public CarController(CarDao carDao) {
        this.carDao = carDao;
    }


    @GetMapping("/car-main")
    public String getCars(Model model) {
        List<Car> allCars = carDao.findAllCars();
        model.addAttribute("cars", allCars);
        return "car-main";
    }


    @GetMapping("/get-car-by-id")
    @ResponseBody
    public Car getCarById(long id, Model model) {
        return carDao.getOneCar(id);
    }


    @GetMapping("/get-car-by-color")
    public String getCarsByColor(Color color, Model model) {
        try {
            List<Car> allCarsInColor = carDao.findCarsByColor(color);
            model.addAttribute("cars", allCarsInColor);
            return "filtered-car-list";
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
            return "error-input";
        }
    }

    @GetMapping("/get-car-by-date")
    public String getCarsByDate(String from, String to, Model model) {
        try {
            List<Car> allCarsBetweenDate = carDao.findCarsByDate(LocalDate.parse(from),
                    LocalDate.parse(to));
            model.addAttribute("cars", allCarsBetweenDate);
            return "filtered-car-list";
        } catch (IllegalArgumentException | DateTimeParseException ex) {
            System.out.println(ex.getMessage());
            return "error-input";
        }
    }


    @PostMapping("/add-car")
    public String addCar(@Validated AddCar addCar) {
        int isAdded = carDao.saveCar(addCar.getMark(), addCar.getModel(), addCar.getColor(),
                addCar.getProductionDate());
        if (isAdded == 1) {
            return "redirect:/car-main";
        }
        return "error-input";
    }


    @GetMapping("/delete-car")
    public String deleteCar(long id) {
        int isRemoved = carDao.deleteCar(id);
        if (isRemoved == 1) {
            return "redirect:/car-main";
        }
        return "error-input";
    }


    @RequestMapping(value = "/modify-car", method = {RequestMethod.PUT, RequestMethod.POST, RequestMethod.GET})
    public String modifyCar(@Validated Car modifiedCar) {
        int isModified = carDao.updateCar(modifiedCar);
        if (isModified == 1) {
            return "redirect:/car-main";
        }
        return "error-input";
    }


    @GetMapping("/go-to-home-page")
    public String goToHomePage() {
        return "redirect:/car-main";
    }


}
