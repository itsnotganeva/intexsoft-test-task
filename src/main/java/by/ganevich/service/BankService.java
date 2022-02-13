package by.ganevich.service;

import by.ganevich.entity.Bank;
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

    public void createBank(Bank bank) {
        bankRepository.save(bank);

        log.info("Bank " + bank.getId() + " successfully created.");
    }

    public List<Bank> readBanks() {
//        List<Bank> banks = bankRepository.
//        return banks;
        return null;
    }

  //  private final BankRepo bankRepo = BankRepo.getInstance();

    public void create(Bank bank) {
     //   bankRepo.save(bank);
    }

    public Bank readById(Long id) {
    //   return bankRepo.getById(id);
        return null;
    }

    public void remove(Long id) {
       // bankRepo.deleteById(id);
    }

    public void update(Bank bank) {
       // bankRepo.save(bank);
    }

    public List<Bank> read() {
      //  return bankRepo.findAll();
        return null;
    }

}
