package pl.pozadr.ksb2;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products") // to avoid @PostMapping("/products") etc.
public class ProductApi {

    // PARAMETER
    /*
    //@RequestMapping(method = RequestMethod.GET)
    @GetMapping
    public String getProducts(@RequestParam String name, @RequestParam(required = false, defaultValue = "") String surname) {
        return "Hello " + name + " " + surname;
    }
     */

    // PATH
    /*
    @GetMapping("/{name}")
    public String getProducts(@PathVariable String name) {
        return "Hello " + name;
    }
     */

    // HEADER
    /*
    @GetMapping
    public String getProducts(@RequestHeader String name) {
        return "Hello " + name;
    }
     */

    // BODY
    /*
    @GetMapping
    public String getProducts(@RequestBody String name) {
        return "Hello " + name;
    }
     */

    @GetMapping
    public String getProducts(@RequestBody String name, @RequestHeader String surname) {
        return "Hello " + name + " " + surname;
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
