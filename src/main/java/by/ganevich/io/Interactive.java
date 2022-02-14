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

    @Autowired
    private ClientMenuInteractive clientMenuInteractive;

    @Autowired
    private BankAccountMenuInteractive bankAccountMenuInteractive;

    @Autowired
    private TransactionMenuInteractive transactionMenuInteractive;

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

    public void clientMenuChoice() {
        boolean isClientMenuShow = true;

        while (isClientMenuShow) {

            clientMenuInteractive.clientMenuPrint();

            switch (InputManager.inputInt()) {
                case 1:
                    clientMenuInteractive.createClient();
                    break;
                case 2:
                    clientMenuInteractive.readClients();
                    break;
                case 3:
                    clientMenuInteractive.updateClient();
                    break;
                case 4:
                    clientMenuInteractive.deleteClient();
                    break;
                case 5:
                    clientMenuInteractive.addClientToBank();
                    break;
                case 6:
                    clientMenuInteractive.sendMoneyToClient();
                    break;
                case 7:
                    isClientMenuShow = false;
                    break;
                default:
                    System.out.println("Wrong input!");

            }
        }
    }

    public void bankAccountMenuChoice() {
        boolean isBankAccountMenuShow = true;

        while (isBankAccountMenuShow) {

            bankAccountMenuInteractive.bankAccountMenuPrint();

            switch (InputManager.inputInt()) {
                case 1:
                    bankAccountMenuInteractive.readBankAccounts();
                    break;
                case 2:
                    isBankAccountMenuShow = false;
                    break;
                default:
                    System.out.println("Wrong input!");
            }
        }
    }

    public void transactionMenuChoice() {
        boolean isTransactionMenuShow = true;

        while (isTransactionMenuShow) {

            transactionMenuInteractive.transactionMenuPrint();

            switch (InputManager.inputInt()) {
                case 1:
                    transactionMenuInteractive.showTransactions();
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
