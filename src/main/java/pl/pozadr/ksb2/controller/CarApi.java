package pl.pozadr.ksb2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.pozadr.ksb2.service.CarService;
import pl.pozadr.ksb2.model.Car;
import pl.pozadr.ksb2.model.Color;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cars")
public class CarApi {
    private CarService carService;

    @Autowired
    public CarApi(CarService carService) {
        this.carService = carService;
    }

    @GetMapping
    public ResponseEntity<List<Car>> getCars() {
        if (!carService.getCarList().isEmpty()) {
            return new ResponseEntity(carService.getCarList(), HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Car> getCarById(@PathVariable long id) {
        Optional<Car> first = carService.getCarByID(id);
        if (first.isPresent()) {
            return new ResponseEntity<>(first.get(), HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }


    @GetMapping("/color/{color}")
    public ResponseEntity<List<Car>> getCarsByColor(@PathVariable String color) {
        List<Car> allCarsInColor = carService.getCarsByColor(Color.valueOf(color));
        if (!allCarsInColor.isEmpty()) {
            return new ResponseEntity(allCarsInColor, HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }


    @PostMapping
    public ResponseEntity addCar(@RequestBody Car newCar) {
        boolean isAdded = carService.addNewCar(newCar);
        if (isAdded) {
            return new ResponseEntity(HttpStatus.CREATED);
        }
        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @DeleteMapping("{id}")
    public ResponseEntity<Car> modifyCar(@PathVariable long id) {
        Optional<Car> carToRemove = carService.getCarByID(id);
        if (carToRemove.isPresent()) {
            boolean isRemoved = carService.deleteCar(id);
            if (isRemoved) {
                return new ResponseEntity(carToRemove, HttpStatus.OK);
            }
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }


    @PutMapping
    public ResponseEntity modifyCar(@RequestBody Car modifiedCar) {
        boolean isRemoved = carService.deleteCar(modifiedCar.getId());
        boolean isAdded = carService.addNewCar(modifiedCar);
        if (isRemoved && isAdded) {
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }


    @PatchMapping("/{id}/{property}/{value}")
    public ResponseEntity<Car> modifyCarProperty(@PathVariable long id, @PathVariable String property,
                                                 @PathVariable String value) {
        boolean isModified = carService.modifyCarProperty(id, property, value);
        if (isModified) {
            return new ResponseEntity(carService.getCarByID(id), HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }


}
