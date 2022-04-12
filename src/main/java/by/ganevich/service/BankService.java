package by.ganevich.service;

import by.ganevich.entity.Bank;
import by.ganevich.repository.BankRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class BankService implements BaseService<Bank>{

    private final BankRepository bankRepository;

    public void save(Bank bank) {
        log.info("Save of bankService is called");
        bankRepository.save(bank);
        log.info("Bank " + bank.getId() + " successfully created.");
    }

    public List<Bank> readAll() {
        log.info("ReadAll of bankService is called.");
        List<Bank> banks = bankRepository.findAll();
        log.info("The list of banks id successfully read.");
        return banks;
    }

    public Bank findBankByName(String name) {
        log.info("findBankByName of bankService is called");
        Bank bank = bankRepository.findByName(name);
        log.info("Bank " + bank.getId() + " successfully found");
        return bank;
    }

    public void removeBank(Bank bank) {
        log.info("removeBank of bankService is called");
        bankRepository.delete(bank);
        log.info("Bank " + bank.getId() + " successfully removed.");
    }

    public Optional<Bank> findBankById(Long id) {
        Optional<Bank> bank = bankRepository.findById(id);
        log.info("Bank " + bank.get().getId() + " successfully found");
        return  bank;
    }

    public void deleteBankById(Long id) {
        bankRepository.deleteById(id);
        log.info("Bank " + id + " successfully removed.");
    }

}
