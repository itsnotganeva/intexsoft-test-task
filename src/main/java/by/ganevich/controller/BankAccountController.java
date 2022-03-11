package by.ganevich.controller;

import by.ganevich.entity.BankAccount;
import by.ganevich.service.BankAccountService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@AllArgsConstructor
public class BankAccountController {

    private final BankAccountService bankAccountService;

    @PostMapping(value = "/bankAccounts")
    public ResponseEntity<?> create(@RequestBody BankAccount bankAccount) {
        bankAccountService.saveBankAccount(bankAccount);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value = "/clients/{id}/bankAccounts")
    public ResponseEntity<Set<BankAccount>> read(@PathVariable(name = "id") Long id) {
        final Set<BankAccount> bankAccounts = bankAccountService.findBankAccountByClientId(id);

        return bankAccounts != null && !bankAccounts.isEmpty()
                ? new ResponseEntity<>(bankAccounts, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


}