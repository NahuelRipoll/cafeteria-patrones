package modelo;

import pago.MetodoPago;

public class Pedido{

    private Producto producto;
    private MetodoPago metodoPago;

    public Pedido(Producto producto, MetodoPago metodoPago){
        this.producto = producto;
        this.metodoPago = metodoPago;

    }

    public double calcularTotal(){
        return metodoPago.calcularTotal(producto.obtenerPrecio());
    }
}
