package pl.pozadr.ksb2.dto;

import org.springframework.format.annotation.DateTimeFormat;
import pl.pozadr.ksb2.model.Color;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

public class AddCar {
    @NotNull(message = "Mark cannot be null.")
    @NotBlank(message = "Mark cannot be blank.")
    @Size(min = 1, message = "Mark must have at least 1 character length.")
    private String mark;

    @NotNull(message = "Model cannot be null.")
    @NotBlank(message = "Model cannot be blank.")
    @Size(min = 1, message = "Model must have at least 1 character length.")
    private String model;

    @NotNull(message = "Color cannot be null.")
    private Color color;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Model cannot be null.")
    private LocalDate productionDate;

    public AddCar() {
    }

    public AddCar(@NotNull(message = "Mark cannot be null.") @NotBlank(message = "Mark cannot be blank.") @Size(min = 1, message = "Mark must have at least 1 character length.") String mark, @NotNull(message = "Model cannot be null.") @NotBlank(message = "Model cannot be blank.") @Size(min = 1, message = "Model must have at least 1 character length.") String model, @NotNull(message = "Color cannot be null.") Color color, @NotNull(message = "Model cannot be null.") LocalDate productionDate) {
        this.mark = mark;
        this.model = model;
        this.color = color;
        this.productionDate = productionDate;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public LocalDate getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(LocalDate productionDate) {
        this.productionDate = productionDate;
    }

    @Override
    public String toString() {
        return "AddCar{" +
                "mark='" + mark + '\'' +
                ", model='" + model + '\'' +
                ", color=" + color +
                ", productionDate=" + productionDate +
                '}';
    }
}
