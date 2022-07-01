package by.ganevich.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionToViewDto {

    @Pattern(regexp = "^sent$|^received$")
    @NotEmpty(message = "Type must not be empty")
    private String type;

    @Valid
    @NotNull(message = "Bank account with entered number not found!")
    private BankAccountDto bankAccountDto;

    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$")
    @NotEmpty(message = "DateBefore must not be empty")
    private String dateBefore;

    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$")
    @NotEmpty(message = "DateAfter must not be empty")
    private String dateAfter;

}
