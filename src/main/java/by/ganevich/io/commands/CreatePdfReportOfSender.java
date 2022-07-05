package by.ganevich.io.commands;

import by.ganevich.dto.ReportOfClientDto;
import by.ganevich.dto.TransactionDto;
import by.ganevich.entity.Client;
import by.ganevich.io.CommandDescriptor;
import by.ganevich.io.CommandResult;
import by.ganevich.mapper.interfaces.TransactionMapper;
import by.ganevich.pdf.PdfCreator;
import by.ganevich.service.ClientService;
import by.ganevich.service.TransactionService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@Getter
@Slf4j
@RequiredArgsConstructor
public class CreatePdfReportOfSender extends BaseCommand {

    private final String commandName = "createPdfReportOfSender";
    private final ClientService clientService;
    private final TransactionService transactionService;
    private final TransactionMapper transactionMapper;
    private final PdfCreator pdfCreator;
    private ReportOfClientDto report;

    @Override
    public String getDescriptionValue() {
        String description = "createPdfReportOfSender dateBefore=? dateAfter=? clientName=? surname=?";
        return description;
    }

    @Override
    public CommandResult doExecute(Map<String, String> parameters) throws IOException {
        Optional<Client> client = clientService.findClientByNameAndSurname(report.getClientName(), report.getSurname());
        List<TransactionDto> transactionsDto = transactionMapper
                .toDtoList(transactionService
                        .readAllBySenderClientAndDate(client.get(),
                                Date.valueOf(report.getDateBefore()), Date.valueOf(report.getDateAfter())));
        pdfCreator.createReportOfAccount(transactionsDto, "ReportOfSender.pdf");
        CommandResult commandResult = new CommandResult();
        commandResult.setResult("PDF report of sender was created");
        return commandResult;
    }

    @Override
    public ICommand setDto(CommandDescriptor commandDescriptor) {
        ReportOfClientDto report = new ReportOfClientDto();
        report.setClientName(commandDescriptor.getParameters().get("clientName"));
        report.setSurname(commandDescriptor.getParameters().get("surname"));
        report.setDateBefore(commandDescriptor.getParameters().get("dateBefore"));
        report.setDateAfter(commandDescriptor.getParameters().get("dateAfter"));
        this.report = report;
        return this;
    }
}