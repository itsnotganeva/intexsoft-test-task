package by.ganevich.io.commands;

import by.ganevich.dto.ReportOfAccountDto;
import by.ganevich.entity.BankAccount;
import by.ganevich.excel.ExcelWorker;
import by.ganevich.io.CommandDescriptor;
import by.ganevich.io.CommandResult;
import by.ganevich.service.BankAccountService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.Date;
import java.util.Map;

@Component
@Getter
@Slf4j
@RequiredArgsConstructor
public class CreateExcelReportOfAccount extends BaseCommand {
    private final String commandName = "createReportOfClient";
    private final BankAccountService bankAccountService;
    private final ExcelWorker excelWorker;
    private ReportOfAccountDto report;

    @Override
    public String getDescriptionValue() {
        String description = "createExcelReportOfAccount dateBefore=? dateAfter=? accountNumber=?";
        return description;
    }

    @Override
    public CommandResult doExecute(Map<String, String> parameters) throws IOException {
        BankAccount bankAccount = bankAccountService.findBankAccountByNumber(Integer.valueOf(report.getAccountNumber()));
        excelWorker.createAccountTransactionsFile(Date.valueOf(report.getDateBefore()),
                Date.valueOf(report.getDateAfter()), bankAccount);
        CommandResult commandResult = new CommandResult();
        commandResult.setResult("Report of account was created");
        return commandResult;
    }

    @Override
    public String getCommandName() {
        return null;
    }

    @Override
    public ICommand setDto(CommandDescriptor commandDescriptor) {
        ReportOfAccountDto report = new ReportOfAccountDto();
        report.setAccountNumber(commandDescriptor.getParameters().get("accountNumber"));
        report.setDateBefore(commandDescriptor.getParameters().get("dateBefore"));
        report.setDateAfter(commandDescriptor.getParameters().get("dateAfter"));
        this.report = report;
        return this;
    }
}
