package estructura;

import java.util.ArrayList;
import java.util.List;

public class Table {
    private List<String> headers;
    private List<Row> rows;
    public Table(){
        rows = new ArrayList<>();
        headers = new ArrayList<>();
    }

    public int size() {return rows.size();}

    public List<String> getHeaders() {return headers;}

    public List<Double> getClumAt(int columNumber){
        List<Double> columnas = new ArrayList<>();
        for (Row row : rows) {
            columnas.add(row.getData().get(columNumber));
        }
        return columnas;
    }
    public Row getRowAt(int rowNumber){
        return rows.get(rowNumber);
    }
    public String getHeader(int index){
        return headers.get(index);
    }

    //opcional
    public boolean addRow(Row row){
       return rows.add(row);
    }
    public boolean addHeader(String header){
        return headers.add(header);
    }

    @Override
    public String toString() {
        String tmp = "";
        tmp += headers;
        for (Row row : rows) tmp += ( "\n" + row);
        return tmp;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public Row getDefaultRow() {
        Row defRow = new Row();
        if (rows.isEmpty()) for(int i = 0; i < rows.get(0).size(); i++) defRow.addItem(0.0);
        return defRow;
    }
}
