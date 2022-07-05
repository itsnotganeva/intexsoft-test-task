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
public class CreatePdfReportOfReceiver extends BaseCommand {

    private final String commandName = "createPdfReportOfReceiver";
    private final ClientService clientService;
    private final TransactionService transactionService;
    private final TransactionMapper transactionMapper;
    private final PdfCreator pdfCreator;
    private ReportOfClientDto report;

    @Override
    public String getDescriptionValue() {
        String description = "createPdfReportOfReceiver dateBefore=? dateAfter=? clientName=? surname=?";
        return description;
    }

    @Override
    public CommandResult doExecute(Map<String, String> parameters) throws IOException {
        Optional<Client> client = clientService.findClientByNameAndSurname(report.getClientName(), report.getSurname());
        List<TransactionDto> transactionsDto = transactionMapper
                .toDtoList(transactionService
                        .readAllByReceiverClientAndDate(client.get(),
                                Date.valueOf(report.getDateBefore()), Date.valueOf(report.getDateAfter())));
        pdfCreator.createReportOfAccount(transactionsDto, "ReportOfReceiver.pdf");
        CommandResult commandResult = new CommandResult();
        commandResult.setResult("PDF report of receiver was created");
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