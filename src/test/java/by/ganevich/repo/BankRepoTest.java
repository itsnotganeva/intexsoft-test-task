package by.ganevich.repo;

import by.ganevich.entity.Bank;
import org.junit.jupiter.api.Assertions;

import java.util.List;

public class BankRepoTest {

   // @Test
    public void testBankRepoInsertGet() {
        BankRepo instance = BankRepo.getInstance();

        Bank bank = new Bank();
        bank.setName("Alfa-bank");
        bank.setCommission(0.03);

        instance.save(bank);

        Bank bank2 = instance.getById(bank.getId());
        Assertions.assertEquals(bank, bank2);

        bank.setName("Belarus");

        instance.save(bank);

        Bank bankAfterUpdate = instance.getById(bank.getId());

        Assertions.assertEquals("Belarus", bankAfterUpdate.getName());

        List<Bank> banks = instance.findAll();

        Assertions.assertEquals(bank, banks.get(0));

        instance.deleteById(bank.getId());

        Assertions.assertNull(instance.getById(bank.getId()));

    }

}
