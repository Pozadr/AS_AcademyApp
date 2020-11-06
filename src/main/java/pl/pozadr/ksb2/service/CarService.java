package pl.pozadr.ksb2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pozadr.ksb2.model.Car;
import pl.pozadr.ksb2.model.Color;
import pl.pozadr.ksb2.repository.CarRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarService {
    List<Car> carList;

    @Autowired
    public CarService(CarRepository carRepository) {
        this.carList = carRepository.getCarList();
    }


    public List<Car> getCarList() {
        return carList;
    }


    public Optional<Car> getCarByID(long id) {
        return carList.stream()
                .filter(video -> video.getId() == id)
                .findFirst();
    }


    public List<Car> getCarsByColor(Color color) {
        return carList.stream()
                .filter(video -> video.getColor().equals(color))
                .collect(Collectors.toList());
    }


    public boolean addNewCar(Car newCar) {
        Optional<Car> idOnList = carList.stream()
                .filter(car -> car.getId() == newCar.getId())
                .findFirst();
        if (idOnList.isPresent()) {
            return false;
        } else {
            return carList.add(newCar);
        }
    }


    public boolean deleteCar(long id) {
        Optional<Car> first = carList.stream()
                .filter(car -> car.getId() == id)
                .findFirst();
        return first.map(car -> carList.remove(car)).orElse(false);
    }


    public boolean modifyCarProperty(long id, String property, String value) {
        Optional<Car> first = carList.stream()
                .filter(car -> car.getId() == (id))
                .findFirst();

        if (first.isEmpty()) {
            return false;
        }

        switch (property) {
            case "mark": {
                first.get().setMark(value);
                return true;
            }
            case "model": {
                first.get().setModel(value);
                return true;
            }
            case "color": {
                first.get().setColor(Color.valueOf(value));
                return true;
            }
            default: {
                return false;
            }
        }
    }


}
