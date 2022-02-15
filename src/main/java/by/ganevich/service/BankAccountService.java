package by.ganevich.service;

import by.ganevich.entity.Bank;
import by.ganevich.entity.BankAccount;
import by.ganevich.entity.Client;
import by.ganevich.repository.BankAccountRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@AllArgsConstructor
@Slf4j
public class BankAccountService {

    private final BankAccountRepository bankAccountRepository;

    public void saveBankAccount(BankAccount bankAccount) {
        bankAccountRepository.save(bankAccount);
    }

    public BankAccount getAccountByClientAndBank(Client client, Bank bank) {
        return bankAccountRepository.findBankAccountByAccountOwnerAndBankProducer(client, bank);
    }

    public Set<BankAccount> getAllAccountsOfClient(Client client) {
        return bankAccountRepository.findBankAccountByAccountOwner(client);
    }

}
