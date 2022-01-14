package by.ganevich.service;

import by.ganevich.entity.BankAccount;
import by.ganevich.repo.BankAccountRepo;

import java.util.List;

public class BankAccountService {

    private final BankAccountRepo bankAccountRepo = BankAccountRepo.getInstance();

    public void create (BankAccount bankAccount) {
        bankAccountRepo.save(bankAccount);
    }

    public BankAccount getByClientAndBank(Long clientId, Long bankId) {
        return bankAccountRepo.getByClientAndBank(clientId, bankId);
    }

    public List<BankAccount> getAllOfClient(Long clientId) {
        return bankAccountRepo.getByClientId(clientId);
    }

}
