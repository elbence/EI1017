package InterfazGrafica.controlador;

import InterfazGrafica.modelo.CambioModelo;
import InterfazGrafica.modelo.ImplementacionModelo;
import InterfazGrafica.vista.ImplementacionVista;
import InterfazGrafica.vista.InterrogaVista;

public class ImplementacionControlador implements Controlador {

    private InterrogaVista vista;
    private CambioModelo modelo;

    public void sayHello() {
        System.out.println("Hello World!");
    }

    @Override
    public void openFile() {
        System.out.println("Opening File...");
    }

    public void setModelo(CambioModelo modelo) {
        this.modelo = modelo;
    }

    public void setVista(InterrogaVista vista) {
        this.vista = vista;
    }

}
