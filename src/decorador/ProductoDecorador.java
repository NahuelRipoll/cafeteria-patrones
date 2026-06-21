package decorador;

import modelo.Producto;

public abstract class ProductoDecorador implements Producto {
    private Producto producto;

    public ProductoDecorador(Producto producto){
        this.producto = producto;
    }

    @Override
    public String obtenerDescripcion() {
        return producto.obtenerDescripcion();
    }

    @Override
    public double obtenerPrecio() {
        return producto.obtenerPrecio();
    }
}

