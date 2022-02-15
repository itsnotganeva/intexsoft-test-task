package by.ganevich.service;

import by.ganevich.entity.Bank;
import by.ganevich.repository.BankRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class BankService {

    private BankRepository bankRepository;

    public void saveBank(Bank bank) {
        bankRepository.save(bank);

        log.info("Bank " + bank.getId() + " successfully created.");
    }

    public List<Bank> readBanks() {
        List<Bank> banks = bankRepository.findAll();
        return banks;
    }

    public Bank findBankByName(String name) {
        Bank bank = bankRepository.findByName(name);
        return bank;
    }

    public void removeBank(Bank bank) {
        bankRepository.delete(bank);
    }

}
