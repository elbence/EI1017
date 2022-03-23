import Algoritmos.LinealRegression;
import Estructura.CSV;
import Estructura.Table;
import Exepciones.NotTrainedException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LinealRegressionTest {

    LinealRegression gestorRL = new LinealRegression();
    CSV gestorCSV = new CSV();
    Table tabla = gestorCSV.readTable("src/main/resources/miles_dollars.csv");

    @Test
    void train() {
        String alpha = "1.255";
        String beta = "274.85";

        gestorRL.train(tabla);
        Double testAlpha=gestorRL.getAlpha();
        Double testBeta=gestorRL.getBeta();
        System.out.println("alpha: "+testAlpha+", beta: "+testBeta);
        assertEquals(alpha, String.format("%.3f", testAlpha));
        assertEquals(beta, String.format("%.2f", testBeta));

    }

    @Test
    void estimate() {
        gestorRL = new LinealRegression();
        assertThrows(NotTrainedException.class, () -> gestorRL.estimate(1500.0));
        gestorRL.train(tabla);
    }
}