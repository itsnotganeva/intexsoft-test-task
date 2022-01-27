package by.ganevich.io;

import by.ganevich.io.inputmanager.InputManager;
import by.ganevich.io.subinteractive.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Interactive {

    private static Logger LOGGER = LoggerFactory.getLogger(Interactive.class);

    private static boolean isShow = true;

    public static void InvokeInteractiveMenu() {
        while (isShow) {
            try {
                mainMenuChoice();
            } catch (RuntimeException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public static void mainMenuChoice() {

        MainMenuInteractive.mainMenuPrint();

        switch (InputManager.inputInt()) {
            case 1:
                bankMenuChoice();
                break;
            case 2:
                clientMenuChoice();
                break;
            case 3:
                bankAccountMenuChoice();
                break;
            case 4:
                transactionMenuChoice();
                break;
            case 5:
                isShow = false;
                break;
            default:
                System.out.println("Wrong input!");
            }

    }

    public static void bankMenuChoice() {
        boolean isBankMenuShow = true;

        while (isBankMenuShow) {

            BankMenuInteractive.bankMenuPrint();

            switch (InputManager.inputInt()) {
                case 1:
                    BankMenuInteractive.createBank();
                    break;
                case 2:
                    BankMenuInteractive.readBank();
                    break;
                case 3:
                    BankMenuInteractive.updateBank();
                    break;
                case 4:
                    BankMenuInteractive.deleteBank();
                    break;
                case 5:
                    isBankMenuShow = false;
                    break;
                default:
                    System.out.println("Wrong input!");

            }
        }
    }

    public static void clientMenuChoice() {
        boolean isClientMenuShow = true;

        while (isClientMenuShow) {

            ClientMenuInteractive.clientMenuPrint();

            switch (InputManager.inputInt()) {
                case 1:
                    ClientMenuInteractive.createClient();
                    break;
                case 2:
                    ClientMenuInteractive.readClients();
                    break;
                case 3:
                    ClientMenuInteractive.updateClient();
                    break;
                case 4:
                    ClientMenuInteractive.deleteClient();
                    break;
                case 5:
                    ClientMenuInteractive.addClientToBank();
                    break;
                case 6:
                    isClientMenuShow = false;
                    break;
                default:
                    System.out.println("Wrong input!");

            }
        }
    }

    public static void bankAccountMenuChoice() {
        boolean isBankAccountMenuShow = true;

        while (isBankAccountMenuShow) {

            BankAccountMenuInteractive.bankAccountMenuPrint();

            switch (InputManager.inputInt()) {
                case 1:
                    BankAccountMenuInteractive.readBankAccounts();
                    break;
                case 2:
                    isBankAccountMenuShow = false;
                    break;
                default:
                    System.out.println("Wrong input!");
            }
        }
    }

    public static void transactionMenuChoice() {
        boolean isTransactionMenuShow = true;

        while (isTransactionMenuShow) {

            TransactionMenuInteractive.transactionMenuPrint();

            switch (InputManager.inputInt()) {
                case 1:
                    TransactionMenuInteractive.makeTransaction();
                    break;
                case 2:
                    isTransactionMenuShow = false;
                    break;
                default:
                    System.out.println("Wrong input!");
            }
        }
    }
}
