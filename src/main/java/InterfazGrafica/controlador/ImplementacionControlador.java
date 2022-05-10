package InterfazGrafica.controlador;

import Distancias.Distance;
import Distancias.DistanceFactory;
import Distancias.DistanceType;
import Distancias.Factory;
import Estructura.CSV;
import Estructura.TableWithLabels;
import InterfazGrafica.modelo.CambioModelo;
import InterfazGrafica.modelo.ImplementacionModelo;
import InterfazGrafica.vista.ImplementacionVista;
import InterfazGrafica.vista.InterrogaVista;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.Arrays;
import java.util.regex.Pattern;

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
            System.out.println("Opening: " + selectedFile.getName());
            modelo.setFile(selectedFile, tipoDistancia);
        } else {
            System.out.println("File not valid");
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
    public void estimateValue() {
        String newPoint = vista.getNewPoint();
        System.out.println("Estimating:");
        if(newPoint != null && validaPunto(newPoint)) {
            System.out.println("Valid!");
            double[] puntoDouble = toDoubleArray(newPoint);
            System.out.println(Arrays.toString(puntoDouble));
        }
        System.out.println();
    }

    private double[] toDoubleArray(String newPoint) {
        String[] strArray = newPoint.split(",");
        double[] array = new double[strArray.length];
        for (int i = 0; i < strArray.length; i++) {
            array[i] = Double.parseDouble(strArray[i]);
        }
        return array;
    }

    private boolean validaPunto(String newPoint) {
        System.out.println(newPoint);
        String[] array = newPoint.split(",");
        if (array.length != 4) return false;
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
