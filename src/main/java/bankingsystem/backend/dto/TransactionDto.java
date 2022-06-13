package bankingsystem.backend.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionDto  {

    private String date;
    private BigDecimal amount;
    private String transactionId;
    private String type;




}
