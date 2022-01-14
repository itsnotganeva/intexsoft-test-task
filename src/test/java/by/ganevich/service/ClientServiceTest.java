package by.ganevich.service;

import by.ganevich.entity.Bank;
import by.ganevich.entity.BankAccount;
import by.ganevich.entity.Client;
import org.junit.jupiter.api.Assertions;

public class ClientServiceTest {

   //@Test
    public void testClientService() {

        ClientService clientService = new ClientService();


        Client client = new Client();
        client.setName("Matvey");
        //client.setType("individual");

        clientService.create(client);

        Client client2 = clientService.readById(client.getId());

        Assertions.assertEquals(client, client2);

        Bank bank = new Bank();
        bank.setName("Alfa");
        bank.setCommission(0.03);


        BankAccount bankAccount = new BankAccount();

        bankAccount.setCurrency("Dollars");
        bankAccount.setAmountOfMoney(500);
        bankAccount.setBankId(bank.getId());

        clientService.addToBank(client, bankAccount);

     //   Assertions.assertTrue(bank.getClients().contains(client));
    }
}