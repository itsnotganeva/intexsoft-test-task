package by.ganevich.controller;

import by.ganevich.dto.TransactionDto;
import by.ganevich.entity.Transaction;
import by.ganevich.mapper.IMapper;
import by.ganevich.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.util.List;

@RestController
@AllArgsConstructor
@Tag(name = "Transaction controller", description = "To manage transactions")
public class TransactionController {

    private final TransactionService transactionService;

    private final IMapper<TransactionDto, Transaction> transactionMapper;

    @GetMapping(value = "/clients/{id}/transactions/{dateBefore}/{dateAfter}")
    @Operation(
            summary = "Reading transactions",
            description = "Allows to read all transactions of client by date"
    )
    public ResponseEntity<List<TransactionDto>> read(
            @PathVariable(name = "id") @Parameter(description = "id of client") Long id,
            @PathVariable(name = "dateBefore") @Parameter(description = "start date") String dateBefore,
            @PathVariable(name = "dateAfter") @Parameter(description = "final date") String dateAfter
    ) {
        final List<Transaction> transactions = transactionService
                .readAllByClientId(Date.valueOf(dateBefore), Date.valueOf(dateAfter), id);

        List<TransactionDto> transactionsDto
                = transactionMapper.listToDto(transactions, TransactionDto.class);

        return transactionsDto != null && !transactions.isEmpty()
                ? new ResponseEntity<>(transactionsDto, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/clients/transactions/"
            + "{senderAccountNumber}/{receiverAccountNumber}/{sum}")
    @Operation(
            summary = "Сonducting transactions",
            description = "Allows to conduct transaction"
    )
    public ResponseEntity<?> makeTransaction(
            @PathVariable(name = "senderAccountNumber")
            @Parameter(description = "number of sender's account") Integer senderAccountNumber,
            @PathVariable(name = "receiverAccountNumber")
            @Parameter(description = "number of receiver's account") Integer receiverAccountNumber,
            @PathVariable(name = "sum") @Parameter(description = "transfer amount") Double sum) {
        transactionService.sendMoney(senderAccountNumber, receiverAccountNumber, sum);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
