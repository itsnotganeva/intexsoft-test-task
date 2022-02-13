package by.ganevich.service;

import by.ganevich.entity.BankAccount;

import java.util.List;

public class BankAccountService {

    public static BankAccountService instance;

    public static BankAccountService getInstance() {
        if (instance == null) {
            BankAccountService.instance = new BankAccountService();
        }
        return instance;
    }

   // private final BankAccountRepo bankAccountRepo = BankAccountRepo.getInstance();

    public void create (BankAccount bankAccount) {
    //    bankAccountRepo.save(bankAccount);
    }

    public BankAccount getByClientAndBank(Long clientId, Long bankId) {
      //  return bankAccountRepo.getByClientAndBank(clientId, bankId);
        return null;
    }

    public List<BankAccount> getAllOfClient(Long clientId) {
       // return bankAccountRepo.getByClientId(clientId);
        return null;
    }

}
