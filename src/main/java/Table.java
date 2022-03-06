import java.util.LinkedList;
import java.util.List;

public class Table {
    private List<String> headers;
    private List<Row> rows;
    public Table(){
        rows = new LinkedList<>();
        headers = new LinkedList<>();
    }

    public List<Double> getClumAt(int columNumber){
        return rows.get(columNumber).getData();
    }
    public Row getRowAt(int rowNumber){
        return rows.get(rowNumber);
    }
    public boolean addRow(Row row){
       return rows.add(row);
    }
    public boolean addHeader(String header){
        return headers.add(header);
    }

}
