package by.ganevich.io.subinteractive;

import by.ganevich.entity.*;
import by.ganevich.io.inputmanager.InputManager;
import by.ganevich.service.BankAccountService;
import by.ganevich.service.BankService;
import by.ganevich.service.ClientService;
import by.ganevich.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.Date;
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
        System.out.println("6 - Make a transaction");
        System.out.println("7 - Back");
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

    public void deleteClient(){
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

        System.out.println("Enter the currency of bank account:");
        newBankAccount.setCurrency(InputManager.inputString());

        System.out.println("Enter the amount of money in bank account:");
        newBankAccount.setAmountOfMoney(InputManager.inputDouble());

        newBankAccount.setBankProducer(bank);
        newBankAccount.setAccountOwner(client);

        clientService.addClientToBank(client, bank);

        clientService.saveClient(client);

        bankAccountService.saveBankAccount(newBankAccount);

        System.out.println("Bank account was created!");
   }

   public void sendMoneyToClient() {

        System.out.println("Enter the name of the client from whose account the money will be sent: ");
        Client sender = clientService.findClientByName(InputManager.inputString());

        System.out.println("Enter the name of the bank of account from whose the money will be sent: ");
        Bank bankOfSender = bankService.findBankByName(InputManager.inputString());

        BankAccount bankAccount1 = bankAccountService.getAccountByClientAndBank(bankOfSender.getId(), sender.getId());

        System.out.println("Enter the name of the client to whose account the money will be sent: ");
        Client recipient = clientService.findClientByName(InputManager.inputString());

        System.out.println("Enter the name of the bank of account to whose the money will be sent: ");
        Bank bankOfRecipient = bankService.findBankByName(InputManager.inputString());

        BankAccount bankAccount2 = bankAccountService.getAccountByClientAndBank(bankOfRecipient.getId(), recipient.getId());

        System.out.println("Enter the sum of money that will be sent: ");
        Double sum = Double.parseDouble(InputManager.inputString());

        transactionService.saveTransaction(sum, sender, recipient);

        clientService.sendMoney(bankAccount1, bankAccount2, sum);
   }
}
