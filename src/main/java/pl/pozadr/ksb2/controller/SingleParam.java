package pl.pozadr.ksb2.controller;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class SingleParam {
    @NotNull(message = "Input cannot be null.")
    @NotBlank(message = "Input cannot be blank.")
    private String input;

    public SingleParam() {
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }
}
