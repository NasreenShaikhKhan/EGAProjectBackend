package bankingsystem.backend.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SecurityException extends RuntimeException{
    public SecurityException(String message) {
        super(message);
    }
}
