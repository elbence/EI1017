package InterfazGrafica.modelo;

import Algoritmos.KNearestNeighbours;
import Distancias.DistanceFactory;
import Distancias.DistanceType;
import Distancias.Factory;
import Estructura.CSV;
import Estructura.TableWithLabels;
import InterfazGrafica.vista.InformaVista;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ImplementacionModelo implements CambioModelo,InterrogaModelo{

    private InformaVista vista;

    private File file;
    private KNearestNeighbours kNearestNeighbours;
    private TableWithLabels table;
    private CSV gestorCSV;
    private Factory distancia;

    public ImplementacionModelo() {
        gestorCSV = new CSV();
        distancia = new DistanceFactory();
    }

    public void setFile(File file, DistanceType tipoDistancia) {
        // modifica valores
        this.file = file;
        table = gestorCSV.readTableWithLabels(file.getAbsolutePath());
        kNearestNeighbours = new KNearestNeighbours(distancia.getDistance(tipoDistancia));
        kNearestNeighbours.train(table);
        // notifica vista
        vista.nuevoDocumento();
    }

    @Override
    public void setTipoDistancia(DistanceType tipoDistancia) {
        kNearestNeighbours.setDistance(distancia.getDistance(tipoDistancia));
    }

    public void setVista(InformaVista vista) {
        this.vista = vista;
    }

    @Override
    public List<String> getHeaders() {
        return table.getHeaders();
    }

    @Override
    public TableWithLabels getDataTable() {
        return table;
    }

    @Override
    public List<String> getTypes() {
        List<String> aparecidos = new ArrayList<>();
        for(int i = 0; i < table.size(); i++) {
            String act = table.getRowAt(i).getLabel();
            if (!aparecidos.contains(act)) aparecidos.add(act);
        }
        return aparecidos;
    }
}
