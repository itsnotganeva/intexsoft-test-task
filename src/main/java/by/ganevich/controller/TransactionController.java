package by.ganevich.controller;

import by.ganevich.dto.ConductTransactionDto;
import by.ganevich.dto.TransactionDto;
import by.ganevich.entity.Transaction;
import by.ganevich.mapper.interfaces.TransactionListMapperImpl;
import by.ganevich.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@RestController
@AllArgsConstructor
@Tag(name = "Transaction controller", description = "To manage transactions")
public class TransactionController {

    private final TransactionService transactionService;

    private final TransactionListMapperImpl transactionListMapper;

    @GetMapping(value = "/clients/{id}/transactions")
    @Operation(
            summary = "Reading transactions",
            description = "Allows to read all transactions of client by date"
    )
    public ResponseEntity<List<TransactionDto>> read(
            @PathVariable(name = "id") @Parameter(description = "id of client") Long id,
            @RequestParam(name = "dateBefore") @Parameter(description = "start date") String dateBefore,
            @RequestParam(name = "dateAfter") @Parameter(description = "final date") String dateAfter
    ) {
        final List<Transaction> transactions = transactionService
                .readAllByClientId(Date.valueOf(dateBefore), Date.valueOf(dateAfter), id);

        List<TransactionDto> transactionsDto
                = transactionListMapper.toDtoList(transactions);

        return transactionsDto != null && !transactions.isEmpty()
                ? new ResponseEntity<>(transactionsDto, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/transactions")
    @Operation(
            summary = "Сonducting transactions",
            description = "Allows to conduct transaction"
    )
    public ResponseEntity<?> makeTransaction(
            @RequestBody @Parameter(description = "dto data to conduct transaction")
                    ConductTransactionDto conductTransactionDto
    ) {
        transactionService.sendMoney(
                        conductTransactionDto.getSenderAccountNumber(),
                        conductTransactionDto.getReceiverAccountNumber(),
                        conductTransactionDto.getAmountOfMoney()
                );
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
