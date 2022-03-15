package by.ganevich.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConductTransactionDto {

    private Integer senderAccountNumber;

    private Integer receiverAccountNumber;

    private Double amountOfMoney;

}
