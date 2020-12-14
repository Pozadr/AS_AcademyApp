package pl.pozadr.ksb2.controller.thymeleaf;

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
    @ResponseBody
    public Optional<Car> getCarById(long id, Model model) {
        return carServiceImpl.getCarByID(id);
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
    public String addCar(@Validated Car newCar) {
        boolean isAdded = carServiceImpl.addNewCar(newCar);
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
