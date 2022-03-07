import org.junit.jupiter.api.BeforeAll;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class TableTest {
    Table tabla= new Table();
    String[] etiquetas = {"sepal length", "sepal width", "petal length", "petal width"};
    @org.junit.jupiter.api.Test
    void addHeader() {
        int i = 0;
        for (String etiqueta : etiquetas){
            tabla.addHeader(etiquetas[i]);
            assertEquals(etiquetas[i], tabla.getHeader(i));
            i++;
        }
        System.out.println(tabla.getHeaders());
    }

    @org.junit.jupiter.api.Test
    void addRow() {
        Double[][] array = {
            {5.1, 4.9, 4.7, 4.6},
            {3.5, 3.0, 3.2, 3.1},
            {1.4, 1.4, 1.3, 1.5},
            {0.2, 0.2, 0.2, 0.2}
        };
        for (int i = 0; i<array.length; i++){
            Row row = new Row();
            for (int j = 0; j<array[i].length; j++){
                row.addItem(array[i][j]);
            }
            tabla.addRow(row);
        }

    }

    @org.junit.jupiter.api.Test
    void getClumAt() {
    }

    @org.junit.jupiter.api.Test
    void getRowAt() {
    }
    @org.junit.jupiter.api.Test
    void getHeader() {
    }
    @org.junit.jupiter.api.Test
    void getHeaders() {
    }
}