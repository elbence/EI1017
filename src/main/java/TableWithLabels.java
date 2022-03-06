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
}
