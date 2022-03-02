package by.ganevich.io.commands;

import by.ganevich.entity.Bank;
import by.ganevich.io.CommandDescriptor;
import by.ganevich.service.BankService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
@Getter
public class ReadBanksCommand implements ICommand {

    private final String commandName = "read banks";

    private final BankService bankService;

    //read banks
    @Override
    public List<Bank> execute(CommandDescriptor commandDescriptor) {

        List<Bank> banks = bankService.readBanks();

        return banks;
    }
}
