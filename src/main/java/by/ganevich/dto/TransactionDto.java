package by.ganevich.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDto {

    private Long id;

    @Pattern(regexp = "\\(?\\d+\\.\\d+\\)?")
    @NotEmpty(message = "Amount of money must not be empty")
    private String amountOfMoney;

    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$")
    @NotEmpty(message = "Date must not be empty")
    private Date date;

}
