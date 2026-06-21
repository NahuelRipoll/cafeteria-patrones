package vista;

import javax.swing.*;
import modelo.Producto;
import modelo.Pedido;
import modelo.Cafe;
import modelo.Te;
import decorador.ConLeche;
import decorador.ConChocolate;
import decorador.TamanioGrande;
import pago.PagoEfectivo;
import pago.PagoTarjeta;
import pago.PagoMercadoPago;
import pago.MetodoPago;

public class VentanaPrincipal extends JFrame {

    private JComboBox<String> comboProducto;
    private JCheckBox checkLeche;
    private JCheckBox checkChocolate;
    private JCheckBox checkGrande;
    private JComboBox<String> comboPago;
    private JTextArea areaResumen;

    public VentanaPrincipal() {
        setTitle("Cafeteria");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        comboProducto = new JComboBox<>(new String[]{"Cafe ($1000)", "Te ($800)"});
        panel.add(new JLabel("Producto:"));
        panel.add(comboProducto);

        checkLeche = new JCheckBox("Leche (+$200)");
        checkChocolate = new JCheckBox("Chocolate (+$300)");
        checkGrande = new JCheckBox("Tamaño grande (+$500)");
        panel.add(new JLabel("Extras:"));
        panel.add(checkLeche);
        panel.add(checkChocolate);
        panel.add(checkGrande);

        comboPago = new JComboBox<>(new String[]{"Efectivo (sin recargo)", "Tarjeta (+10%)", "MercadoPago (-5%)"});
        panel.add(new JLabel("Metodo de pago:"));
        panel.add(comboPago);

        JButton botonCalcular = new JButton("Calcular pedido");
        panel.add(botonCalcular);

        areaResumen = new JTextArea(5, 30);
        areaResumen.setEditable(false);
        panel.add(new JLabel("Resumen:"));
        panel.add(areaResumen);

        botonCalcular.addActionListener(e -> calcularPedido());

        add(panel);
        setVisible(true);
    }

    private void calcularPedido() {
        Producto producto;
        String nombreBebida;
        double precioBebida;
        if (comboProducto.getSelectedItem().equals("Cafe ($1000)")) {
            producto = new Cafe();
            nombreBebida = "Cafe";
            precioBebida = 1000;
        } else {
            producto = new Te();
            nombreBebida = "Te";
            precioBebida = 800;
        }

        if (checkLeche.isSelected()) producto = new ConLeche(producto);
        if (checkChocolate.isSelected()) producto = new ConChocolate(producto);
        if (checkGrande.isSelected()) producto = new TamanioGrande(producto);

        MetodoPago metodoPago;
        String pagoSeleccionado = (String) comboPago.getSelectedItem();
        if (pagoSeleccionado.equals("Tarjeta (+10%)")) {
            metodoPago = new PagoTarjeta();
        } else if (pagoSeleccionado.equals("MercadoPago (-5%)")) {
            metodoPago = new PagoMercadoPago();
        } else {
            metodoPago = new PagoEfectivo();
        }

        double precioBase = producto.obtenerPrecio();
        Pedido pedido = new Pedido(producto, metodoPago);
        double total = pedido.calcularTotal();

        String detallePago;
        if (pagoSeleccionado.equals("Tarjeta (+10%)")) {
            detallePago = "Tarjeta (recargo 10%): +$" + String.format("%.1f", precioBase * 0.10);
        } else if (pagoSeleccionado.equals("MercadoPago (-5%)")) {
            detallePago = "MercadoPago (descuento 5%): -$" + String.format("%.1f", precioBase * 0.05);
        } else {
            detallePago = "Efectivo (sin recargo)";
        }

        areaResumen.setText(
                "Producto: " + producto.obtenerDescripcion() + "\n" +
                nombreBebida + ": $" + precioBebida + "\n" +
                "Extras: $" + (precioBase - precioBebida) + "\n" +
                "Precio base: $" + precioBase + "\n" +
                "Forma de pago: " + detallePago + "\n" +
                "Total: $" + total
        );
    }
}