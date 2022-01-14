package by.ganevich;

import by.ganevich.entity.Bank;
import by.ganevich.entity.BankAccount;
import by.ganevich.entity.Client;
import by.ganevich.entity.ClientType;

import by.ganevich.service.BankAccountService;
import by.ganevich.service.BankService;
import by.ganevich.service.ClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Interactive {

    private static final Logger LOGGER = LoggerFactory.getLogger(Interactive.class);

    private static final BankService bankService = new BankService();
    private static final ClientService clientService = new ClientService();
    private static final BankAccountService bankAccountService = new BankAccountService();



    public Interactive() {
        showMenu();
    }

    public static void menu(){
        System.out.println("Choose the menu number: ");
        System.out.println("1 - Create bank \t 5 - Create client \t\t 9 - Create bank account for client");
        System.out.println("2 - Read banks \t\t 6 - Read clients \t\t 10 - Read bank accounts of client");
        System.out.println("3 - Update bank \t 7 - Update client \t\t 11 - Make a transaction");
        System.out.println("4 - Delete bank \t 8 - Delete client \t\t 12 - Exit");
    }

    public static void showMenu() {
        Scanner in = new Scanner(System.in);
        int num = 0;
        String input = "";
        menu();

        do {
            input = in.next();
            try {
                num = Integer.parseInt(input);
            } catch (Exception ex) {
                LOGGER.error("Wrong input! Please try again!");
            }

            switch (num) {
                case 1:
                    createBank();
                    break;
                case 2:
                    readBank();
                    break;
                case 3:
                    updateBank();
                    break;
                case 4:
                    deleteBank();
                    break;
                case 5:
                    createClient();
                    break;
                case 6:
                    readClients();
                    break;
                case 7:
                    updateClient();
                    break;
                case 8:
                    deleteClient();
                    break;
                case 9:
                    addClientToBank();
                    break;
                case 10:
                    readBankAccounts();
                    break;
                case 11:
                    makeTransaction();
                    break;
            }

        } while (!input.equals("12"));
    }

    public static void createBank() {
        Scanner in = new Scanner(System.in);
        Bank bank = new Bank();
        System.out.println("Enter the name of bank: ");
        bank.setName(in.nextLine());
        System.out.println("Enter the commission of bank (%): ");
        bank.setCommission(in.nextDouble());
        bankService.create(bank);
    }

    public static void readBank() {
        List<Bank> banks = bankService.read();
        System.out.println(banks.toString());
    }

    public static void updateBank() {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter the name of bank you want to update: ");
        Bank bank = bankService.findByName(in.nextLine());
        System.out.println("Enter the new name of bank: ");
        bank.setName(in.nextLine());
        System.out.println("Enter the new commission of bank (%): ");
        bank.setCommission(in.nextDouble());
        bankService.update(bank);
    }

    public static void deleteBank() {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter the name of bank you want to delete: ");
        Bank bank = bankService.findByName(in.nextLine());
        bankService.remove(bank.getId());
        System.out.println("Removed!");
    }

    public static void createClient() {
        Scanner in = new Scanner(System.in);
        Client client = new Client();
        System.out.println("Enter the name of client: ");
        client.setName(in.nextLine());
        System.out.println("Enter the type of client:\n1 - Individual\n 2 - Industrial");
        int num = in.nextInt();
        if (num == 1) {
            client.setType(ClientType.INDIVIDUAL);
        } else if (num == 2) {
            client.setType(ClientType.INDUSTRIAL);
        } else throw new RuntimeException("CHOOSE 1 OR 2 TO SET A TYPE!");

        clientService.create(client);
    }

    public static void readClients() {
        List<Client> clients = clientService.read();
        System.out.println(clients.toString());
    }

    public static void updateClient() {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter the name of client you want to update: ");
        Client client = clientService.findByName(in.nextLine());
        System.out.println("Enter the new name of client: ");
        client.setName(in.nextLine());
        System.out.println("Enter the new type of client:\n1 - Individual\n2 - Industrial");
        int num = in.nextInt();
        if (num == 1) {
            client.setType(ClientType.INDIVIDUAL);
        } else if (num == 2) {
            client.setType(ClientType.INDUSTRIAL);
        } else throw new RuntimeException("CHOOSE 1 OR 2 TO SET A TYPE!");

        clientService.update(client);
    }

    public static void deleteClient(){
        Scanner in = new Scanner(System.in);
        System.out.println("Enter the name of client you want to delete: ");
        Client client = clientService.findByName(in.nextLine());
        clientService.remove(client.getId());
        System.out.println("Removed!");
    }

    public static void addClientToBank() {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter the name of client for whom you want to create a bank account: ");
        Client client = clientService.findByName(in.nextLine());
        System.out.println("Enter the name of bank in which you want to add client: ");
        Bank bank = bankService.findByName(in.nextLine());

        BankAccount bankAccount = bankAccountService.getByClientAndBank(client.getId(), bank.getId());
        if (bankAccount.getBankId() == null) {
            BankAccount bankAccount1 = new BankAccount();
            System.out.println("Enter the currency of bank account:");
            bankAccount1.setCurrency(in.nextLine());
            System.out.println("Enter the amount of money in account:");
            bankAccount1.setAmountOfMoney(in.nextDouble());
            bankAccount1.setBankId(bank.getId());
            bankAccount1.setClientId(client.getId());
            bankAccountService.create(bankAccount1);
            clientService.addToBank(client, bankAccount1);
            System.out.println("Bank account was created!");
        }
        else throw new RuntimeException("Client already has an account of this bank!");
    }

    public static void readBankAccounts() {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter the name of client of who you want to check all bank accounts: ");
        Client client = clientService.findByName(in.nextLine());
        List<BankAccount> bankAccounts = bankAccountService.getAllOfClient(client.getId());
        System.out.println(bankAccounts.toString());
    }

    public static void makeTransaction() {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter the name of the client from whose account the money will be sent: ");
        Client client1 = clientService.findByName(in.nextLine());
        System.out.println("Enter the name of the bank of account from whose the money will be sent: ");
        Bank bank1 = bankService.findByName(in.nextLine());
        BankAccount bankAccount1 = bankAccountService.getByClientAndBank(client1.getId(), bank1.getId());

        System.out.println("Enter the name of the client to whose account the money will be sent: ");
        Client client2 = clientService.findByName(in.nextLine());
        System.out.println("Enter the name of the bank of account to whose the money will be sent: ");
        Bank bank2 = bankService.findByName(in.nextLine());
        BankAccount bankAccount2 = bankAccountService.getByClientAndBank(client2.getId(), bank2.getId());
        System.out.println("Enter the sum of money that will be sent: ");
        Double sum = in.nextDouble();
        clientService.sendMoney(bankAccount1, bankAccount2, sum);

    }

}
