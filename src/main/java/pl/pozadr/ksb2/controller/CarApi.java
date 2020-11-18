package pl.pozadr.ksb2.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import pl.pozadr.ksb2.model.Car;
import pl.pozadr.ksb2.model.Color;
import pl.pozadr.ksb2.service.CarServiceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping(path = "/cars", produces = {
        MediaType.APPLICATION_JSON_VALUE, //default
        MediaType.APPLICATION_XML_VALUE,
})
public class CarApi {
    private final CarServiceImpl carServiceImpl;

    @Autowired
    public CarApi(CarServiceImpl carServiceImpl) {
        this.carServiceImpl = carServiceImpl;
    }

    @GetMapping
    public ResponseEntity<CollectionModel<Car>> getCars() {
        if (!carServiceImpl.getCarList().isEmpty()) {
            List<Car> allCars = carServiceImpl.getCarList();
            allCars.forEach(car -> car.addIf(!car.hasLinks(), () -> linkTo(CarApi.class).slash(car.getId()).withSelfRel()));
            Link link = linkTo(CarApi.class)
                    .withSelfRel();
            CollectionModel<Car> carEntityModel = CollectionModel.of(allCars, link);
            return ResponseEntity.ok(carEntityModel);
        }
        return ResponseEntity.notFound().build();
    }


    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Car>> getCarById(@PathVariable long id) {
        Optional<Car> firstCarOnList = carServiceImpl.getCarByID(id);
        if (firstCarOnList.isPresent()) {
            Link link = linkTo(CarApi.class).slash(id).withSelfRel();
            //EntityModel<Car> carEntityModel = EntityModel.of(firstCarOnList.get(), link);
            EntityModel<Car> carEntityModel = firstCarOnList.map(car -> new EntityModel(car.addIf(!car.hasLinks(), () -> link))).get();
            return ResponseEntity.ok(carEntityModel);
        }
        return ResponseEntity.notFound().build();
    }


    @GetMapping("/color/{color}")
    public ResponseEntity<CollectionModel<Car>> getCarsByColor(@PathVariable String color) {
        List<Car> allCarsInColor = carServiceImpl.getCarsByColor(Color.valueOf(color));
        if (!allCarsInColor.isEmpty()) {
            allCarsInColor.forEach(car -> car.addIf(!car.hasLinks(), () -> linkTo(CarApi.class)
                    .slash(car.getId())
                    .withSelfRel()));
            allCarsInColor.forEach(car -> car.addIf(!car.hasLinks(), () -> linkTo(CarApi.class)
                    .withRel("allColors")));
            Link link = linkTo(CarApi.class).slash("color").slash(color).withSelfRel();
            CollectionModel<Car> carsCollectionModel = CollectionModel.of(allCarsInColor, link);
            return ResponseEntity.ok(carsCollectionModel);
        }
        return ResponseEntity.notFound().build();
    }


    @PostMapping
    public ResponseEntity addCar(@Validated @RequestBody Car newCar) {
        boolean isAdded = carServiceImpl.addNewCar(newCar);
        if (isAdded) {
            Link link = linkTo(CarApi.class).slash(newCar.getId()).withSelfRel();
            return new ResponseEntity<>(newCar.addIf(!newCar.hasLinks(), () -> link), HttpStatus.CREATED);
        }
        return new ResponseEntity("{\n\t\"id\": \"not unique.\"\n}", HttpStatus.BAD_REQUEST);
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

    @DeleteMapping("{id}")
    public ResponseEntity<Car> modifyCar(@PathVariable long id) {
        Optional<Car> carToRemove = carServiceImpl.getCarByID(id);
        if (carToRemove.isPresent()) {
            boolean isRemoved = carServiceImpl.deleteCar(id);
            if (isRemoved) {
                return ResponseEntity.ok(carToRemove.get());
            }
        }
        return ResponseEntity.notFound().build();
    }


    @PutMapping
    public ResponseEntity<Car> modifyCar(@Validated @RequestBody Car modifiedCar) {
        boolean isRemoved = carServiceImpl.deleteCar(modifiedCar.getId());
        boolean isAdded = carServiceImpl.addNewCar(modifiedCar);
        if (isRemoved && isAdded) {
            return ResponseEntity.ok(modifiedCar);
        }
        return ResponseEntity.notFound().build();
    }


    @PatchMapping("/{id}/{property}/{value}")
    public ResponseEntity<Car> modifyCarProperty(@PathVariable long id, @PathVariable String property,
                                                 @PathVariable String value) {
        boolean isModified = carServiceImpl.modifyCarProperty(id, property, value);
        if (isModified) {
            return ResponseEntity.ok(carServiceImpl.getCarByID(id).get());
        }
        return ResponseEntity.notFound().build();
    }

}
