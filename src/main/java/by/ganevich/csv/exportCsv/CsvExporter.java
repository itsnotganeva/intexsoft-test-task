package by.ganevich.csv.exportCsv;

import java.io.File;
import java.io.IOException;


public abstract class CsvExporter {

    public File doExport(String fileName) throws IOException {
        File file = new File(fileName);
        file.delete();
        exportCsv(fileName);
        File exportedFile = new File(fileName);
        return exportedFile;
    }

    public abstract void exportCsv(String fileName) throws IOException;

}
