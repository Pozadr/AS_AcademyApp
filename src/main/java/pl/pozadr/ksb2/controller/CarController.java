package pl.pozadr.ksb2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.pozadr.ksb2.dto.AddCar;
import pl.pozadr.ksb2.dto.ModifyField;
import pl.pozadr.ksb2.model.Color;
import pl.pozadr.ksb2.service.CarService;
import pl.pozadr.ksb2.model.Car;

import java.util.*;


@Controller
public class CarController {
    private final CarService carService;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }


    @GetMapping("/car-main")
    public String getCars(Model model) {
        List<Car> allCars = carService.getCarList();
        model.addAttribute("cars", allCars);
        return "car-main";
    }


    @GetMapping("/get-car-by-id")
    @ResponseBody
    public Optional<Car> getCarById(long id, Model model) {
        return carService.getCarByID(id);
    }


    @GetMapping("/get-car-by-color")
    public String getCarsByColor(Color color, Model model) {
        try {
            List<Car> allCarsInColor = carService.getCarsByColor(color);
            model.addAttribute("cars", allCarsInColor);
            return "car-by-color";
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
            return "error-input";
        }
    }


    @PostMapping("/add-car")
    public String addCar(@Validated AddCar addCar) {
        boolean isAdded = carService.addNewCar(addCar.getMark(), addCar.getModel(), addCar.getColor().toString());
        if (isAdded) {
            return "redirect:/car-main";
        }
        return "error-input";
    }


    @GetMapping("/delete-car")
    public String deleteCar(long id) {
        boolean isRemoved = carService.deleteCar(id);
        if (isRemoved) {
            return "redirect:/car-main";
        }
        return "error-input";
    }


    @RequestMapping(value = "/modify-car", method = {RequestMethod.PUT, RequestMethod.POST, RequestMethod.GET})
    public String modifyCar(@Validated Car modifiedCar) {
        boolean isModified = carService.modifyCar(modifiedCar);
        if (isModified) {
            System.out.println("ok before redirect");
            return "redirect:/car-main";
        }
        return "error-input";
    }


    @GetMapping("/modify-field")
    public String modifyCarProperty(@Validated ModifyField modifyField) {
        boolean isModified = carService.modifyCarProperty(modifyField.getId(), modifyField.getProperty(),
                modifyField.getValue());
        if (isModified) {
            return "redirect:/car-main";
        }
        return "error-input";
    }


    @GetMapping("/go-to-home-page")
    public String goToHomePage() {
        return "redirect:/car-main";
    }

}
