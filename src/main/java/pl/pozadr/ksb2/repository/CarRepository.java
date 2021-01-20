package pl.pozadr.ksb2.repository;

import org.springframework.stereotype.Repository;
import pl.pozadr.ksb2.model.Car;
import pl.pozadr.ksb2.model.Color;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CarRepository {
    List<Car> carList;

    public CarRepository() {
        this.carList = new ArrayList<>();
        carList.add(new Car(1L, "VW", "T5", Color.BLUE));
        carList.add(new Car(2L, "Volvo", "V60", Color.BLACK));
        carList.add(new Car(3L, "Mercedes", "V-Class", Color.WHITE));
        carList.add(new Car(4L, "Mercedes", "EQC", Color.BROWN));
        carList.add(new Car(5L, "Ferrari", "California", Color.BLACK));
        carList.add(new Car(6L, "Fiat", "125p", Color.YELLOW));
    }

    public List<Car> getCarList() {
        return carList;
    }

    public void setCarList(List<Car> carList) {
        this.carList = carList;
    }
}
