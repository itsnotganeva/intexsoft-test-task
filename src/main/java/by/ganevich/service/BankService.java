package by.ganevich.service;

import by.ganevich.entity.Bank;
import by.ganevich.entity.BankAccount;
import by.ganevich.repo.BankRepo;

import java.util.ArrayList;
import java.util.List;

public class BankService {

    private List<Bank> banks = new ArrayList<>();

    private final BankRepo bankRepo = BankRepo.getInstance();

    public void create(Bank bank) {
        bankRepo.save(bank);
    }

    public Bank readById(Long id) {
       return bankRepo.getById(id);
    }

    public void remove(Long id) {
        bankRepo.deleteById(id);
    }

    public void update(Bank bank) {
        bankRepo.save(bank);
    }

    public List<Bank> read() {
        return bankRepo.findAll();
    }

    public Bank findByName(String name) {
        banks = read();
        return banks.stream().filter(b -> b.getName().equals(name)).findFirst().get();
    }
}
