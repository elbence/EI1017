package InterfazGrafica.modelo;

import Distancias.DistanceType;

import java.io.File;

public interface CambioModelo {
    void setFile(File selectedFile, DistanceType tipoDistancia);

    void setTipoDistancia(DistanceType tipoDistancia);
}
