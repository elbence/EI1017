package InterfazGrafica.modelo;

import InterfazGrafica.vista.ImplementacionVista;
import InterfazGrafica.vista.InformaVista;

public class ImplementacionModelo implements CambioModelo,InterrogaModelo{

    private InformaVista vista;

    public void setVista(InformaVista vista) {
        this.vista = vista;
    }

}
