package pl.pozadr.ksb2.model;


import javax.validation.constraints.*;

public class Car {
    @NotNull(message = "ID cannot be null.")
    @Min(value = 1, message = "ID must be greater than 1.")
    private long id;

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


    public Car() {
    }


    public Car(long id, String mark, String model, Color color) {
        this.id = id;
        this.mark = mark;
        this.model = model;
        this.color = color;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", mark='" + mark + '\'' +
                ", model='" + model + '\'' +
                ", color='" + color + '\'' +
                '}';
    }
}
