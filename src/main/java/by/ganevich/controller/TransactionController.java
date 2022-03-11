package by.ganevich.controller;

import by.ganevich.entity.Transaction;
import by.ganevich.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.util.Set;

@RestController
@AllArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping(value = "/clients/{id}/transactions/{dateBefore}/{dateAfter}")
    public ResponseEntity<Set<Transaction>> read(@PathVariable(name = "id") Long id,
                                                 @PathVariable(name = "dateBefore") String dateBefore,
                                                 @PathVariable(name = "dateAfter") String dateAfter) {
        final Set<Transaction> transactions = transactionService
                .readAllByClientId(Date.valueOf(dateBefore), Date.valueOf(dateAfter), id);

        return transactions != null && !transactions.isEmpty()
                ? new ResponseEntity<>(transactions, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/clients/transactions/"
            + "{senderAccountNumber}/{receiverAccountNumber}/{sum}")
    public ResponseEntity<?> create(@PathVariable(name = "senderAccountNumber") Integer senderAccountNumber,
                                    @PathVariable(name = "receiverAccountNumber") Integer receiverAccountNumber,
                                    @PathVariable(name = "sum") Double sum) {
        transactionService.sendMoney(senderAccountNumber, receiverAccountNumber, sum);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
