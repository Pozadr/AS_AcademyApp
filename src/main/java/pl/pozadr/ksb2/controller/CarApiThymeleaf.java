package pl.pozadr.ksb2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.pozadr.ksb2.model.Color;
import pl.pozadr.ksb2.service.CarServiceImpl;
import pl.pozadr.ksb2.model.Car;

import java.util.*;


@Controller
public class CarApiThymeleaf {
    private final CarServiceImpl carServiceImpl;

    @Autowired
    public CarApiThymeleaf(CarServiceImpl carServiceImpl) {
        this.carServiceImpl = carServiceImpl;
    }


    @GetMapping("/car-main")
    public String getCars(Model model) {
        if (!carServiceImpl.getCarList().isEmpty()) {
            List<Car> allCars = carServiceImpl.getCarList();
            model.addAttribute("cars", allCars);
            model.addAttribute("newCar", new Car());
            model.addAttribute("getByColor", new SingleParam());
            model.addAttribute("modifyField", new ModifyField());
            return "car-main";
        }
        return "error-not-found";
    }


    @GetMapping("/get-car-by-id")
    @ResponseBody
    public Optional<Car> getCarById(long id, Model model) {
        return carServiceImpl.getCarByID(id);
    }


    @GetMapping("/get-car-by-color")
    public String getCarsByColor(@Validated Color color, Model model) {
        try {
            List<Car> allCarsInColor = carServiceImpl.getCarsByColor(color);
            if (!allCarsInColor.isEmpty()) {
                model.addAttribute("cars", allCarsInColor);
                return "car-by-color";
            }
            return "error-not-found";
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
            return "error-input";
        }
    }


    @PostMapping("/add-car")
    public String addCar(String mark, String model, String color) {
        boolean isAdded = carServiceImpl.addNewCar(mark, model, color);
        System.out.println("mark: " + mark);
        System.out.println("model: " + model);
        System.out.println("color: " + color);
        if (isAdded) {
            return "redirect:/car-main";
        }
        return "error-input";
    }


    @GetMapping("/delete-car")
    public String deleteCar(long id) {
        boolean isRemoved = carServiceImpl.deleteCar(id);
        if (isRemoved) {
            return "redirect:/car-main";
        }
        return "error-input";
    }


    @RequestMapping(value = "/modify-car", method = {RequestMethod.PUT, RequestMethod.POST, RequestMethod.GET})
    public String modifyCar(Car modifiedCar) {
        boolean isModified = carServiceImpl.modifyCar(modifiedCar);
        if (isModified) {
            System.out.println("ok before redirect");
            return "redirect:/car-main";
        }
        return "error-input";
    }


    @GetMapping("/modify-field")
    public String modifyCarProperty(@Validated @ModelAttribute ModifyField modifyField) {
        boolean isModified = carServiceImpl.modifyCarProperty(modifyField.getId(), modifyField.getProperty(),
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
