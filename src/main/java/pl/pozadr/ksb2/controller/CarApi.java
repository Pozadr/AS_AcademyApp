package pl.pozadr.ksb2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.pozadr.ksb2.controller.thymeleaf.ModifyField;
import pl.pozadr.ksb2.controller.thymeleaf.SingleParam;
import pl.pozadr.ksb2.model.Color;
import pl.pozadr.ksb2.service.CarServiceImpl;
import pl.pozadr.ksb2.model.Car;

import java.util.*;


@Controller
public class CarApi {
    private final CarServiceImpl carServiceImpl;

    @Autowired
    public CarApi(CarServiceImpl carServiceImpl) {
        this.carServiceImpl = carServiceImpl;
    }


    @GetMapping("/car-main")
    public String getCars(Model model) {
        if (!carServiceImpl.getCarList().isEmpty()) {
            List<Car> allCars = carServiceImpl.getCarList();
            model.addAttribute("cars", allCars);
            model.addAttribute("newCar", new Car());
            model.addAttribute("modifyCar", new Car());
            model.addAttribute("delCar", new SingleParam());
            model.addAttribute("getById", new SingleParam());
            model.addAttribute("getByColor", new SingleParam());
            model.addAttribute("modifyField", new ModifyField());
            return "car-main";
        }
        return "error-not-found";
    }


    @GetMapping("/get-car-by-id")
    public String getCarById(@Validated @ModelAttribute SingleParam input, Model model) {
        long inputCarId = Integer.parseInt(input.getInput());
        Optional<Car> car = carServiceImpl.getCarByID(inputCarId);

        if (car.isPresent()) {
            model.addAttribute("car", car.get());
            return "car-by-id";
        }
        return "error-not-found";
    }


    @GetMapping("/get-car-by-color")
    public String getCarsByColor(@Validated @ModelAttribute SingleParam input, Model model) {
        try {
            Color inputCarColor = Color.valueOf(input.getInput());
            List<Car> allCarsInColor = carServiceImpl.getCarsByColor(inputCarColor);
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
    public String addCar(@Validated @ModelAttribute Car newCar) {
        boolean isAdded = carServiceImpl.addNewCar(newCar);
        if (isAdded) {
            return "redirect:/car-main";
        }
        return "error-input";
    }


    @GetMapping("/delete-car")
    public String deleteCar(@Validated @ModelAttribute SingleParam input) {
        long inputCarId = Integer.parseInt(input.getInput());
        Optional<Car> carToRemove = carServiceImpl.getCarByID(inputCarId);
        if (carToRemove.isPresent()) {
            boolean isRemoved = carServiceImpl.deleteCar(inputCarId);
            if (isRemoved) {
                return "redirect:/car-main";
            }
        }
        return "error-input";
    }


    @GetMapping("/modify-car")
    public String modifyCar(@Validated @ModelAttribute Car modifiedCar) {
        boolean isRemoved = carServiceImpl.deleteCar(modifiedCar.getId());
        boolean isAdded = carServiceImpl.addNewCar(modifiedCar);
        if (isRemoved && isAdded) {
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
