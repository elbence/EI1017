import Estructura.Row;
import Estructura.Table;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TableTest {

    Table tabla= new Table();
    String[] etiquetas = {"sepal length", "sepal width", "petal length", "petal width"};
    Double[][] array = {
            {5.1, 4.9, 4.7, 4.6},
            {3.5, 3.0, 3.2, 3.1},
            {1.4, 1.4, 1.3, 1.5},
            {0.2, 0.2, 0.2, 0.2}
    };

    @org.junit.jupiter.api.Test
    void addHeader() {
        int i = 0;
        for (String etiqueta : etiquetas){
            tabla.addHeader(etiqueta);
            System.out.println(tabla.getHeader(i));
            assertEquals(etiqueta, tabla.getHeader(i));
            i++;
        }
    }

    @org.junit.jupiter.api.Test
    void addRow() {
        for (int i = 0; i<array.length; i++){
            Row row = new Row();
            for (int j = 0; j<array[i].length; j++){
                row.addItem(array[i][j]);
            }
            tabla.addRow(row);
            System.out.println(tabla.getRowAt(i));
            assertEquals(row, tabla.getRowAt(i));
        }

    }
    void rellenar() {
        for (Double[] doubles : array) {
            Row row = new Row();
            for (Double aDouble : doubles) {
                row.addItem(aDouble);
            }
            tabla.addRow(row);
        }
    }
    @org.junit.jupiter.api.Test
    void getClumAt() {
        rellenar();
        for (int i = 0; i<array.length; i++){
           List<Double> columna= new LinkedList<>();
            for (Double[] doubles : array) {
                columna.add(doubles[i]);
            }
            System.out.println(tabla.getClumAt(i));
            assertEquals(columna, tabla.getClumAt(i));

        }
    }

    @org.junit.jupiter.api.Test
    void getRowAt() {
        for (int i = 0; i<array.length; i++){
            Row row = new Row();
            for (int j = 0; j<array[i].length; j++){
                row.addItem(array[i][j]);
            }
            tabla.addRow(row);
            System.out.println(tabla.getRowAt(i));
            assertEquals(row, tabla.getRowAt(i));
        }
    }
}