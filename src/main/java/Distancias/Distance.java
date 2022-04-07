package Distancias;

import Estructura.Row;

import java.util.List;

public interface Distance {
    Double calculateDistance(List<Double> p, List<Double> q);
}
