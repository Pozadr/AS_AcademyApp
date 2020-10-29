package pl.pozadr.ksb2;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products") // to avoid @PostMapping("/products") etc.
public class ProductApi {

    //@RequestMapping(method = RequestMethod.GET)
    @GetMapping
    public String getProducts() {
        return "Hello World with GET";
    }

    @PostMapping
    public String addProduct() {
        return "Hello World with POST";
    }

    @PutMapping
    public String modifyProduct() {
        return "Hello World with PUT";
    }

    @DeleteMapping
    public String deleteProduct() {
        return "Hello World with DELETE";
    }

}
