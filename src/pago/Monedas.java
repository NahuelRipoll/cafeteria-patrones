package pago;


public class Monedas implements MetodoPago{

    @Override
    public double calcularTotal(double monto) {
        return monto + monto * 0.1;
    }
}
