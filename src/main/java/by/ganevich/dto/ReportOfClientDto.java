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
public class ReportOfClientDto {

    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$")
    @NotEmpty(message = "DateBefore must not be empty")
    private String dateBefore;

    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$")
    @NotEmpty(message = "DateAfter must not be empty")
    private String dateAfter;

    @Pattern(regexp = "[A-Z][a-z]*", message = "Client name must start with a capital letter")
    @Size(min = 2, max = 25, message = "Name length must be between 2 and 25")
    @NotEmpty(message = "Client name must not be empty")
    private String clientName;

    @Pattern(regexp = "[A-Z][a-z]*", message = "Client surname must start with a capital letter")
    @Size(min = 2, max = 25, message = "Surname length must be between 2 and 25")
    @NotEmpty(message = "Client surname must not be empty")
    private String surname;
}
