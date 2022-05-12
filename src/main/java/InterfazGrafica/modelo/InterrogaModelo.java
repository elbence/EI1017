package InterfazGrafica.modelo;

import Estructura.RowWithLabels;
import Estructura.TableWithLabels;

import java.util.List;

public interface InterrogaModelo {
    List<String> getHeaders();

    TableWithLabels getDataTable();

    List<String> getTypes();

    String getTipoEstimado();

    RowWithLabels getRowEstimada();
}
