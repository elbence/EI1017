package Distancias;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DistanceFactoryTest {

    @Test
    void getDistance() {
        Factory distancia = new DistanceFactory();
        Distance eucDist = distancia.getDistance(DistanceType.EUCLIDEAN);
        assertTrue(eucDist instanceof EuclideanDistance);
    }
}