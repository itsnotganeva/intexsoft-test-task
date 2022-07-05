package by.ganevich.io.commands;

import by.ganevich.dto.ReportOfAccountDto;
import by.ganevich.dto.TransactionDto;
import by.ganevich.entity.BankAccount;
import by.ganevich.io.CommandDescriptor;
import by.ganevich.io.CommandResult;
import by.ganevich.mapper.interfaces.TransactionMapper;
import by.ganevich.pdf.PdfCreator;
import by.ganevich.service.BankAccountService;
import by.ganevich.service.TransactionService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.Date;
import java.util.List;
import java.util.Map;

@Component
@Getter
@Slf4j
@RequiredArgsConstructor
public class CreatePdfReportOfSentAccount extends BaseCommand {

    private final String commandName = "createPdfReportOfSentAccount";
    private final TransactionService transactionService;
    private final TransactionMapper transactionMapper;
    private final PdfCreator pdfCreator;
    private final BankAccountService bankAccountService;
    private ReportOfAccountDto report;

    @Override
    public String getDescriptionValue() {
        String description = "createPdfReportOfSentAccount dateBefore=? dateAfter=? accountNumber=?";
        return description;
    }

    @Override
    public CommandResult doExecute(Map<String, String> parameters) throws IOException {
        BankAccount bankAccount = bankAccountService
                .findBankAccountByNumber(Integer.valueOf(report.getAccountNumber()));
        List<TransactionDto> sentTransactions = transactionMapper
                .toDtoList(transactionService.readAllByDateAndSenderAccount(Date.valueOf(report.getDateBefore()),
                        Date.valueOf(report.getDateAfter()), bankAccount));
        pdfCreator.createReportOfAccount(sentTransactions, "ReportOfSentAccount.pdf");
        CommandResult commandResult = new CommandResult();
        commandResult.setResult("PDF report of sent account was created");
        return commandResult;
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