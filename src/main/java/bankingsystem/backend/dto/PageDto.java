package bankingsystem.backend.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageDto {
    List<TransactionDto> transactionList;
    String totalCount;
}
