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
public class BankService {

    private final BankRepository bankRepository;

    public Bank saveBank(Bank bank) {

        bankRepository.save(bank);
        log.info("Bank " + bank.getId() + " successfully created.");

        return bank;
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

    public Optional<Bank> findBankById(Long id) {
        return bankRepository.findById(id);
    }

    public void deleteBankById(Long id) {
        bankRepository.deleteById(id);
    }

}
