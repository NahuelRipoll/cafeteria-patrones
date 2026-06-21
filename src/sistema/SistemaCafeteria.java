package sistema;

import vista.VentanaPrincipal;

public class SistemaCafeteria {

    private SistemaCafeteria() {}

    private static SistemaCafeteria instancia;

    public static SistemaCafeteria getInstance() {
        if (instancia == null) {
            instancia = new SistemaCafeteria();
        }
        return instancia;
    }

    public void iniciar() {
        new VentanaPrincipal();
    }
}
