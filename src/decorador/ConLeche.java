package decorador;

import modelo.Producto;

public class ConLeche extends ProductoDecorador {

    public ConLeche(Producto producto ){
        super(producto);
    }

    @Override
    public String obtenerDescripcion() {
        return super.obtenerDescripcion()  + " + leche";
    }

    @Override
    public double obtenerPrecio() {
        return super.obtenerPrecio() + 200;
    }
}
