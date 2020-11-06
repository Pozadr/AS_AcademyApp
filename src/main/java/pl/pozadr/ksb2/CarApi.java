package pl.pozadr.ksb2;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cars")
public class CarApi {
    private final List<Car> carList;

    public CarApi() {
        this.carList = new ArrayList<>();
        carList.add(new Car(1, "VW", "Polo", "blue"));
        carList.add(new Car(2, "Volvo", "V60", "white"));
        carList.add(new Car(3, "Mercedes", "VClass", "black"));
    }

    @GetMapping
    public ResponseEntity<List<Car>> getCars() {
        if (!carList.isEmpty()) {
            return new ResponseEntity(carList, HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Car> getCarById(@PathVariable long id) {
        Optional<Car> first = carList.stream().filter(video -> video.getId() == id).findFirst();
        if (first.isPresent()) {
            return new ResponseEntity<>(first.get(), HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/color/{color}")
    public ResponseEntity<List<Car>> getCarByColor(@PathVariable String color) {
        List<Car> allCarsInColor = carList.stream()
                .filter(video -> video.getColor().equals(color))
                .collect(Collectors.toList());
        if (!allCarsInColor.isEmpty()) {
            return new ResponseEntity(allCarsInColor, HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity addCar(@RequestBody Car newCar) {
        boolean addNewCar = carList.add(newCar);
        if (addNewCar) {
            return new ResponseEntity(HttpStatus.CREATED);
        }
        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping
    public ResponseEntity modifyCar(@RequestBody Car modifiedCar) {
        Optional<Car> firstCarOnList = carList.stream()
                .filter(car -> car.getId() == modifiedCar.getId())
                .findFirst();
        if (firstCarOnList.isPresent()) {
            carList.remove(firstCarOnList.get());
            carList.add(modifiedCar);
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @PatchMapping("/mark/{id}")
    public ResponseEntity modifyCarMark(@PathVariable long id, @RequestParam String newMark) {
        Optional<Car> first = carList.stream()
                .filter(car -> car.getId() == (id))
                .findFirst();
        if (first.isPresent()) {
            first.get().setMark(newMark);
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @PatchMapping("/model/{id}")
    public ResponseEntity modifyCarModel(@PathVariable long id, @RequestParam String newModel) {
        Optional<Car> first = carList.stream()
                .filter(car -> car.getId() == (id))
                .findFirst();
        if (first.isPresent()) {
            first.get().setModel(newModel);
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @PatchMapping("/color/{id}")
    public ResponseEntity modifyCarColor(@PathVariable long id, @RequestParam String newColor) {
        Optional<Car> first = carList.stream()
                .filter(car -> car.getId() == (id))
                .findFirst();
        if (first.isPresent()) {
            first.get().setColor(newColor);
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Car> modifyCar(@PathVariable long id) {
        Optional<Car> first = carList.stream()
                .filter(car -> car.getId() == id)
                .findFirst();
        if (first.isPresent()) {
            carList.remove(first.get());
            return new ResponseEntity(first.get(), HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

}
