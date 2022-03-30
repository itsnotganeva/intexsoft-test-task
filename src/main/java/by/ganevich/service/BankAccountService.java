package by.ganevich.service;

import by.ganevich.entity.Bank;
import by.ganevich.entity.BankAccount;
import by.ganevich.entity.Client;
import by.ganevich.repository.BankAccountRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class BankAccountService implements BaseService<BankAccount> {

    private final BankAccountRepository bankAccountRepository;

    public void save(BankAccount bankAccount) {
        bankAccountRepository.save(bankAccount);
    }

    public BankAccount getAccountByClientAndBank(Client client, Bank bank) {
        return bankAccountRepository.findBankAccountByOwnerAndBankProducer(client, bank);
    }

    public Set<BankAccount> getAllAccountsOfClient(Client client) {
        return bankAccountRepository.findBankAccountByOwner(client);
    }

    public List<BankAccount> findBankAccountByClientId(Long id) {
        return bankAccountRepository.findBankAccountByOwnerId(id);
    }

    public BankAccount findBankAccountByNumber(Integer number) {
        return bankAccountRepository.findBankAccountByNumber(number);
    }

    public List<BankAccount> readAll() {
        return bankAccountRepository.findAll();
    }
}
