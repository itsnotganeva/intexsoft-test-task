package by.ganevich.io.factory;

import by.ganevich.exception.CommandNotFoundException;
import by.ganevich.io.CommandDescriptor;
import by.ganevich.io.commands.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;

@AllArgsConstructor
@Component
public class CommandFactory {

    private final CreateBankCommand createBankCommand;
    private final ReadBanksCommand readBanksCommand;
    private final UpdateBankCommand updateBankCommand;
    private final DeleteBankCommand deleteBankCommand;

    private final CreateClientCommand createClientCommand;
    private final ReadClientsCommand readClientsCommand;
    private final UpdateClientCommand updateClientCommand;
    private final DeleteClientCommand deleteClientCommand;
    private final AddClientToBankCommand addClientToBankCommand;

    private final MakeTransactionCommand makeTransactionCommand;
    private final ReadTransactionsCommand readTransactionsCommand;

    private final ReadBankAccountsCommand readBankAccountsCommand;

    private final HelpCommand helpCommand;
    private final ExitCommand exitCommand;

    private Map<String, ICommand> commands;

    @PostConstruct
    public Map<String, ICommand> collectCommands() {
        commands.put(createBankCommand.getCommandName(), createBankCommand);
        commands.put(readBanksCommand.getCommandName(), readBanksCommand);
        commands.put(updateBankCommand.getCommandName(), updateBankCommand);
        commands.put(deleteBankCommand.getCommandName(), deleteBankCommand);
        commands.put(createClientCommand.getCommandName(), createClientCommand);
        commands.put(readClientsCommand.getCommandName(), readClientsCommand);
        commands.put(updateClientCommand.getCommandName(), updateClientCommand);
        commands.put(deleteClientCommand.getCommandName(), deleteClientCommand);
        commands.put(addClientToBankCommand.getCommandName(), addClientToBankCommand);
        commands.put(makeTransactionCommand.getCommandName(), makeTransactionCommand);
        commands.put(readTransactionsCommand.getCommandName(), readTransactionsCommand);
        commands.put(readBankAccountsCommand.getCommandName(), readBankAccountsCommand);
        commands.put(helpCommand.getCommandName(), helpCommand);
        commands.put(exitCommand.getCommandName(), exitCommand);
        return commands;
    }

    public ICommand getCommand(CommandDescriptor commandDescriptor) {

        ICommand command = commands.get(commandDescriptor.getCommandName());

        if (command == null) {
            throw new CommandNotFoundException("Wrong input of command! Try again!");
        }

        return command;
    }

}
