package decorador;

import modelo.Producto;

public class TamanioGrande extends ProductoDecorador {

    public TamanioGrande(Producto producto){
        super(producto);
    }

    @Override
    public String obtenerDescripcion() {
        return super.obtenerDescripcion() + " + tamaño grande";
    }


    @Override
    public double obtenerPrecio() {
        return super.obtenerPrecio() + 500;
    }
}
