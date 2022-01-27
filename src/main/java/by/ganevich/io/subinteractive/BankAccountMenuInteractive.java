package by.ganevich.io.subinteractive;

import by.ganevich.entity.BankAccount;
import by.ganevich.entity.Client;
import by.ganevich.io.inputmanager.InputManager;
import by.ganevich.service.BankAccountService;
import by.ganevich.service.ClientService;

import java.util.List;

public class BankAccountMenuInteractive {

    public static void bankAccountMenuPrint() {
        System.out.println("1 - Read bank accounts of client");
        System.out.println("2 - Back");
    }

    public static void readBankAccounts() {
        System.out.println("Enter the name of client of who you want to check all bank accounts: ");
        Client client = ClientService.getInstance().findByName(InputManager.inputString());

        List<BankAccount> bankAccounts = BankAccountService.getInstance().getAllOfClient(client.getId());
        System.out.println(bankAccounts.toString());
    }

}
