package pl.pozadr.ksb2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pozadr.ksb2.model.Car;
import pl.pozadr.ksb2.model.Color;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class CarServiceImpl {
    List<Car> carList;
    /*

    @Autowired
    public CarServiceImpl(CarRepository carRepository) {
        this.carList = carRepository.getCarList();
    }



    public Optional<Car> getCarByID(long id) {
        return carList.stream()
                .filter(video -> video.getId() == id)
                .findFirst();
    }

    @Override
    public List<Car> getCarsByColor(Color color) {
        return carList.stream()
                .sorted(Comparator.comparing(Car::getId))
                .filter(video -> video.getColor().equals(color))
                .collect(Collectors.toList());
    }

    @Override
    public boolean addNewCar(String mark, String model, String color) {
        long maxId = carList.stream()
                .max(Comparator.comparing(Car::getId))
                .get()
                .getId();
        Car newCar = new Car();
        newCar.setId(maxId + 1);
        newCar.setMark(mark);
        newCar.setModel(model);
        newCar.setColor(Color.valueOf(color));
        return carList.add(newCar);
    }

    @Override
    public boolean modifyCar(Car modCar) {
        Optional<Car> carToEditOpt = getCarByID(modCar.getId());
        if (carToEditOpt.isPresent()) {
            Car carToEdit = carToEditOpt.get();
            carToEdit.setMark(modCar.getMark());
            carToEdit.setModel(modCar.getModel());
            carToEdit.setColor(modCar.getColor());
            return true;
        } else {
            return false;
        }

    }

    @Override
    public boolean deleteCar(long id) {
        Optional<Car> first = carList.stream()
                .filter(car -> car.getId() == id)
                .findFirst();
        return first.map(car -> carList.remove(car)).orElse(false);
    }

    @Override
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
                try {
                    Color valueColor = Color.valueOf(value.toUpperCase());
                    first.get().setColor(valueColor);
                    return true;
                } catch (IllegalArgumentException ex) {
                    System.out.println(ex.getMessage());
                    return false;
                }
            }
            default: {
                return false;
            }
        }
    }


 */

}
