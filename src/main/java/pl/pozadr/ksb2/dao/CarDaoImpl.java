package pl.pozadr.ksb2.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import pl.pozadr.ksb2.model.Car;
import pl.pozadr.ksb2.model.Color;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

@Repository
public class CarDaoImpl implements CarDao {
    Logger logger = LoggerFactory.getLogger(CarDaoImpl.class);
    private final JdbcTemplate jdbcTemplate;


    @Autowired
    public CarDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public int saveCar(String mark, String model, Color color, LocalDate date) {
        try {
            String sql = "INSERT INTO cars(mark, model, color, production_date) VALUES (?, ?, ?, ?)";
            return jdbcTemplate.update(sql, mark, model, color.toString(), date);
        } catch (DataAccessException e) {
            logger.error("Cannot connect with DB!");
            e.printStackTrace();
        }
        return 0;
    }


    @Override
    public List<Car> findAllCars() {
        String sql = "SELECT * FROM cars";
        List<Map<String, Object>> dbOutput = jdbcTemplate.queryForList(sql);
        return dbToPojoMapper(dbOutput);
    }


    @Override
    public List<Car> findCarsByColor(Color color) {
        String sql = "SELECT * FROM cars WHERE color = ?";
        List<Map<String, Object>> dbOutput = jdbcTemplate.queryForList(sql, color.toString());
        return dbToPojoMapper(dbOutput);
    }


    @Override
    public List<Car> findCarsByDate(LocalDate fromDate, LocalDate toDate) {
        String sql = "SELECT * FROM cars WHERE (production_date > ? AND production_date < ?)";
        List<Map<String, Object>> dbOutput = jdbcTemplate.queryForList(sql, fromDate.toString(), toDate.toString());
        return dbToPojoMapper(dbOutput);
    }


    @Override
    public int updateCar(Car newCar) {
        String sql = "UPDATE cars SET cars.mark = ?, cars.model = ?, cars.color = ?, cars.production_date = ?" +
                " WHERE id = ?";
        return jdbcTemplate.update(sql, newCar.getMark(), newCar.getModel(), newCar.getColor().toString(),
                Date.valueOf(newCar.getProductionDate()), newCar.getId());
    }


    @Override
    public int deleteCar(long id) {
        String sql = "DELETE FROM cars WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }


    @Override
    public Optional<Car> getOneCar(long id) {
        String sql = "SELECT * FROM cars WHERE id = ?";
        try {
            Car car = jdbcTemplate.queryForObject(sql,
                    (rs, rowNum) -> new Car(rs.getLong("id"),
                            rs.getString("mark"),
                            rs.getString("model"),
                            Color.valueOf(rs.getString("color")),
                            LocalDate.parse(rs.getString("production_date"))),
                    id);
            return Optional.ofNullable(car);
        } catch (IncorrectResultSizeDataAccessException ex) {
            System.out.println(ex.getMessage());
            return Optional.empty();
        }
    }


    private List<Car> dbToPojoMapper(List<Map<String, Object>> dbOutput) {
        List<Car> carList = new ArrayList<>();
        // DB to POJO
        dbOutput.forEach(element -> carList.add(new Car(
                Long.parseLong(String.valueOf(element.get("id"))),
                String.valueOf(element.get("mark")),
                String.valueOf(element.get("model")),
                Color.valueOf(String.valueOf(element.get("color"))),
                LocalDate.parse(String.valueOf(element.get("production_date")))
        )));
        return carList;
    }
}
