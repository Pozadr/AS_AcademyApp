package pl.pozadr.ksb2.dao;

import pl.pozadr.ksb2.model.Car;
import pl.pozadr.ksb2.model.Color;

import java.time.LocalDate;
import java.util.List;

public interface CarDao {
    int saveCar(String mark, String model, Color color, LocalDate date);

    List<Car> findAllCars();

    void updateCar(Car newVideo);

    int deleteCar(long id);

    Car getOneCar(long id);
}
