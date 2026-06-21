# Sistema de Pedidos para Cafeteria — Ayuda Memoria Final

## Que es el proyecto

Aplicacion de escritorio hecha en Java con interfaz grafica en Swing. Permite crear un pedido de cafeteria eligiendo un producto base, agregando extras y seleccionando una forma de pago. El sistema calcula y muestra el total con el detalle del calculo.

El objetivo no es un sistema comercial completo, sino demostrar el uso correcto de tres patrones de diseno en un proyecto chico y facil de explicar.

---

## Patrones Usados

1. **Decorator** — para agregar extras al producto
2. **Strategy** — para los distintos metodos de pago
3. **Singleton** — para el sistema principal

---

## Estructura del Proyecto

```
src/
  Main.java

  modelo/
    Producto.java       <- interfaz base
    Cafe.java           <- producto base
    Te.java             <- producto base
    Pedido.java         <- relaciona producto con metodo de pago

  decorador/
    ProductoDecorador.java   <- clase abstracta base de los decoradores
    ConLeche.java            <- extra: +$200
    ConChocolate.java        <- extra: +$300
    TamanioGrande.java       <- extra: +$500

  pago/
    MetodoPago.java          <- interfaz de pago
    PagoEfectivo.java        <- sin recargo
    PagoTarjeta.java         <- recargo 10%
    PagoMercadoPago.java     <- descuento 5%

  sistema/
    SistemaCafeteria.java    <- Singleton

  vista/
    VentanaPrincipal.java    <- ventana Swing
```

---

## Patron Decorator

### Para que se usa

Para agregar extras a un producto sin modificar la clase original y sin crear una clase por cada combinacion posible.

Sin Decorator habria que crear: `CafeConLeche`, `CafeConChocolate`, `CafeConLecheYChocolate`, `CafeGrandeConLeche`, etc. Con Decorator se encadenan los extras dinamicamente.

### Como funciona

`Producto` es la interfaz con dos metodos: `obtenerDescripcion()` y `obtenerPrecio()`.

`Cafe` y `Te` la implementan directamente como productos base.

`ProductoDecorador` tambien implementa `Producto`, pero guarda internamente otro `Producto` y le delega los metodos:

```java
public abstract class ProductoDecorador implements Producto {
    private Producto producto;

    public ProductoDecorador(Producto producto) {
        this.producto = producto;
    }

    public String obtenerDescripcion() { return producto.obtenerDescripcion(); }
    public double obtenerPrecio() { return producto.obtenerPrecio(); }
}
```

Los decoradores concretos extienden `ProductoDecorador` y suman su aporte:

```java
public class ConLeche extends ProductoDecorador {
    public ConLeche(Producto producto) { super(producto); }

    public String obtenerDescripcion() { return super.obtenerDescripcion() + " + leche"; }
    public double obtenerPrecio() { return super.obtenerPrecio() + 200; }
}
```

### Ejemplo de uso

```java
Producto producto = new Cafe();           // "Cafe" - $1000
producto = new ConLeche(producto);        // "Cafe + leche" - $1200
producto = new ConChocolate(producto);    // "Cafe + leche + chocolate" - $1500
```

### Por que se aplica aqui

Porque los extras se pueden combinar libremente. Decorator evita crear una clase distinta para cada combinacion posible.

---

## Patron Strategy

### Para que se usa

Para representar distintas formas de pago donde cada una calcula el total de manera diferente, sin que `Pedido` conozca los detalles de cada una.

### Como funciona

`MetodoPago` es la interfaz con un metodo:

```java
public interface MetodoPago {
    double calcularTotal(double monto);
}
```

Cada estrategia la implementa a su manera:

```java
// Sin recargo
public class PagoEfectivo implements MetodoPago {
    public double calcularTotal(double monto) { return monto; }
}

// Recargo 10%
public class PagoTarjeta implements MetodoPago {
    public double calcularTotal(double monto) { return monto + monto * 0.10; }
}

// Descuento 5%
public class PagoMercadoPago implements MetodoPago {
    public double calcularTotal(double monto) { return monto - monto * 0.05; }
}
```

`Pedido` usa la interfaz sin saber que estrategia es:

```java
public class Pedido {
    private Producto producto;
    private MetodoPago metodoPago;

    public Pedido(Producto producto, MetodoPago metodoPago) {
        this.producto = producto;
        this.metodoPago = metodoPago;
    }

    public double calcularTotal() {
        return metodoPago.calcularTotal(producto.obtenerPrecio());
    }
}
```

### Por que se aplica aqui

Porque cada forma de pago calcula el total de manera distinta. Si se quiere agregar una nueva forma de pago, solo se crea una nueva clase sin modificar `Pedido`.

---

## Patron Singleton

### Para que se usa

Para garantizar que `SistemaCafeteria` tenga una unica instancia durante toda la ejecucion del programa.

### Como funciona

```java
public class SistemaCafeteria {
    private static SistemaCafeteria instancia;

    private SistemaCafeteria() {}

    public static SistemaCafeteria getInstance() {
        if (instancia == null) {
            instancia = new SistemaCafeteria();
        }
        return instancia;
    }
}
```

- Constructor `private`: nadie puede hacer `new SistemaCafeteria()` desde afuera.
- Variable `static`: pertenece a la clase, no a un objeto.
- `getInstance()`: unico punto de acceso. Si no existe la instancia la crea, si ya existe la devuelve.

### Por que se aplica aqui

Porque no tiene sentido tener dos sistemas de cafeteria corriendo al mismo tiempo. Centraliza el acceso al sistema principal.

---

## Flujo del Programa

```
Main
  └── SistemaCafeteria.getInstance()   <- Singleton: obtiene la unica instancia
        └── .iniciar()
              └── new VentanaPrincipal()
                    └── El usuario elige producto, extras y forma de pago
                    └── Presiona "Calcular pedido"
                    └── calcularPedido():
                          1. Crea Cafe o Te  (producto base)
                          2. Envuelve con decoradores segun checkboxes  (Decorator)
                          3. Crea la estrategia de pago segun seleccion  (Strategy)
                          4. Crea Pedido con producto y metodoPago
                          5. Llama pedido.calcularTotal()
                          6. Muestra resumen en pantalla
```

---

## Preguntas Posibles del Profesor

**Por que usaste Decorator?**
Porque los extras se pueden combinar libremente. Si no usara Decorator tendria que crear una clase por cada combinacion posible de extras, lo cual es imposible de mantener.

**Por que usaste Strategy?**
Porque cada forma de pago calcula el total de manera distinta. La clase `Pedido` no necesita conocer esos detalles, solo usa la interfaz `MetodoPago`. Si quiero agregar una nueva forma de pago, creo una nueva clase sin tocar `Pedido`.

**Por que usaste Singleton?**
Porque el sistema principal debe tener una unica instancia durante toda la ejecucion. No tendria sentido crear varios sistemas de cafeteria en el mismo programa.

**Cual es el patron mas importante?**
Decorator, porque resuelve el problema central del proyecto: los productos con extras configurables dinamicamente.

**Que diferencia hay entre Decorator y herencia simple?**
Con herencia tendria que crear una clase por cada combinacion posible. Con Decorator encadeno objetos en tiempo de ejecucion y puedo combinarlos libremente.

**Donde esta el polimorfismo en este proyecto?**
En Decorator: `ConLeche`, `ConChocolate` y `TamanioGrande` son tratados como `Producto` aunque sean decoradores. En Strategy: `PagoEfectivo`, `PagoTarjeta` y `PagoMercadoPago` son tratados como `MetodoPago`.

**Que se podria mejorar?**
Agregar persistencia con base de datos, historial de pedidos, control de stock o mas productos. Pero para esta version el foco fue aplicar correctamente los patrones en un proyecto simple y defendible.
