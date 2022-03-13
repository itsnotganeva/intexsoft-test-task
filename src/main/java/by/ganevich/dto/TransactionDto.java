package by.ganevich.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDto {

    @JsonProperty(value = "id")
    private Long id;

    @JsonProperty(value = "sender")
    private ClientDto sender;

    @JsonProperty(value = "receiver")
    private ClientDto receiver;

    @JsonProperty(value = "amountOfMoney")
    private Double amountOfMoney;

    @JsonProperty(value = "date")
    private Date date;

}
