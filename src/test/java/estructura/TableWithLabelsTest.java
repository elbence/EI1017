package estructura;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TableWithLabelsTest {
    Table table = new TableWithLabels();
    String[] etiquetas = {"sepal length", "sepal width", "petal length", "petal width, class"};
    String[] tipos = {"Iris-Rnd", "Iris-Veris"};
    Double[][] array = {
            {5.1, 4.9, 4.7, 4.6},
            {3.5, 3.0, 3.2, 3.1},
            {1.4, 1.4, 1.3, 1.5},
            {0.2, 0.2, 0.2, 0.2}
    };

    String expected =   "[sepal length, sepal width, petal length, petal width, class]" + "\n" +
                        "[5.1, 4.9, 4.7, 4.6] Iris-Rnd" + "\n" +
                        "[3.5, 3.0, 3.2, 3.1] Iris-Veris" + "\n" +
                        "[1.4, 1.4, 1.3, 1.5] Iris-Rnd" + "\n" +
                        "[0.2, 0.2, 0.2, 0.2] Iris-Veris";

    @Test
    void addRow() {
        for (int i = 0; i<array.length; i++){
            RowWithLabels row = new RowWithLabels(etiquetas[i]);
            for (int j = 0; j<array[i].length; j++){
                row.addItem(array[i][j]);
            }
            table.addRow(row);
            assertEquals(row, table.getRowAt(i));
        }
    }

    @Test
    void toStringTest() {
        fillData();
        System.out.println(table);
        assertEquals(table.toString(), expected);
    }

    private void fillData() {
        table = new TableWithLabels();
        for (String etiqueta : etiquetas) table.addHeader(etiqueta);
        for (int i = 0; i<array.length; i++){
            RowWithLabels row = new RowWithLabels(tipos[i%2]);
            for (int j = 0; j<array[i].length; j++){
                row.addItem(array[i][j]);
            }
            table.addRow(row);
        }
    }

}