package by.ganevich.io.subinteractive;

import by.ganevich.entity.Bank;
import by.ganevich.io.inputmanager.InputManager;
import by.ganevich.service.BankService;

import java.util.List;

public class BankMenuInteractive {

    public static void bankMenuPrint() {
        System.out.println("1 - Create bank");
        System.out.println("2 - Read banks");
        System.out.println("3 - Update bank");
        System.out.println("4 - Delete bank");
        System.out.println("5 - Back");
    }

    public static void createBank() {
        Bank bank = new Bank();

        System.out.println("Enter the name of bank: ");
        bank.setName(InputManager.inputString());

        System.out.println("Enter the commission of bank (%): ");
        bank.setCommission(InputManager.inputDouble());

        BankService.getInstance().create(bank);
    }

    public static void readBank() {
        List<Bank> banks = BankService.getInstance().read();
        System.out.println(banks.toString());
    }

    public static void updateBank() {
        System.out.println("Enter the name of bank you want to update: ");
        Bank bank = BankService.getInstance().findByName(InputManager.inputString());

        System.out.println("Enter the new name of bank: ");
        bank.setName(InputManager.inputString());

        System.out.println("Enter the new commission of bank (%): ");
        bank.setCommission(InputManager.inputDouble());

        BankService.getInstance().update(bank);
    }

    public static void deleteBank() {
        System.out.println("Enter the name of bank you want to delete: ");
        Bank bank = BankService.getInstance().findByName(InputManager.inputString());

        BankService.getInstance().remove(bank.getId());

        System.out.println("Removed!");
    }
}
