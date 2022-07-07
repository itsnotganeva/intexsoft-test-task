package by.ganevich.io.commands;

import by.ganevich.dto.ReportOfClientDto;
import by.ganevich.entity.Client;
import by.ganevich.excel.ExcelWorker;
import by.ganevich.io.CommandDescriptor;
import by.ganevich.io.CommandResult;
import by.ganevich.service.ClientService;
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
public class CreateExcelReportOfClient extends BaseCommand {

    private final String commandName = "createReportOfClient";
    private final ClientService clientService;
    private final ExcelWorker excelWorker;

    private ReportOfClientDto report;

    @Override
    public String getDescriptionValue() {
        String description = "createExcelReportOfClient dateBefore=? dateAfter=? clientName=? surname=?";
        return description;
    }

    @Override
    public CommandResult doExecute(Map<String, String> parameters) throws IOException {
        Client client = clientService.findClientByNameAndSurname(report.getClientName(), report.getSurname()).get();
        excelWorker.createClientTransactionsFile(Date.valueOf(report.getDateBefore()),
                Date.valueOf(report.getDateAfter()), client.getId());
        CommandResult commandResult = new CommandResult();
        commandResult.setResult("Report of client was created");
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