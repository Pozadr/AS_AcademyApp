package pl.pozadr.ksb2.dto;

import pl.pozadr.ksb2.model.Color;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
}
