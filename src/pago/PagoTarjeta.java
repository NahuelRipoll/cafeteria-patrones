package pago;

public class PagoTarjeta implements MetodoPago{

    @Override
    public double calcularTotal(double monto) {
        return monto + monto * 0.10;
    }
}
