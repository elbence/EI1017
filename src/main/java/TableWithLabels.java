import java.util.LinkedList;
import java.util.List;

public class TableWithLabels extends Table{
    private List<RowWithLabels> rows;

    public TableWithLabels(){
        super();
        rows = new LinkedList<>();
    }

    @Override
    public RowWithLabels getRowAt(int rowNumber) {
        return rows.get(rowNumber);
    }

    public boolean addRow(RowWithLabels row){
        return rows.add(row);
    }

    public List<RowWithLabels> getAllData() {return rows;}

    @Override
    public String toString() {
        String tmp = "";
        tmp += headers;
        for (RowWithLabels row : rows) {
            tmp += ("\n" + row);
        }
        return tmp;
    }
}
