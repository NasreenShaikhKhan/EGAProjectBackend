package bankingsystem.backend.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    @NotBlank
    private String name;
    @NotBlank
    private String email;
    @NotBlank
    private String accountNo;
    @NotBlank
    private String pin;
}
