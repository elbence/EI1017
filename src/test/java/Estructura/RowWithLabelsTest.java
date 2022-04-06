package Estructura;

import Estructura.RowWithLabels;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RowWithLabelsTest {

    @Test
    void getData() {
        List<Double> expectedValue = new ArrayList<>();
        RowWithLabels newRow = new RowWithLabels();
        for (int i = 0; i < Math.round(Math.random()*10); i++) {
            Double aux = Math.random();
            expectedValue.add(aux);
            newRow.addItem(aux);
        }
        assertEquals(expectedValue.toString(), newRow.getData().toString());
    }

    @Test
    void getLabel() {
        RowWithLabels newRow = new RowWithLabels();
        newRow.addLabel("Hello!");
        assertEquals("Hello!", newRow.getLabel());
    }
}