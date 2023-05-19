package interfaz_grafica.modelo;

import estructura.RowWithLabels;
import estructura.TableWithLabels;

import java.util.List;

public interface InterrogaModelo {
    List<String> getHeaders();

    TableWithLabels getDataTable();

    List<String> getTypes();

    String getTipoEstimado();

    RowWithLabels getRowEstimada();
}
