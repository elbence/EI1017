package Estructura;

import Estructura.Row;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RowTest {

    @Test
    void getData() {
        List<Double> expectedValue = new ArrayList<>();
        Row newRow = new Row();
        for (int i = 0; i < Math.round(Math.random()*10); i++) {
            Double aux = Math.random();
            expectedValue.add(aux);
            newRow.addItem(aux);
        }
        assertEquals(expectedValue.toString(), newRow.getData().toString());
    }
}