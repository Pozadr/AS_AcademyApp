package pl.pozadr.ksb2.exceptions.carnotfound;


public class CarNotFoundExceptionRequest extends RuntimeException {

    public CarNotFoundExceptionRequest(Long id) {
        super("Could not find car with id: " + id);
    }

}
