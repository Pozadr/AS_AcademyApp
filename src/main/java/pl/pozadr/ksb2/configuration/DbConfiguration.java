package pl.pozadr.ksb2.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.jdbc.core.JdbcTemplate;
import pl.pozadr.ksb2.model.Car;
import pl.pozadr.ksb2.model.Color;

import javax.sql.DataSource;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class DbConfiguration {
    private final DataSource dataSource;

    @Autowired
    public DbConfiguration(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public JdbcTemplate getJdbcTemplate() {
        return new JdbcTemplate(dataSource);
    }


    @EventListener(ApplicationReadyEvent.class)
    public void initDb() {
        String dropTable = "DROP TABLE IF EXISTS `cars`";
        getJdbcTemplate().update(dropTable);

        String createTable = "CREATE TABLE cars (id int, mark varchar (100), model varchar (100)," +
                " color varchar (50), production_date varchar (50), PRIMARY KEY (id))";
        getJdbcTemplate().update(createTable);

        String sql = "INSERT INTO cars (id, mark, model, color, production_date) VALUES (?,?,?,?,?)";
        createInitCarList().forEach(car ->
                getJdbcTemplate().update(sql, car.getId(), car.getMark(), car.getModel(),car.getColor().toString(),
                        Date.valueOf(car.getProductionDate())));
    }

    private List<Car> createInitCarList() {
        List<Car> initDbList = new ArrayList<>();
        initDbList.add(new Car(1L, "VW", "Golf", Color.BLACK, LocalDate.of(2011, 1, 3)));
        initDbList.add(new Car(2L, "Ford", "Mustang", Color.RED, LocalDate.of(1985, 8, 30)));
        initDbList.add(new Car(3L, "Volvo", "V70", Color.GREY, LocalDate.of(2002, 1, 12)));
        initDbList.add(new Car(4L, "VW", "T5", Color.BLACK, LocalDate.of(2011, 6, 20)));
        initDbList.add(new Car(5L, "Skoda", "Octavia", Color.GREEN, LocalDate.of(2019, 2, 8)));
        initDbList.add(new Car(6L, "Fiat", "125", Color.ORANGE, LocalDate.of(1988, 3, 15)));
        initDbList.add(new Car(7L, "Ford", "Mondeo", Color.BLACK, LocalDate.of(2013, 4, 15)));
        initDbList.add(new Car(8L, "Seat", "Ibiza", Color.YELLOW, LocalDate.of(2020, 12, 13)));
        return initDbList;
    }



}
