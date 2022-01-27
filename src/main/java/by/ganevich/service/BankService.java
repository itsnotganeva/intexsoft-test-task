package by.ganevich.service;

import by.ganevich.entity.Bank;
import by.ganevich.repo.BankRepo;

import java.util.ArrayList;
import java.util.List;

public class BankService {

    public static BankService instance;

    public static BankService getInstance() {
        if (instance == null) {
            BankService.instance = new BankService();
        }
        return instance;
    }

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
        return banks.stream()
                .filter(b -> b.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No bank with given name"));
    }
}
