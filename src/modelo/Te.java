package modelo;

public class Te implements Producto{
    @Override
    public String obtenerDescripcion() {
        return "Te";
    }

    @Override
    public double obtenerPrecio() {
        return 800;
    }
}
