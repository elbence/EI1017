package Estructura;

import java.util.ArrayList;
import java.util.List;

public class TableWithLabels extends Table{
    private List<RowWithLabels> rows;

    public TableWithLabels(){
        super();
        rows = new ArrayList<>();
    }

    public int size() {return rows.size();}

    @Override
    public RowWithLabels getRowAt(int rowNumber) {
        return rows.get(rowNumber);
    }

    public boolean addRow(RowWithLabels row){
        return rows.add(row);
    }

    public List<RowWithLabels> getAllData() { return rows; }

    @Override
    public String toString() {
        String tmp = "";
        tmp += super.getAllHeaders();
        for (RowWithLabels row : rows) {
            tmp += ("\n" + row);
        }
        return tmp;
    }
}
