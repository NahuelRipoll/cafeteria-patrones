package decorador;

import modelo.Producto;

public class ConChocolate extends ProductoDecorador{

    public ConChocolate(Producto producto){
        super(producto);
    }

    @Override
    public String obtenerDescripcion() {
        return super.obtenerDescripcion() + " + chocolate";
    }

    @Override
    public double obtenerPrecio() {
        return super.obtenerPrecio() + 300;
    }
}
