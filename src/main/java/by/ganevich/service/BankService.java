package by.ganevich.service;

import by.ganevich.entity.Bank;
import by.ganevich.entity.BankAccount;
import by.ganevich.entity.Client;
import by.ganevich.repository.BankAccountRepository;
import by.ganevich.repository.BankRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Service;
//import by.ganevich.repo.BankRepo;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

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

    public void addClientToBank(Client client, Bank bank) {

        bank.getClients().add(client);
        client.getBanks().add(bank);

    }


}
