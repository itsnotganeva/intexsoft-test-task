package by.ganevich.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDto {

    private Long id;

    private ClientDto sender;

    private ClientDto receiver;

    private Double amountOfMoney;

    private Date date;

}
