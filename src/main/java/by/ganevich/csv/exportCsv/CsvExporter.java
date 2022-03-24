package by.ganevich.csv.exportCsv;

import java.io.File;
import java.io.IOException;


public abstract class CsvExporter {

    public void doExport(String fileName) throws IOException {
        File file = new File(fileName);
        file.delete();
        exportCsv(fileName);
    }

    public abstract void exportCsv(String fileName) throws IOException;

}
