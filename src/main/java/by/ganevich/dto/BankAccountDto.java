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

    @JsonProperty(value = "id")
    private Long id;

    @JsonProperty(value = "number")
    private Integer number;

    @JsonProperty(value = "currency")
    private Currency currency;

    @JsonProperty(value = "amountOfMoney")
    private Double amountOfMoney;

    @JsonProperty(value = "owner")
    private ClientDto owner;

    @JsonProperty(value = "bank")
    private BankDto bankDto;
}
