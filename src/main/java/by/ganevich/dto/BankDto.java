package by.ganevich.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BankDto {

    private Long id;

    private String name;

    @JsonProperty(value = "commissions")
    private Set<CommissionDto> commissionsDto;
}
