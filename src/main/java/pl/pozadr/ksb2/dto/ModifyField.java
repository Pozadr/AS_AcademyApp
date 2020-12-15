package pl.pozadr.ksb2.dto;

import javax.validation.constraints.*;

public class ModifyField {
    @NotNull(message = "ID cannot be null.")
    @Min(value = 1, message = "ID must be greater than 1.")
    private long id;

    @NotNull(message = "Property cannot be null.")
    @NotBlank(message = "Property cannot be blank.")
    private String property;

    @NotNull(message = "Value cannot be null.")
    @NotBlank(message = "Value cannot be blank.")
    private String value;

    public ModifyField() {
    }

    public ModifyField(long id, String property, String value) {
        this.id = id;
        this.property = property;
        this.value = value;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
