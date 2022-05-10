package InterfazGrafica.modelo;

import Estructura.TableWithLabels;

import java.util.List;

public interface InterrogaModelo {
    List<String> getHeaders();

    TableWithLabels getDataTable();

    List<String> getTypes();
}
