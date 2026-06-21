package pago;


public class PagoEfectivo implements MetodoPago{

    @Override
    public double calcularTotal(double monto) {
        return monto;
    }
}
