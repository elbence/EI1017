package algoritmos;

import estructura.CSV;
import estructura.Table;
import exepciones.NotTrainedException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LinealRegressionTest {

    LinealRegression gestorRL = new LinealRegression();
    CSV gestorCSV = new CSV();
    Table tabla = gestorCSV.readTable("src/main/resources/miles_dollars.csv");

    @Test
    void train() {
        Double alpha = 1.255;
        Double beta = 274.85;

        gestorRL.train(tabla);
        Double testAlpha=gestorRL.getAlpha();
        Double testBeta=gestorRL.getBeta();
        System.out.println("alpha: "+testAlpha+", beta: "+testBeta);
        assertEquals(alpha, (double) Math.round(testAlpha * 1000d) / 1000d);
        assertEquals(beta, (double) Math.round(testBeta * 1000d) / 1000d);

    }

    @Test
    void estimate() {
        gestorRL = new LinealRegression();
        assertThrows(NotTrainedException.class, () -> gestorRL.estimate(1500.0));
        gestorRL.train(tabla);
    }
}