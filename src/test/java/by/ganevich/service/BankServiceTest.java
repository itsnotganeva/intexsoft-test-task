package by.ganevich.service;

import by.ganevich.entity.Bank;
import by.ganevich.repository.BankRepository;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
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
        Bank bank = new Bank();
        bank.setName("Test");

        testBanks = new ArrayList<>();
        testBanks.add(bank);
    }

    @BeforeEach
    public void init()
    {
        bankService = new BankService(bankRepository);
    }


    @Test
    void save()
    {
        when(bankRepository.save(any(Bank.class))).thenReturn(testBanks.stream().findFirst().get());
        Bank resultBank = bankService.save(testBanks.stream().findFirst().get());

        Assert.assertNotNull(resultBank);
        Assert.assertTrue(resultBank.getName().equals("Test"));
    }

    @Test
    void readAll()
    {
        when(bankRepository.findAll()).thenReturn(testBanks);
        List<Bank> resultBanks = bankService.readAll();

        Assert.assertNotNull(resultBanks);
        Assert.assertEquals(testBanks, resultBanks);
    }

    @Test
    void findBankByName_Should_Return_Bank()
    {
        when(bankRepository.findByName(any(String.class))).thenReturn(testBanks.stream().findFirst().get());

        Bank resultBank = bankService.findBankByName(testBanks.stream().findFirst().get().getName());

        Assert.assertNotNull(resultBank);
        Assert.assertEquals(testBanks.stream().findFirst().get(), resultBank);
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
                .delete(testBanks.stream().findFirst().get());

        bankService
                .removeBank(testBanks.stream().findFirst().get());

        verify(bankRepository, times(1))
                .delete(testBanks.stream().findFirst().get());
    }
}
