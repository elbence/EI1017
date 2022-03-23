import Estructura.RowWithLabels;
import Estructura.Table;
import Estructura.TableWithLabels;
import Exepciones.NoDataException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TableWithLabelsTest {
    Table tabla= new Table();
    String[] etiquetas = {"sepal length", "sepal width", "petal length", "petal width"};
    Double[][] array = {
            {5.1, 4.9, 4.7, 4.6},
            {3.5, 3.0, 3.2, 3.1},
            {1.4, 1.4, 1.3, 1.5},
            {0.2, 0.2, 0.2, 0.2}
    };

    @Test
    void addRow() {
        for (int i = 0; i<array.length; i++){
            RowWithLabels row = new RowWithLabels(etiquetas[i]);
            for (int j = 0; j<array[i].length; j++){
                row.addItem(array[i][j]);
            }
            tabla.addRow(row);
            assertEquals(row, tabla.getRowAt(i));
        }
    }

    @Test
    void getAllData(){
        TableWithLabels tabla= new TableWithLabels();
        assertThrows(NoDataException.class, () -> tabla.getAllData());
        RowWithLabels row = new RowWithLabels(etiquetas[0]);
        tabla.addRow(row);
        assertDoesNotThrow(() -> tabla.getAllData());
    }
}