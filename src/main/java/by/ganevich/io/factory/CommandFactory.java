package by.ganevich.io.factory;

import by.ganevich.io.CommandDescriptor;
import by.ganevich.io.commands.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

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

    private List<ICommand> commands;

    public void collectCommands() {
        commands.add(createBankCommand);
        commands.add(readBanksCommand);
        commands.add(updateBankCommand);
        commands.add(deleteBankCommand);
        commands.add(createClientCommand);
        commands.add(readClientsCommand);
        commands.add(updateClientCommand);
        commands.add(deleteClientCommand);
        commands.add(addClientToBankCommand);
        commands.add(makeTransactionCommand);
        commands.add(readTransactionsCommand);
        commands.add(readBankAccountsCommand);
        commands.add(helpCommand);
    }

    public ICommand getCommand(CommandDescriptor commandDescriptor) {

        collectCommands();

        ICommand icommand = null;

        for (ICommand command : commands) {
            if (command.getCommandName().equals(commandDescriptor.getCommandName())) {
                icommand = command;
            }
        }

        return icommand;
    }

}
