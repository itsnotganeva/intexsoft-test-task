package by.ganevich.io.commands;

import by.ganevich.entity.Bank;
import by.ganevich.io.CommandDescriptor;
import by.ganevich.io.CommandResult;
import by.ganevich.service.BankService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
@Getter
public class ReadBanksCommand implements ICommand {

    private final String commandName = "readBanks";

    private final BankService bankService;

    @Override
    public CommandResult execute(CommandDescriptor commandDescriptor) {

        List<Bank> banks = bankService.readBanks();

        CommandResult commandResult = new CommandResult();
        commandResult.setResult(banks);
        return commandResult;
    }
}
