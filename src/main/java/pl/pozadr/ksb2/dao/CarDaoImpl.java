package pl.pozadr.ksb2.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import pl.pozadr.ksb2.model.Car;
import pl.pozadr.ksb2.model.Color;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Repository
public class CarDaoImpl implements CarDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CarDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public int saveCar(String mark, String model, Color color, LocalDate date) {
        List<Car> allCars = findAllCars();
        long maxId = allCars.stream()
                .max(Comparator.comparing(Car::getId))
                .get()
                .getId();
        Car newCar = new Car(maxId + 1, mark, model, color, date);
        String sql = "INSERT INTO cars VALUES (?, ?, ?, ?, ?)";

        return jdbcTemplate.update(sql, newCar.getId(), newCar.getMark(), newCar.getModel(),
                newCar.getColor().toString(), newCar.getProductionDate());


    }

    @Override
    public List<Car> findAllCars() {
        List<Car> carList = new ArrayList<>();
        String sql = "SELECT * FROM cars";
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql);

        // DB to POJO
        maps.forEach(element -> carList.add(new Car(
                Long.parseLong(String.valueOf(element.get("id"))),
                String.valueOf(element.get("mark")),
                String.valueOf(element.get("model")),
                Color.valueOf(String.valueOf(element.get("color"))),
                LocalDate.parse(String.valueOf(element.get("production_date")))
        )));
        return carList;
    }

    @Override
    public void updateCar(Car newVideo) {

    }

    @Override
    public void deleteCar(long id) {

    }

    @Override
    public Car getOneCar(long id) {
        return null;
    }
}
