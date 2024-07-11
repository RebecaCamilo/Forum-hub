package api.forum.hub.config.exception;

import org.hibernate.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandlerConfig {

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity tratarErro404(ObjectNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
