package by.ganevich.controller;

import by.ganevich.dto.BankAccountDto;
import by.ganevich.entity.BankAccount;
import by.ganevich.mapper.interfaces.BankAccountMapper;
import by.ganevich.service.BankAccountService;
import by.ganevich.validator.CustomValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@Tag(name = "Bank account controller", description = "To manage bank accounts")
public class BankAccountController {

    private final BankAccountService bankAccountService;
    private final CustomValidator<BankAccountDto> bankAccountValidator;
    private final BankAccountMapper bankAccountMapper;

    @PreAuthorize("hasAnyRole('ROLE_OPERATOR', 'ROLE_CLIENT')")
    @PostMapping(value = "/bank-accounts")
    @Operation(
            summary = "Bank account creation",
            description = "Allows to create a new bank account"
    )
    public ResponseEntity<?> create(
            @RequestBody @Parameter(description = "bank account to be added to the database")
                    BankAccountDto bankAccountDto
    ) {
        log.info("REST: Create bank account is called");
        if (!bankAccountValidator.validateDto(bankAccountDto)) {
            log.error("REST: input of bank data is invalid");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        BankAccount bankAccount = bankAccountMapper.toEntity(bankAccountDto);
        bankAccountService.save(bankAccount);
        log.info("REST: bank is created successfully");
        return new ResponseEntity<>(HttpStatus.OK);
    }

//    @PreAuthorize("hasAnyRole('ROLE_OPERATOR', 'ROLE_CLIENT', 'ROLE_ADMIN')")
    @GetMapping(value = "/clients/{id}/bank-accounts")
    @Operation(
            summary = "Reading bank accounts",
            description = "Allows to read all bank accounts of client"
    )
    public ResponseEntity<List<BankAccountDto>> read(
            @PathVariable(name = "id") @Parameter(description = "id of client") Long id
    ) {
        log.info("REST: Read bank accounts of client " + id + " is called");
        final List<BankAccount> bankAccounts = bankAccountService.findBankAccountByClientId(id);
        List<BankAccountDto> bankAccountsDto
                = bankAccountMapper.toDtoList(bankAccounts);

        log.info("REST: Reading of accounts was successful");
        return new ResponseEntity<>(bankAccountsDto, HttpStatus.OK);
    }


}