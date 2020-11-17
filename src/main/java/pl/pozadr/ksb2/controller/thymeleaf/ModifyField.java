package pl.pozadr.ksb2.controller.thymeleaf;

public class ModifyField {
    private long id;
    private String property;
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
