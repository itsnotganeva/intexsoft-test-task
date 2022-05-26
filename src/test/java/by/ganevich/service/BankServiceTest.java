package by.ganevich.service;

import by.ganevich.entity.Bank;
import by.ganevich.entity.Commission;
import by.ganevich.repository.BankRepository;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BankServiceTest {

    @Mock
    private BankRepository bankRepository;

    private BankService bankService;

    private static List<Bank> testBanks;

    @BeforeEach
    public void prepareTestData()
    {
        Commission individual = new Commission();
        individual.setId(1l);
        individual.setCommission(0.03);
        individual.setClientType(0);

        Commission industrial = new Commission();
        industrial.setId(2l);
        industrial.setCommission(0.04);
        industrial.setClientType(1);

        Bank bank = new Bank();
        bank.setId(1l);
        bank.setName("Test");
        bank.setCommissions(new HashSet<>());
        bank.getCommissions().add(individual);
        bank.getCommissions().add(industrial);

        testBanks = new ArrayList<>();
        testBanks.add(bank);
    }
    private Bank getBank(List<Bank> banks) {
        return testBanks.stream().findFirst().get();
    }

    @BeforeEach
    public void init()
    {
        bankService = new BankService(bankRepository);
    }

    @Test
    void save_Should_Return_Bank()
    {
        when(bankRepository.save(any(Bank.class))).thenReturn(getBank(testBanks));
        Bank resultBank = bankService.save(getBank(testBanks));

        Assert.assertNotNull(resultBank);
        Assert.assertEquals(getBank(testBanks), resultBank);
        Assert.assertEquals(getBank(testBanks).getName(), resultBank.getName());
        Assert.assertEquals(getBank(testBanks).getCommissions(), resultBank.getCommissions());
    }

    @Test
    void readAll_Should_Return_Banks()
    {
        when(bankRepository.findAll()).thenReturn(testBanks);
        List<Bank> resultBanks = bankService.readAll();

        Assert.assertNotNull(resultBanks);
        Assert.assertEquals(testBanks, resultBanks);
    }

    @Test
    void findBankByName_Should_Return_Bank()
    {
        when(bankRepository.findByName(any(String.class))).thenReturn(getBank(testBanks));

        Bank resultBank = bankService.findBankByName(getBank(testBanks).getName());

        Assert.assertNotNull(resultBank);
        Assert.assertEquals(getBank(testBanks), resultBank);
        Assert.assertEquals(getBank(testBanks).getName(), resultBank.getName());
        Assert.assertEquals(getBank(testBanks).getCommissions(), resultBank.getCommissions());
    }

    @Test
    public void findBankByName_Should_Return_Null()
    {
        when(bankRepository.findByName("invalid")).thenReturn(null);
        Bank resultBank = bankService.findBankByName("invalid");

        Assert.assertTrue(resultBank == null);
    }

    @Test
    public void removeBank()
    {
        doNothing().when(bankRepository)
                .delete(getBank(testBanks));

        bankService
                .removeBank(getBank(testBanks));

        verify(bankRepository, times(1))
                .delete(getBank(testBanks));
    }
}
