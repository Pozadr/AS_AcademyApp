package pl.pozadr.ksb2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.pozadr.ksb2.service.CarServiceImpl;
import pl.pozadr.ksb2.model.Car;
import pl.pozadr.ksb2.model.Color;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/cars", produces = {
        MediaType.APPLICATION_XML_VALUE,
        MediaType.APPLICATION_JSON_VALUE})
public class CarApi {
    private CarServiceImpl carServiceImpl;

    @Autowired
    public CarApi(CarServiceImpl carServiceImpl) {
        this.carServiceImpl = carServiceImpl;
    }

    @GetMapping
    public ResponseEntity<List<Car>> getCars() {
        if (!carServiceImpl.getCarList().isEmpty()) {
            return new ResponseEntity(carServiceImpl.getCarList(), HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Car> getCarById(@PathVariable long id) {
        Optional<Car> first = carServiceImpl.getCarByID(id);
        if (first.isPresent()) {
            return new ResponseEntity<>(first.get(), HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }


    @GetMapping("/color/{color}")
    public ResponseEntity<List<Car>> getCarsByColor(@PathVariable String color) {
        List<Car> allCarsInColor = carServiceImpl.getCarsByColor(Color.valueOf(color));
        if (!allCarsInColor.isEmpty()) {
            return new ResponseEntity(allCarsInColor, HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }


    @PostMapping
    public ResponseEntity addCar(@RequestBody Car newCar) {
        boolean isAdded = carServiceImpl.addNewCar(newCar);
        if (isAdded) {
            return new ResponseEntity(HttpStatus.CREATED);
        }
        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @DeleteMapping("{id}")
    public ResponseEntity<Car> modifyCar(@PathVariable long id) {
        Optional<Car> carToRemove = carServiceImpl.getCarByID(id);
        if (carToRemove.isPresent()) {
            boolean isRemoved = carServiceImpl.deleteCar(id);
            if (isRemoved) {
                return new ResponseEntity(carToRemove, HttpStatus.OK);
            }
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }


    @PutMapping
    public ResponseEntity modifyCar(@RequestBody Car modifiedCar) {
        boolean isRemoved = carServiceImpl.deleteCar(modifiedCar.getId());
        boolean isAdded = carServiceImpl.addNewCar(modifiedCar);
        if (isRemoved && isAdded) {
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }


    @PatchMapping("/{id}/{property}/{value}")
    public ResponseEntity<Car> modifyCarProperty(@PathVariable long id, @PathVariable String property,
                                                 @PathVariable String value) {
        boolean isModified = carServiceImpl.modifyCarProperty(id, property, value);
        if (isModified) {
            return new ResponseEntity(carServiceImpl.getCarByID(id), HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }


}
