package by.ganevich.controller;

import by.ganevich.dto.ReportOfAccountDto;
import by.ganevich.dto.ReportOfClientDto;
import by.ganevich.entity.BankAccount;
import by.ganevich.entity.Client;
import by.ganevich.excel.ExcelWorker;
import by.ganevich.service.BankAccountService;
import by.ganevich.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.util.Optional;

@RestController
@AllArgsConstructor
@Slf4j
@Tag(name = "Report controller", description = "To manage reports")
public class ReportController {

    private final ExcelWorker excelWorker;
    private final BankAccountService bankAccountService;
    private final ClientService clientService;

    @PreAuthorize("hasAnyRole('ROLE_OPERATOR', 'ROLE_ADMIN')")
    @PostMapping(value = "/reports/{account-number}")
    @Operation(
            summary = "Creating report of account transactions",
            description = "Allows to read all clients"
    )
    public ResponseEntity<?> createReportOfAccount(
            @RequestBody@Parameter(description = "Data to create report of account")
                    ReportOfAccountDto report
    ) {
        BankAccount bankAccount = bankAccountService.findBankAccountByNumber(Integer.valueOf(report.getAccountNumber()));
        excelWorker.createAccountTransactionsFile(Date.valueOf(report.getDateBefore()),
                Date.valueOf(report.getDateAfter()), bankAccount);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_OPERATOR', 'ROLE_ADMIN')")
    @PostMapping(value = "/reports")
    @Operation(
            summary = "Creating report of client transactions",
            description = "Allows to read all clients"
    )
    public ResponseEntity<?> createReportOfClient(
            @RequestBody @Parameter(description = "Data to create report of client")
            ReportOfClientDto report
    ) {
        Optional<Client> client = clientService.findClientByNameAndSurname(report.getClientName(), report.getSurname());
        excelWorker.createClientTransactionsFile(Date.valueOf(report.getDateBefore()),
                Date.valueOf(report.getDateAfter()), client.get().getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
