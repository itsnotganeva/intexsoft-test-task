package by.ganevich.io.subinteractive;

import by.ganevich.entity.Bank;
import by.ganevich.entity.BankAccount;
import by.ganevich.entity.Client;
import by.ganevich.entity.ClientType;
import by.ganevich.io.inputmanager.InputManager;
import by.ganevich.service.BankAccountService;
import by.ganevich.service.BankService;
import by.ganevich.service.ClientService;

import java.util.List;
import java.util.Objects;

public class ClientMenuInteractive {

    public static void clientMenuPrint() {
        System.out.println("1 - Create client");
        System.out.println("2 - Read clients");
        System.out.println("3 - Update client");
        System.out.println("4 - Delete client");
        System.out.println("5 - Add client to bank");
        System.out.println("6 - Back");
    }

    public static void createClient() {
        Client client = new Client();

        System.out.println("Enter the name of client: ");
        client.setName(InputManager.inputString());

        System.out.println("Enter the type of client:\n1 - Individual\n 2 - Industrial");
        Integer num = InputManager.inputInt();

        if (num.equals(1)) {
            client.setType(ClientType.INDIVIDUAL);
        } else if (num.equals(2)) {
            client.setType(ClientType.INDUSTRIAL);
        } else throw new RuntimeException("CHOOSE 1 OR 2 TO SET A TYPE!");

        ClientService.getInstance().create(client);
    }

    public static void readClients() {
        List<Client> clients = ClientService.getInstance().read();
        System.out.println(clients.toString());
    }

    public static void updateClient() {

        System.out.println("Enter the name of client you want to update: ");
        Client client = ClientService.getInstance().findByName(InputManager.inputString());

        System.out.println("Enter the new name of client: ");
        client.setName(InputManager.inputString());

        System.out.println("Enter the new type of client:\n1 - Individual\n2 - Industrial");
        Integer num = InputManager.inputInt();

        if (num.equals(1))
            client.setType(ClientType.INDIVIDUAL);
         else if (num.equals(2))
            client.setType(ClientType.INDUSTRIAL);
        else throw new RuntimeException("CHOOSE 1 OR 2 TO SET A TYPE!");

        ClientService.getInstance().update(client);
    }

    public static void deleteClient(){
        System.out.println("Enter the name of client you want to delete: ");
        Client client = ClientService.getInstance().findByName(InputManager.inputString());

        ClientService.getInstance().remove(client.getId());
        System.out.println("Removed!");
    }

    public static void addClientToBank() {
        System.out.println("Enter the name of client for whom you want to create a bank account: ");
        Client client = ClientService.getInstance().findByName(InputManager.inputString());

        System.out.println("Enter the name of bank in which you want to add client: ");
        Bank bank = BankService.getInstance().findByName(InputManager.inputString());

        BankAccount bankAccount = BankAccountService.getInstance().getByClientAndBank(client.getId(), bank.getId());

        if (Objects.isNull(bankAccount.getBankId())) {
            BankAccount newBankAccount = new BankAccount();

            System.out.println("Enter the currency of bank account:");
            newBankAccount.setCurrency(InputManager.inputString());

            System.out.println("Enter the amount of money in account:");
            newBankAccount.setAmountOfMoney(InputManager.inputDouble());

            newBankAccount.setBankId(bank.getId());
            newBankAccount.setClientId(client.getId());

            BankAccountService.getInstance().create(newBankAccount);

            ClientService.getInstance().addToBank(client, newBankAccount);

            System.out.println("Bank account was created!");
        }
        else throw new RuntimeException("Client already has an account of this bank!");
    }

}
