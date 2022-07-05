package by.ganevich.pdf;

import be.quodlibet.boxable.BaseTable;
import be.quodlibet.boxable.Cell;
import be.quodlibet.boxable.Row;
import be.quodlibet.boxable.VerticalAlignment;
import be.quodlibet.boxable.line.LineStyle;
import by.ganevich.dto.TransactionDto;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.io.IOException;
import java.util.List;

@Component
public class PdfCreator {

    public void createReportOfAccount(List<TransactionDto> transactionsDto, String filename) throws IOException {
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);
        final PDPageContentStream contentStream = new PDPageContentStream(document, page);
        float startNewPage = page.getMediaBox().getHeight() - 100;
        float tableWidth = page.getMediaBox().getWidth() - 100;

        BaseTable table = new BaseTable(750, startNewPage,
                70, tableWidth, 50, document, page, true, true);
        Row<PDPage> headerRow = table.createRow(50);
        Cell<PDPage> cell = null;
        if (filename.equals("ReportOfSender.pdf")) {
            cell = headerRow.createCell(100, "Transactions of sender");
        } else if (filename.equals("ReportOfReceiver.pdf")) {
            cell = headerRow.createCell(100, "Transactions of receiver");
        } else if (filename.equals("ReportOfSentAccount.pdf")) {
            cell = headerRow.createCell(100, "Transactions of sent account");
        } else {
            cell = headerRow.createCell(100, "Transactions of receive account");
        }
        cell.setFont(PDType1Font.HELVETICA_BOLD);
        cell.setFontSize(20);
        cell.setValign(VerticalAlignment.MIDDLE);
        cell.setTopBorderStyle(new LineStyle(Color.BLACK, 10));
        table.addHeaderRow(headerRow);

        Row<PDPage> row = table.createRow(20);
        cell = row.createCell(20, "Sender's number");
        cell.setBottomBorderStyle(new LineStyle(Color.RED, 5));
        cell.setFontSize(16);
        cell = row.createCell(20, "Amount of money");
        cell.setFontSize(16);
        cell = row.createCell(20, "Currency");
        cell.setFontSize(16);
        cell = row.createCell(20, "Receiver's number");
        cell.setBottomBorderStyle(new LineStyle(Color.GREEN, 5));
        cell.setFontSize(16);
        cell = row.createCell(20, "Date");
        cell.setFontSize(16);

        for (TransactionDto t : transactionsDto) {
            row = table.createRow(20);
            cell = row.createCell(20, t.getSenderAccount().getNumber());
            cell.setFontSize(14);
            cell = row.createCell(20, t.getAmountOfMoney());
            cell.setFontSize(14);
            cell = row.createCell(20, t.getSenderAccount().getCurrency());
            cell.setFontSize(14);
            cell = row.createCell(20, t.getReceiverAccount().getNumber());
            cell.setFontSize(14);
            cell = row.createCell(20, t.getDate().toString());
            cell.setFontSize(14);
        }
        row = table.createRow(20);
        cell = row.createCell(60, "Total sum of money");
        cell.setFont(PDType1Font.HELVETICA_BOLD);
        cell.setFontSize(14);
        cell = row.createCell(40, getTotalSum(transactionsDto).toString());
        cell.setFontSize(14);

        table.draw();
        contentStream.close();
        document.save("/home/INTEXSOFT/matsvei.hanevich/work/test-task/intexsoft-test-task/src/main/resources/pdf/" + filename);
        document.close();
    }

    public Double getTotalSum(List<TransactionDto> transactions) {
        Double totalSum = 0.0;
        for (TransactionDto transaction : transactions) {
            totalSum += Double.valueOf(transaction.getAmountOfMoney());
        }
        return totalSum;
    }
}