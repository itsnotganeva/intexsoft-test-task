package by.ganevich.io.subinteractive;

import by.ganevich.entity.BankAccount;
import by.ganevich.entity.Client;
import by.ganevich.io.inputmanager.InputManager;
import by.ganevich.service.BankAccountService;
import by.ganevich.service.ClientService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@AllArgsConstructor
public class BankAccountMenuInteractive {

    private final BankAccountService bankAccountService;
    private final ClientService clientService;

    public void bankAccountMenuPrint() {
        System.out.println("1 - Read bank accounts of client");
        System.out.println("2 - Back");
    }

    public void readBankAccounts() {
        System.out.println("Enter the name of client of who you want to check all bank accounts: ");
        Client client = clientService.findClientByName(InputManager.inputString());

        Set<BankAccount> bankAccounts = bankAccountService.getAllAccountsOfClient(client);
        System.out.println(bankAccounts.toString());
    }

}
