package InterfazGrafica.modelo;

import Algoritmos.KNearestNeighbours;
import Distancias.DistanceFactory;
import Distancias.DistanceType;
import Distancias.Factory;
import Estructura.CSV;
import Estructura.RowWithLabels;
import Estructura.TableWithLabels;
import Exepciones.NotTrainedException;
import InterfazGrafica.vista.InformaVista;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ImplementacionModelo implements CambioModelo,InterrogaModelo{

    private InformaVista vista;

    private final CSV gestorCSV;
    private final Factory distancia;
    private boolean fileOpened;
    private TableWithLabels table;
    private KNearestNeighbours kNearestNeighbours;
    private List<Double> puntoAEstimar;
    private String tipoEstimado;

    public ImplementacionModelo() {
        gestorCSV = new CSV();
        distancia = new DistanceFactory();
        fileOpened = false;
    }

    public void setFile(File file, DistanceType tipoDistancia) { // Falta: ver si table || file es valida
        // modifica variables principales
        table = gestorCSV.readTableWithLabels(file.getAbsolutePath());
        fileOpened = true;
        // entrena KNN con tipos base y nuevos datos
        kNearestNeighbours = new KNearestNeighbours(distancia.getDistance(tipoDistancia));
        kNearestNeighbours.train(table);
        // notifica vista
        vista.nuevoDocumento();
    }

    @Override
    public void setTipoDistancia(DistanceType tipoDistancia) {
        kNearestNeighbours.setDistance(distancia.getDistance(tipoDistancia));
    }

    @Override
    public void setNuevoValorEstimate(List<Double> puntoDouble) { // * TO DO, missing tipoNotGuessed object
        puntoAEstimar = puntoDouble;
        if (!validaPuntoAEstimar()) return;
        try {
            tipoEstimado = kNearestNeighbours.estimate(puntoAEstimar);
        } catch (NotTrainedException e) {
            tipoEstimado = null;
            throw new RuntimeException(e);
        }
        if (tipoEstimado != null) vista.nuevaEstimacion();
    }

    private boolean validaPuntoAEstimar() { // * TO DO
        if (!fileOpened || puntoAEstimar == null || puntoAEstimar.size() != table.getDefaultRow().size()) {
            System.out.println("(modl) Punto NO valido");
            return false;
        }
        System.out.println("(modl) Punto valido");
        return true;
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

    @Override
    public String getTipoEstimado() {
        return tipoEstimado;
    }

    @Override
    public RowWithLabels getRowEstimada() {
        RowWithLabels tmp = new RowWithLabels();
        tmp.addLabel("ESTIMADO");
        for(Double coord : puntoAEstimar) tmp.addItem(coord);
        return tmp;
    }
}
