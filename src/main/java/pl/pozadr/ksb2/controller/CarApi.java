package pl.pozadr.ksb2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import pl.pozadr.ksb2.service.CarServiceImpl;
import pl.pozadr.ksb2.model.Car;

import javax.validation.Valid;
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
            model.addAttribute("delCar", new Car());
            model.addAttribute("getById", new Car());
            model.addAttribute("getByColor", new Car());
            model.addAttribute("modifyField", new ModifyField());
            return "car-main";
        }
        return "error"; // not found

    }

    @GetMapping("/get-car-by-id")
    public String getCarById(@ModelAttribute Car carById, Model model) {
        System.out.println(carById.getId());
        Optional<Car> car = carServiceImpl.getCarByID(carById.getId());

        if (car.isPresent()) {
            model.addAttribute("car", car.get());
            return "car-by-id";
        }
        return "error";
    }


    @GetMapping("/get-car-by-color")
    public String getCarsByColor(@ModelAttribute Car carByColor, Model model) {
        List<Car> allCarsInColor = carServiceImpl.getCarsByColor(carByColor.getColor());
        if (!allCarsInColor.isEmpty()) {
            model.addAttribute("cars", allCarsInColor);
            return "car-by-color";
        }
        return "error";
    }


    @PostMapping("/add-car")
    public String addCar(@ModelAttribute Car newCar) {
        boolean isAdded = carServiceImpl.addNewCar(newCar);
        if (isAdded) {
            return "redirect:/car-main";
        }
        return "redirect:/car-main";
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }


    @GetMapping("/delete-car")
    public String deleteCar(@Valid @ModelAttribute Car delCar) {
        Optional<Car> carToRemove = carServiceImpl.getCarByID(delCar.getId());
        if (carToRemove.isPresent()) {
            boolean isRemoved = carServiceImpl.deleteCar(delCar.getId());
            if (isRemoved) {
                return "redirect:/car-main";
            }
        }
        return "error";
    }


    @GetMapping("/modify-car")
    public String modifyCar(@Valid @ModelAttribute Car modifiedCar) {
        boolean isRemoved = carServiceImpl.deleteCar(modifiedCar.getId());
        boolean isAdded = carServiceImpl.addNewCar(modifiedCar);
        if (isRemoved && isAdded) {
            return "redirect:/car-main";
        }
        return "/error";
    }


    @GetMapping("/modify-field")
    public String modifyCarProperty(@ModelAttribute ModifyField modifyField) {
        System.out.println(modifyField.getId());
        System.out.println(modifyField.getProperty());
        System.out.println(modifyField.getValue());

        boolean isModified = carServiceImpl.modifyCarProperty(modifyField.getId(), modifyField.getProperty(),
                                                                modifyField.getValue());
        if (isModified) {
            return "redirect:/car-main";
        }
        return "/error";
    }

    @GetMapping("/go-to-home-page")
    public String goToHomePage() {
        return "redirect:/car-main";
    }

}
