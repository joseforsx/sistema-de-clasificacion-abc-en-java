package Interfaces;
/**
 *
 * @author José Ángel Trevilla León
 */
public class Producto {

    // Atributos 
    
    String nombre;
    int cantidad;
    double costoUnitario;
    double valorTotal;
    String clasificacion;
    
    
    // Método Constructor. Inicializa los atributos
    public Producto(String nombre, int cantidad, double costoUnitario) {
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.costoUnitario = costoUnitario;
        this.valorTotal = cantidad * costoUnitario;
    }
}
