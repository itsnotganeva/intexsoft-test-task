package by.ganevich.io.commands;

import by.ganevich.entity.Bank;
import by.ganevich.io.CommandDescriptor;
import by.ganevich.service.BankService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
@AllArgsConstructor
@Getter
public class DeleteBankCommand implements ICommand{

    private final String commandName = "delete bank";

    private final BankService bankService;

    //delete bank Alfa
    @Override
    public Bank execute(CommandDescriptor commandDescriptor) {

        HashMap<Integer, String> parameters = commandDescriptor.getParameters();

        Bank bank = bankService.findBankByName(parameters.get(0));

        bankService.removeBank(bank);

        return null;
    }
}
