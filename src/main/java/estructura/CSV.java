package estructura;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class CSV {

    // Se tomará constructor por defecto
    // Se trata de una clase para gestionar ficheros del tipo Estructura.CSV

    public Table readTable (String route) {
        try (Scanner myIter = new Scanner(new File(route))) {
            myIter.useDelimiter(",");

            Table table = new Table();
            if (myIter.hasNextLine()) addHeadersToTable(myIter.nextLine(), table);
            while (myIter.hasNextLine()) {
                String line = myIter.nextLine();
                table.addRow(stringToRow(line));
            }
            return table;
        } catch (FileNotFoundException e) {
            System.out.println("ERROR: File does not exist.");
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.out.println("ERROR: Provide a path");
            e.printStackTrace();
        }
        return null;
    }
    // Defined Structure to read:
    // First line should contain all headers
    // Remaining rows should contain key at end of line (recommended)
    public TableWithLabels readTableWithLabels (String route) {
        try {
            File myFile = new File(route);
            Scanner myIter = new Scanner(myFile);
            myIter.useDelimiter(",");

            TableWithLabels table = new TableWithLabels();
            if (myIter.hasNextLine()) addHeadersToTable(myIter.nextLine(), table);
            while (myIter.hasNextLine()) {
                String line = myIter.nextLine();
                table.addRow(stringToRowWithLabels(line));
            }
            return table;
        } catch (FileNotFoundException e) {
            System.out.println("ERROR: File does not exist.");
            e.printStackTrace();
        }
        return null;
    }

    // Adds the strings in headerList as headers of the Estructura.Table table
    private void addHeadersToTable (String headersList, Table table) {
        Scanner headerScanner = new Scanner(headersList);
        headerScanner.useDelimiter(",");
        while (headerScanner.hasNext()) {
            table.addHeader(headerScanner.next());
        }
    }

    // Converts a String into a Estructura.Row and returns it (NO SAFETY, should be checked first!)
    private Row stringToRow (String linea) {
        Row newRow = new Row();
        Scanner lineScanner = new Scanner(linea);
        lineScanner.useDelimiter(",");
        while (lineScanner.hasNext()) newRow.addItem(Double.parseDouble(lineScanner.next()));
        return newRow;
    }
    // Converts a String into a ROW and checks for a label to add
    private RowWithLabels stringToRowWithLabels (String linea) {
        RowWithLabels newRow = new RowWithLabels();
        Scanner lineScanner = new Scanner(linea);
        lineScanner.useDelimiter(",");
        while (lineScanner.hasNext()) {
            String data = lineScanner.next();
            if (isDouble(data)) {
                newRow.addItem(Double.parseDouble(data));
            } else {
                newRow.addLabel(data);
            }
        }
        return newRow;
    }

    private boolean isDouble(String value) {
        try {
            Double tmp = Double.parseDouble(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}