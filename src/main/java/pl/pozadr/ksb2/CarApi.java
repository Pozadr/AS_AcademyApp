package pl.pozadr.ksb2;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
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
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/color/{color}")
    public ResponseEntity<List<Car>> getCarByColor(@PathVariable String color) {
        List<Car> allCarsInColor = carList.stream()
                .filter(video -> video.getColor().equals(color))
                .collect(Collectors.toList());
        if (!allCarsInColor.isEmpty()) {
            return new ResponseEntity<>(allCarsInColor, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


}
