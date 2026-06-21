package pago;

public class PagoMercadoPago implements MetodoPago{

    @Override
    public double calcularTotal(double monto) {
        return monto - monto * 0.05;
    }
}
