package by.ganevich.controller;

import by.ganevich.dto.BankAccountDto;
import by.ganevich.entity.Bank;
import by.ganevich.entity.BankAccount;
import by.ganevich.entity.Client;
import by.ganevich.entity.enums.ClientType;
import by.ganevich.entity.enums.Currency;
import by.ganevich.mapper.interfaces.BankAccountMapper;
import by.ganevich.service.BankAccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BankAccountControllerTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private BankAccountMapper bankAccountMapper;

    private MockMvc mvc;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }
    @MockBean
    private BankAccountService bankAccountService;

    private List<BankAccount> bankAccounts;

    @BeforeEach
    public void prepareTestData()
    {
        Client client = new Client();
        client.setName("Test");
        client.setType(ClientType.INDIVIDUAL);
        
        Bank bank = new Bank();
        bank.setName("Bank");
        
        bankAccounts = new ArrayList<>();
        
        BankAccount bankAccount = new BankAccount();
        bankAccount.setNumber(12345);
        bankAccount.setCurrency(Currency.BYN);
        bankAccount.setAmountOfMoney(3453.5);
        bankAccount.setOwner(client);
        bankAccount.setBankProducer(bank);

        bankAccounts.add(bankAccount);
    }

    @WithMockUser(authorities = "ROLE_CLIENT")
    @Test
    public void read_Should_Return_Bank_Accounts() throws Exception
    {
        when(bankAccountService.findBankAccountByClientId(any(Long.class))).thenReturn(bankAccounts);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/clients/1/bank-accounts").accept(MediaType.APPLICATION_JSON);

        List<BankAccountDto> bankAccountsDto = bankAccountMapper.toDtoList(bankAccounts);
        String expectedJson = new ObjectMapper().writeValueAsString(bankAccountsDto);

        MvcResult result = mvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson))
                .andReturn();
    }

    @WithMockUser(authorities = "ROLE_CLIENT")
    @Test
    public void create_Should_Return_Status_Ok() throws Exception
    {
        when(bankAccountService.save(any(BankAccount.class))).thenReturn(bankAccounts.stream().findFirst().get());

        BankAccountDto bankAccountDto = bankAccountMapper.toDto(bankAccounts.stream().findFirst().get());
        String expectedJson = new ObjectMapper().writeValueAsString(bankAccountDto);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/bank-accounts")
                .accept(MediaType.APPLICATION_JSON)
                .content(expectedJson)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @WithMockUser(authorities = "ROLE_CLIENT")
    @Test
    public void create_Should_Return_Status_Bad_Request() throws Exception
    {
        when(bankAccountService.save(any(BankAccount.class))).thenReturn(bankAccounts.stream().findFirst().get());

        BankAccount invalidAccount = bankAccounts.stream().findFirst().get();
        invalidAccount.setNumber(123);

        BankAccountDto bankAccountDto = bankAccountMapper.toDto(invalidAccount);
        String expectedJson = new ObjectMapper().writeValueAsString(bankAccountDto);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/bank-accounts")
                .accept(MediaType.APPLICATION_JSON)
                .content(expectedJson)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();
    }
}
