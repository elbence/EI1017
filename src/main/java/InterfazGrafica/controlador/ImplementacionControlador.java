package InterfazGrafica.controlador;

import Distancias.DistanceType;
import InterfazGrafica.modelo.CambioModelo;
import InterfazGrafica.vista.InterrogaVista;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ImplementacionControlador implements Controlador {

    // * MAIN VARS
    private InterrogaVista vista;
    private CambioModelo modelo;

    // * OTHER VARS

    private DistanceType tipoDistancia;

    public ImplementacionControlador() {
        tipoDistancia = DistanceType.EUCLIDEAN;
    }

    public void sayHello() {
        System.out.println("Hello World!");
    }

    @Override
    public void openFile() {
        FileChooser fc = new FileChooser();
        File selectedFile = fc.showOpenDialog(null);

        if (selectedFile != null && selectedFile.isFile() && checkFileExtension(selectedFile.getAbsolutePath())) {
            System.out.println("(ctrl) Opening: " + selectedFile.getName());
            modelo.setFile(selectedFile, tipoDistancia);
        } else {
            System.out.println("(ctrl) File type not valid");
        }
    }

    private boolean checkFileExtension(String absolutePath) {
        return absolutePath.substring(absolutePath.lastIndexOf(".")).equals(".csv");
    }

    @Override
    public void actualizaDistancia(Number valorInicial, Number valorActual) {
        if (valorActual.equals(valorInicial)) return;
        else if (valorActual.intValue() == 0) {
            tipoDistancia = DistanceType.EUCLIDEAN;
        } else {
            tipoDistancia = DistanceType.MANHATTAN;
        }
        modelo.setTipoDistancia(tipoDistancia);
    }

    @Override
    public void notificaNuevoValorEstimate() { // Convertir el valor a un conjunto de doubles y darselo a modelo
        String newPoint = vista.getNuevoValorEstimate();
        System.out.println("(ctrl) New candidate: " + newPoint);
        if(newPoint != null && validaPunto(newPoint)) {
            List<Double> puntoDouble = toDoubleList(newPoint);
            System.out.println("(ctrl) Valid: " + newPoint);
            modelo.setNuevoValorEstimate(puntoDouble);
        }
    }

    private List<Double> toDoubleList(String newPoint) {
        String[] strArray = newPoint.split(",");
        List<Double> array = new ArrayList<>(strArray.length);
        for (String s : strArray) array.add(Double.parseDouble(s));
        return array;
    }

    private boolean validaPunto(String newPoint) {
        String[] array = newPoint.split(",");
        for (String act : array) if (!isNumeric(act)) return false;
        return true;
    }

    private boolean isNumeric(String act) {
        if (act == null) return false;
        try {
            Double.parseDouble(act);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void setModelo(CambioModelo modelo) {
        this.modelo = modelo;
    }

    public void setVista(InterrogaVista vista) {
        this.vista = vista;
    }

}
