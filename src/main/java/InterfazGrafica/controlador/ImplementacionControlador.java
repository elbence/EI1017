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

        if (selectedFile.isFile() && checkFileExtension(selectedFile.getAbsolutePath())) {
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

    public void setModelo(CambioModelo modelo) {
        this.modelo = modelo;
    }

    public void setVista(InterrogaVista vista) {
        this.vista = vista;
    }

}
