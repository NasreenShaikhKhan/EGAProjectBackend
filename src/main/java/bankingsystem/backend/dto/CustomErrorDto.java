package bankingsystem.backend.dto;


/*********************************************************************************************************
 * 10/08/2021                          Nasreen Khan                        Inital Version
 ***********************************************************************************************************/
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@Builder
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class CustomErrorDto {

    int status;

    String message;


    public CustomErrorDto(int status, String message) {
        super();
        this.status = status;
        this.message = message;

    }

}