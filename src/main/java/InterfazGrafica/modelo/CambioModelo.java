package InterfazGrafica.modelo;

import Distancias.DistanceType;

import java.io.File;
import java.util.List;

public interface CambioModelo {
    void setFile(File selectedFile, DistanceType tipoDistancia);

    void setTipoDistancia(DistanceType tipoDistancia);

    void setNuevoValorEstimate(List<Double> puntoDouble);
}
