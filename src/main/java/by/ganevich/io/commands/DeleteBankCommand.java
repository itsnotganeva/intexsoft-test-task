package by.ganevich.io.commands;

import by.ganevich.entity.Bank;
import by.ganevich.io.CommandDescriptor;
import by.ganevich.service.BankService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@AllArgsConstructor
@Getter
public class DeleteBankCommand implements ICommand{

    private final String commandName = "deleteBank";

    private final BankService bankService;

    @Override
    public Bank execute(CommandDescriptor commandDescriptor) {

        Map<String, String> parameters = commandDescriptor.getParameters();

        if (parameters.containsValue("help")){
            String help = "deleteBank bankName=?";
            System.out.println(help);
            return null;
        }

        Bank bank = bankService.findBankByName(parameters.get("bankName"));

        bankService.removeBank(bank);

        return null;
    }
}
