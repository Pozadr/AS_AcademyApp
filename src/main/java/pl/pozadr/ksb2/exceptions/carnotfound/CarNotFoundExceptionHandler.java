package pl.pozadr.ksb2.exceptions.carnotfound;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class CarNotFoundExceptionHandler {

    @ExceptionHandler(value = {CarNotFoundExceptionRequest.class})
    public ResponseEntity<Object> handleCarNotFoundException(CarNotFoundExceptionRequest ex) {
        HttpStatus badRequestStatus = HttpStatus.BAD_REQUEST;
        CarNotFoundException carNotFoundException = new CarNotFoundException(
                ex.getMessage(),
                badRequestStatus,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(carNotFoundException, badRequestStatus);
    }
}
