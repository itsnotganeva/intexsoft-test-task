package by.ganevich.controller;

import by.ganevich.dto.BankDto;
import by.ganevich.entity.Bank;
import by.ganevich.mapper.interfaces.BankMapper;
import by.ganevich.service.BankService;
import by.ganevich.validator.CustomValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@Slf4j
@Tag(name = "Bank controller", description = "To manage banks")
public class BankController {

    private final BankService bankService;
    private final CustomValidator<BankDto> bankValidator;
    private final BankMapper bankMapper;


    @PostMapping(value = "/banks")
    @Operation(
            summary = "Bank creation",
            description = "Allows to create a new bank"
    )
    public ResponseEntity<?> create(
            @RequestBody @Parameter(description = "bank to be added to the database")
                    BankDto bankDto
    ) {
        log.info("REST: Create bank is called");
        Bank bank = bankMapper.toEntity(bankDto);
        if (!bankValidator.validateDto(bankDto)) {
            log.error("REST: Input of bank data is invalid");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        bankService.save(bank);
        log.info("REST: Bank was created successfully");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/banks")
    @Operation(
            summary = "Reading banks",
            description = "Allows to read all banks"
    )
    public ResponseEntity<List<BankDto>> read() {
        log.info("REST: Read banks is called");
        final List<Bank> banks = bankService.readAll();
        List<BankDto> banksDto = bankMapper.toDtoList(banks);

        log.info("REST: reading of banks was successful");
        return new ResponseEntity<>(banksDto, HttpStatus.OK);
    }

    @GetMapping(value = "/banks/{id}")
    @Operation(
            summary = "Reading bank",
            description = "Allows to read specific bank by id"
    )
    public ResponseEntity<BankDto> read(
            @PathVariable(name = "id") @Parameter(description = "id of bank") Long id
    ) {
        log.info("REST: Read bank with id" + id + " is called");
        final Optional<Bank> bank = bankService.findBankById(id);
        BankDto bankDto = bankMapper.toDto(bank.get());

        log.info("REST: Reading of bank with id" + id + " was successful");
        return bankDto != null
                ? new ResponseEntity<>(bankDto, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping(value = "/banks/{id}")
    @Operation(
            summary = "Bank update",
            description = "Allows to update specific bank by id"
    )
    public ResponseEntity<?> update(
            @PathVariable(name = "id") @Parameter(description = "id of bank to update") Long id,
            @RequestBody @Parameter(description = "updated bank") BankDto bankDto
    ) {
        log.info("REST: Update bank with id" + id + " is called");

        if (!bankValidator.validateDto(bankDto)) {
            log.info("REST: The new data of bank is invalid");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Bank bank = bankMapper.toEntity(bankDto);
        bankService.save(bank);
        log.info("REST: Bank with id" + id + " was updated successfully");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/banks/{id}")
    @Operation(
            summary = "Bank deletion",
            description = "Allows to delete specific bank by id"
    )
    public ResponseEntity<?> delete(
            @PathVariable(name = "id") @Parameter(description = "id of bank") Long id
    ) {
        log.info("REST: Delete bank with id" + id + " is called");
        bankService.deleteBankById(id);
        log.info("REST: Bank with id" + id + " was removed successfully");
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
