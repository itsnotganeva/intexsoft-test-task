package by.ganevich.controller;

import by.ganevich.dto.ConductTransactionDto;
import by.ganevich.dto.TransactionDto;
import by.ganevich.entity.Transaction;
import by.ganevich.mapper.interfaces.TransactionMapper;
import by.ganevich.service.TransactionService;
import by.ganevich.validator.CustomValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@Tag(name = "Transaction controller", description = "To manage transactions")
public class TransactionController {

    private final TransactionService transactionService;

    private final CustomValidator<ConductTransactionDto> transactionValidator;
    private final TransactionMapper transactionMapper;

    @GetMapping(value = "/clients/{id}/transactions/get")
    @Operation(
            summary = "Reading transactions",
            description = "Allows to read all transactions of client by date"
    )
    public ResponseEntity<List<TransactionDto>> read(
            @PathVariable(name = "id") @Parameter(description = "id of client") Long id,
            @RequestParam(name = "dateBefore") @Parameter(description = "start date") String dateBefore,
            @RequestParam(name = "dateAfter") @Parameter(description = "final date") String dateAfter
    ) {
        log.info("REST: Read transactions of client with id + " + id + " by date between " + dateBefore + " and + " + dateAfter + " is called");
        final List<Transaction> transactions = transactionService
                .readAllByClientId(Date.valueOf(dateBefore), Date.valueOf(dateAfter), id);

        List<TransactionDto> transactionsDto
                = transactionMapper.toDtoList(transactions);

        log.info("REST: Reading of transactions was successful");
        return transactionsDto != null && !transactions.isEmpty()
                ? new ResponseEntity<>(transactionsDto, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/transactions/add")
    @Operation(
            summary = "Сonducting transactions",
            description = "Allows to conduct transaction"
    )
    public ResponseEntity<?> makeTransaction(
            @RequestBody @Parameter(description = "dto data to conduct transaction")
                    ConductTransactionDto conductTransactionDto
    ) {
        log.info("REST: Make transaction is called");
        if (!transactionValidator.validateDto(conductTransactionDto)) {
            log.info("REST: The input data of transaction is invalid");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        transactionService.sendMoney(
                        Integer.valueOf(conductTransactionDto.getSenderAccountNumber()),
                        Integer.valueOf(conductTransactionDto.getReceiverAccountNumber()),
                        Double.valueOf(conductTransactionDto.getAmountOfMoney())
                );
        log.info("REST: Transaction was carried out successful");
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
