package by.ganevich.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportOfAccountDto {

    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$")
    @NotEmpty(message = "DateBefore must not be empty")
    private String dateBefore;

    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$")
    @NotEmpty(message = "DateAfter must not be empty")
    private String dateAfter;

    @Size(min = 5, max = 5)
    @NotEmpty(message = "Account number must not be empty")
    private String accountNumber;
}
