package by.ganevich.io.subinteractive;

import by.ganevich.entity.*;
import by.ganevich.io.inputmanager.InputManager;
import by.ganevich.service.BankAccountService;
import by.ganevich.service.BankService;
import by.ganevich.service.ClientService;
import by.ganevich.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class ClientMenuInteractive {

    private ClientService clientService;
    private BankService bankService;
    private BankAccountService bankAccountService;
    private TransactionService transactionService;

    public void clientMenuPrint() {
        System.out.println("1 - Create client");
        System.out.println("2 - Read clients");
        System.out.println("3 - Update client");
        System.out.println("4 - Delete client");
        System.out.println("5 - Add client to bank");
        System.out.println("6 - Back");
    }

    public void createClient() {
        Client client = new Client();

        System.out.println("Enter the name of client: ");
        client.setName(InputManager.inputString());

        System.out.println("Enter the type of client:\n1 - Individual \n2 - Industrial");
        Integer num = InputManager.inputInt();

        if (num.equals(1)) {
            client.setType(ClientType.INDIVIDUAL);
        } else if (num.equals(2)) {
            client.setType(ClientType.INDUSTRIAL);
        } else throw new RuntimeException("CHOOSE 1 OR 2 TO SET A TYPE!");

        clientService.saveClient(client);
    }

    public void readClients() {
        List<Client> clients = clientService.readClients();
        System.out.println(clients.toString());
    }

    public void updateClient() {

        System.out.println("Enter the name of client you want to update: ");
        Client client = clientService.findClientByName(InputManager.inputString());

        System.out.println("Enter the new name of client: ");
        client.setName(InputManager.inputString());

        System.out.println("Enter the new type of client:\n1 - Individual\n2 - Industrial");
        Integer num = InputManager.inputInt();

        if (num.equals(1))
            client.setType(ClientType.INDIVIDUAL);
        else if (num.equals(2))
            client.setType(ClientType.INDUSTRIAL);
        else throw new RuntimeException("CHOOSE 1 OR 2 TO SET A TYPE!");

        clientService.saveClient(client);
    }

    public void deleteClient() {
        System.out.println("Enter the name of client you want to delete: ");
        Client client = clientService.findClientByName(InputManager.inputString());

        clientService.removeClient(client);

        System.out.println("Removed!");
    }

    public void addClientToBank() {
        System.out.println("Enter the name of the client you want to add to the bank: ");
        Client client = clientService.findClientByName(InputManager.inputString());

        System.out.println("Enter the name of bank in which you want to add client: ");
        Bank bank = bankService.findBankByName(InputManager.inputString());

        BankAccount newBankAccount = new BankAccount();

        System.out.println("Enter the currency of bank account:" +
                " \n1 - USD \n2 - EUR \n3 - BYN");
        Integer num = InputManager.inputInt();
        if (num.equals(1))
            newBankAccount.setCurrency(Currency.USD);
        else if (num.equals(2))
            newBankAccount.setCurrency(Currency.EUR);
        else if (num.equals(3))
            newBankAccount.setCurrency(Currency.BYN);
        else throw new RuntimeException("CHOOSE NUMBER FROM 1 TO 4 TO SET A CURRENCY!");

        System.out.println("Enter the amount of money in bank account:");
        newBankAccount.setAmountOfMoney(InputManager.inputDouble());

        newBankAccount.setAccountOwner(client);
        newBankAccount.setBankProducer(bank);

        clientService.saveClient(client);
        bankService.saveBank(bank);

        bankAccountService.saveBankAccount(newBankAccount);

        System.out.println("Bank account was created!");
    }

}
