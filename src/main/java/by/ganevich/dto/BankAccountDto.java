package by.ganevich.dto;

import by.ganevich.entity.Currency;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BankAccountDto {

    private Long id;

    private Integer number;

    private Currency currency;

    private Double amountOfMoney;

    private ClientDto owner;

    @JsonProperty(value = "bank")
    private BankDto bankDto;
}
