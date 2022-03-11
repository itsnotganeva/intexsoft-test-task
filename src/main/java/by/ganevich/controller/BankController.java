package by.ganevich.controller;

import by.ganevich.entity.Bank;
import by.ganevich.service.BankService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
public class BankController {

    private final BankService bankService;

    @PostMapping(value = "/banks")
    public ResponseEntity<?> create(@RequestBody Bank bank) {
        bankService.saveBank(bank);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value = "/banks")
    public ResponseEntity<List<Bank>> read() {
        final List<Bank> banks = bankService.readBanks();

        return banks != null && !banks.isEmpty()
                ? new ResponseEntity<>(banks, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/banks/{id}")
    public ResponseEntity<Optional<Bank>> read(@PathVariable(name = "id") Long id) {
        final Optional<Bank> bank = bankService.findBankById(id);

        return bank != null
                ? new ResponseEntity<>(bank, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping(value = "/banks/{id}")
    public ResponseEntity<?> update(@PathVariable(name = "id") Long id, @RequestBody Bank bank) {
        bankService.saveBank(bank);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/banks/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") Long id) {
        bankService.deleteBankById(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
