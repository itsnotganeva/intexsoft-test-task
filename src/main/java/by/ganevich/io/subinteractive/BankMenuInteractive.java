package by.ganevich.io.subinteractive;

import by.ganevich.entity.Bank;
import by.ganevich.entity.ClientType;
import by.ganevich.entity.Commission;
import by.ganevich.io.inputmanager.InputManager;
import by.ganevich.service.BankService;
import by.ganevich.service.CommissionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class BankMenuInteractive {

    private final BankService bankService;
    private final CommissionService commissionService;

    public void bankMenuPrint() {
        System.out.println("1 - Create bank");
        System.out.println("2 - Read banks");
        System.out.println("3 - Update bank");
        System.out.println("4 - Delete bank");
        System.out.println("5 - Back");
    }

    public void createBank() {
        Bank bank = new Bank();

        System.out.println("Enter the name of bank: ");
        bank.setName(InputManager.inputString());

        bankService.saveBank(bank);


        System.out.println("Enter the commission of bank for individual client: ");

        Commission individualCommission = new Commission();
        individualCommission.setClientType(ClientType.INDIVIDUAL.ordinal());
        individualCommission.setCommission(InputManager.inputDouble());
        individualCommission.setBank(bank);
        commissionService.saveCommission(individualCommission);

        System.out.println("Enter the commission of bank for industrial client: ");

        Commission industrialCommission = new Commission();
        industrialCommission.setClientType(ClientType.INDUSTRIAL.ordinal());
        industrialCommission.setCommission(InputManager.inputDouble());
        industrialCommission.setBank(bank);
        commissionService.saveCommission(industrialCommission);

    }

    public void readBank() {
        List<Bank> banks = bankService.readBanks();
        System.out.println(banks.toString());
    }

    public void updateBank() {
        System.out.println("Enter the name of bank you want to update: ");
        Bank bank = bankService.findBankByName(InputManager.inputString());

        System.out.println("Enter the new name of bank: ");
        bank.setName(InputManager.inputString());

        bankService.saveBank(bank);

        System.out.println("Enter the new commission of bank for individual client: ");

        Commission individualCommission = commissionService.findByBankAndClientType(bank, ClientType.INDIVIDUAL);
        individualCommission.setClientType(ClientType.INDIVIDUAL.ordinal());
        individualCommission.setCommission(InputManager.inputDouble());
        individualCommission.setBank(bank);
        commissionService.saveCommission(individualCommission);

        System.out.println("Enter the new commission of bank for industrial client: ");

        Commission industrialCommission = commissionService.findByBankAndClientType(bank, ClientType.INDUSTRIAL);
        industrialCommission.setClientType(ClientType.INDUSTRIAL.ordinal());
        industrialCommission.setCommission(InputManager.inputDouble());
        industrialCommission.setBank(bank);
        commissionService.saveCommission(industrialCommission);

    }

    public void deleteBank() {
        System.out.println("Enter the name of bank you want to delete: ");
        Bank bank = bankService.findBankByName(InputManager.inputString());

        bankService.removeBank(bank);

        System.out.println("Removed!");
    }
}
