package by.ganevich.io;

import by.ganevich.io.inputmanager.InputManager;
import by.ganevich.io.subinteractive.*;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


@Component
public class Interactive {

    @Autowired
    private BankMenuInteractive bankMenuInteractive;

    private boolean isShow = true;

    public void InvokeInteractiveMenu() {
        while (isShow) {
            try {
                mainMenuChoice();
            } catch (RuntimeException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public void mainMenuChoice() {

        MainMenuInteractive.mainMenuPrint();

        switch (InputManager.inputInt()) {
            case 1:
                bankMenuChoice();
                break;
//            case 2:
//                clientMenuChoice();
//                break;
//            case 3:
//                bankAccountMenuChoice();
//                break;
//            case 4:
//                transactionMenuChoice();
//                break;
            case 5:
                isShow = false;
                break;
            default:
                System.out.println("Wrong input!");
            }

    }

    public void bankMenuChoice() {
        boolean isBankMenuShow = true;

        while (isBankMenuShow) {

            bankMenuInteractive.bankMenuPrint();

            switch (InputManager.inputInt()) {
                case 1:
                    bankMenuInteractive.createBank();
                    break;
                case 2:
                    bankMenuInteractive.readBank();
                    break;
                case 3:
                    bankMenuInteractive.updateBank();
                    break;
                case 4:
                    bankMenuInteractive.deleteBank();
                    break;
                case 5:
                    isBankMenuShow = false;
                    break;
                default:
                    System.out.println("Wrong input!");

            }
        }
    }

//    public static void clientMenuChoice() {
//        boolean isClientMenuShow = true;
//
//        while (isClientMenuShow) {
//
//            ClientMenuInteractive.clientMenuPrint();
//
//            switch (InputManager.inputInt()) {
//                case 1:
//                    ClientMenuInteractive.createClient();
//                    break;
//                case 2:
//                    ClientMenuInteractive.readClients();
//                    break;
//                case 3:
//                    ClientMenuInteractive.updateClient();
//                    break;
//                case 4:
//                    ClientMenuInteractive.deleteClient();
//                    break;
//                case 5:
//                    ClientMenuInteractive.addClientToBank();
//                    break;
//                case 6:
//                    isClientMenuShow = false;
//                    break;
//                default:
//                    System.out.println("Wrong input!");
//
//            }
//        }
//    }
//
//    public static void bankAccountMenuChoice() {
//        boolean isBankAccountMenuShow = true;
//
//        while (isBankAccountMenuShow) {
//
//            BankAccountMenuInteractive.bankAccountMenuPrint();
//
//            switch (InputManager.inputInt()) {
//                case 1:
//                    BankAccountMenuInteractive.readBankAccounts();
//                    break;
//                case 2:
//                    isBankAccountMenuShow = false;
//                    break;
//                default:
//                    System.out.println("Wrong input!");
//            }
//        }
//    }
//
//    public static void transactionMenuChoice() {
//        boolean isTransactionMenuShow = true;
//
//        while (isTransactionMenuShow) {
//
//            TransactionMenuInteractive.transactionMenuPrint();
//
//            switch (InputManager.inputInt()) {
//                case 1:
//                    TransactionMenuInteractive.makeTransaction();
//                    break;
//                case 2:
//                    isTransactionMenuShow = false;
//                    break;
//                default:
//                    System.out.println("Wrong input!");
//            }
//        }
//    }
}
