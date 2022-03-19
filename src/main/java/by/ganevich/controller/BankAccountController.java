package by.ganevich.controller;

import by.ganevich.dto.BankAccountDto;
import by.ganevich.entity.BankAccount;
import by.ganevich.mapper.IMapper;
import by.ganevich.service.BankAccountService;
import by.ganevich.validator.CommandValidator;
import by.ganevich.validator.EntityValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Tag(name = "Bank account controller", description = "To manage bank accounts")
public class BankAccountController {

    private final BankAccountService bankAccountService;
    private final CommandValidator<BankAccount> bankAccountValidator;

    private final IMapper<BankAccountDto, BankAccount> bankAccountMapper;

    @PostMapping(value = "/bank-accounts")
    @Operation(
            summary = "Bank account creation",
            description = "Allows to create a new bank account"
    )
    public ResponseEntity<?> create(
            @RequestBody @Parameter(description = "bank account to be added to the database")
                    BankAccountDto bankAccountDto
    ) {
        BankAccount bankAccount = bankAccountMapper.toEntity(bankAccountDto);
        if (!bankAccountValidator.validateEntity(bankAccount)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        bankAccountService.saveBankAccount(bankAccount);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/clients/{id}/bank-accounts")
    @Operation(
            summary = "Reading bank accounts",
            description = "Allows to read all bank accounts of client"
    )
    public ResponseEntity<List<BankAccountDto>> read(
            @PathVariable(name = "id") @Parameter(description = "id of client") Long id
    ) {
        final List<BankAccount> bankAccounts = bankAccountService.findBankAccountByClientId(id);
        List<BankAccountDto> bankAccountsDto
                = bankAccountListMapper.toDtoList(bankAccounts);

        return new ResponseEntity<>(bankAccountsDto, HttpStatus.OK);
    }


}