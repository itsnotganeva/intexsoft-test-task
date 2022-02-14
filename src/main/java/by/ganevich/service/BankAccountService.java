package by.ganevich.service;

import by.ganevich.entity.BankAccount;
import by.ganevich.repository.BankAccountRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@AllArgsConstructor
@Slf4j
public class BankAccountService {

    private BankAccountRepository bankAccountRepository;

    public void saveBankAccount(BankAccount bankAccount) {
        bankAccountRepository.save(bankAccount);
    }

    public BankAccount getAccountByClientAndBank(Long bankId, Long clientId) {
        return bankAccountRepository.findBankAccountByBankIdAndClientId(bankId, clientId);
    }

    public Set<BankAccount> getAllAccountsOfClient(Long clientId) {
        return bankAccountRepository.findBankAccountByClientId(clientId);
    }

}
