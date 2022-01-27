package by.ganevich.io.subinteractive;

import by.ganevich.entity.Bank;
import by.ganevich.entity.BankAccount;
import by.ganevich.entity.Client;
import by.ganevich.io.inputmanager.InputManager;
import by.ganevich.service.BankAccountService;
import by.ganevich.service.BankService;
import by.ganevich.service.ClientService;

public class TransactionMenuInteractive {

    public static void transactionMenuPrint() {
        System.out.println("1 - Make transaction");
        System.out.println("2 - Back");
    }

    public static void makeTransaction() {

        System.out.println("Enter the name of the client from whose account the money will be sent: ");
        Client client1 = ClientService.getInstance().findByName(InputManager.inputString());

        System.out.println("Enter the name of the bank of account from whose the money will be sent: ");
        Bank bank1 = BankService.getInstance().findByName(InputManager.inputString());

        BankAccount bankAccount1 = BankAccountService.getInstance().getByClientAndBank(client1.getId(), bank1.getId());

        System.out.println("Enter the name of the client to whose account the money will be sent: ");
        Client client2 = ClientService.getInstance().findByName(InputManager.inputString());

        System.out.println("Enter the name of the bank of account to whose the money will be sent: ");
        Bank bank2 = BankService.getInstance().findByName(InputManager.inputString());

        BankAccount bankAccount2 = BankAccountService.getInstance().getByClientAndBank(client2.getId(), bank2.getId());

        System.out.println("Enter the sum of money that will be sent: ");
        Double sum = Double.parseDouble(InputManager.inputString());

        ClientService.getInstance().sendMoney(bankAccount1, bankAccount2, sum);

    }

}
