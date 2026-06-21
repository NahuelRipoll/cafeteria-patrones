package modelo;

public class Cafe implements Producto{
    @Override
    public String obtenerDescripcion(){
        return "Cafe";
    }

    @Override
    public double obtenerPrecio() {
        return 1000;
    }
}
