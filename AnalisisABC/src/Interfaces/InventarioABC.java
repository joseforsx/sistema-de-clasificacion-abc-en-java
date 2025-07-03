package Interfaces;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Comparator;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * José Ángel Trevilla León
 */
public class InventarioABC extends javax.swing.JFrame {

    private JTextField txtNombre, txtCantidad, txtCosto;
    private DefaultTableModel modeloTabla, modeloResumen;
    private List<Producto> productos = new ArrayList<>();

    // Método Constructor. Inicializa la interfaz gráfica y configura los componentes.
    public InventarioABC() {

        // Inicialización de la interfaz gráfica
        initComponents();
        setTitle("Análisis ABC");
        setSize(800, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panelEntrada = new JPanel(new GridLayout(4, 2));
        panelEntrada.add(new JLabel("Nombre del Artículo:"));
        txtNombre = new JTextField();
        panelEntrada.add(txtNombre);

        panelEntrada.add(new JLabel("Cantidad:"));
        txtCantidad = new JTextField();
        panelEntrada.add(txtCantidad);

        panelEntrada.add(new JLabel("Costo Unitario:"));
        txtCosto = new JTextField();
        panelEntrada.add(txtCosto);

        JButton btnAgregar = new JButton("Agregar Producto");
        panelEntrada.add(btnAgregar);

        add(panelEntrada, BorderLayout.NORTH);

        // Tabla principal para mostrar los productos
        String[] columnas = {"Nombre", "Cantidad", "Costo Unitario", "Valor Total", "Clasificación"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        JTable tabla = new JTable(modeloTabla);
        
        tabla.setShowGrid(true); 
        tabla.setGridColor(Color.BLACK); 
        tabla.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        
        tabla.setBackground(new Color(230, 240, 255));
        tabla.setGridColor(Color.GRAY); 
        tabla.setSelectionBackground(new Color(100, 150, 255)); 
        
        add(new JScrollPane(tabla), BorderLayout.SOUTH);

        // Tabla resumen para mostrar el análisis ABC
        String[] columnasResumen = {"Tipo", "Productos", "Porcentaje", "Inversión", "Porcentaje"};
        modeloResumen = new DefaultTableModel(columnasResumen, 0);
        JTable tablaResumen = new JTable(modeloResumen);

        tablaResumen.setShowGrid(true);
        tablaResumen.setGridColor(Color.BLACK); 
        tablaResumen.setBorder(BorderFactory.createLineBorder(Color.BLACK)); 
        
        tablaResumen.setBackground(new Color(240, 240, 240)); 
        tablaResumen.setGridColor(Color.DARK_GRAY);
        tablaResumen.setSelectionBackground(new Color(180, 200, 255));
        
        add(new JScrollPane(tablaResumen), BorderLayout.CENTER);


        // Botón para agregar productos
        btnAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarProducto();
            }
        });

        setLocationRelativeTo(null);

    }

    // Método para agregar un producto al inventario. Recibe los datos de los campos de texto y los valida.
    private void agregarProducto() {
        String nombre = txtNombre.getText();
        String cantidadTexto = txtCantidad.getText();
        String costoTexto = txtCosto.getText();

        // Verificar que los campos no estén vacíos
        if (nombre.isEmpty() || cantidadTexto.isEmpty() || costoTexto.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Todos los campos deben ser completados.", "Error", JOptionPane.ERROR_MESSAGE);
            return; // Detener el proceso si hay campos vacíos
        }

        // Convertir la cantidad y el costo a sus tipos correspondientes (entero y doble)
        try {
            int cantidad = Integer.parseInt(cantidadTexto);
            double costo = Double.parseDouble(costoTexto);

            // Crear y agregar el producto a nuestra tabla
            Producto producto = new Producto(nombre, cantidad, costo);
            productos.add(producto);

            // Invocar otros métodos para clasificar y actualizar las tablas
            clasificarProductos();
            actualizarTabla();
            limpiarCampos();
            actualizarResumen();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Cantidad y Costo deben ser números válidos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método para clasificar los productos según el Análisis ABC.  
    // Clasifica los productos en tres categorías: A, B y C.
    private void clasificarProductos() {
        // Ordenar los productos por costo unitario (de mayor a menor)      
        productos.sort(Comparator.comparingDouble((Producto p) -> -p.costoUnitario));

        int totalProductos = productos.size();
        int cantidadA = (int) Math.ceil(totalProductos * 0.20); // 20% de productos
        int cantidadB = (int) Math.ceil(totalProductos * 0.15); // 15% de productos

        // Clasificar los productos en A, B y C
        for (int i = 0; i < totalProductos; i++) {
            if (i < cantidadA) {
                productos.get(i).clasificacion = "A";
            } else if (i < cantidadA + cantidadB) {
                productos.get(i).clasificacion = "B";
            } else {
                productos.get(i).clasificacion = "C";
            }
        }

    }

    // Método para actualizar la tabla principal con la información actualizada de los productos.
    private void actualizarTabla() {
        modeloTabla.setRowCount(0);
        for (Producto p : productos) {
            modeloTabla.addRow(new Object[]{p.nombre, p.cantidad, p.costoUnitario, p.valorTotal, p.clasificacion});
        }
    }

    // Método para limpiar los campos de entrada después de agregar un producto.
    private void limpiarCampos() {
        txtNombre.setText("");
        txtCantidad.setText("");
        txtCosto.setText("");
    }

    // Método para actualizar la tabla resumen con el análisis ABC, mostrando el número de productos, porcentaje y valor.
    private void actualizarResumen() {
        modeloResumen.setRowCount(0);
        int countA = 0, countB = 0, countC = 0;
        double totalValor = productos.stream().mapToDouble(p -> p.valorTotal).sum();
        double valorA = 0, valorB = 0, valorC = 0;

        // Clasifica los productos según su clasificación (A, B o C) 
        // y acumula los valores correspondientes
        for (Producto p : productos) {
            switch (p.clasificacion) {
                case "A":
                    countA++;
                    valorA += p.valorTotal;
                    break;
                case "B":
                    countB++;
                    valorB += p.valorTotal;
                    break;
                case "C":
                    countC++;
                    valorC += p.valorTotal;
                    break;
            }
        }

        // Agrega los datos de clasificación a la tabla de resumen
        modeloResumen.addRow(new Object[]{"A", countA, formatPorcentaje(countA * 100.0 / productos.size()), valorA, formatPorcentaje(valorA * 100.0 / totalValor)});
        modeloResumen.addRow(new Object[]{"B", countB, formatPorcentaje(countB * 100.0 / productos.size()), valorB, formatPorcentaje(valorB * 100.0 / totalValor)});
        modeloResumen.addRow(new Object[]{"C", countC, formatPorcentaje(countC * 100.0 / productos.size()), valorC, formatPorcentaje(valorC * 100.0 / totalValor)});
        modeloResumen.addRow(new Object[]{"Total", countA + countB + countC, "100%", totalValor, "100%"});
    }

    // Método  para dar formato a un valor porcentual con dos decimales
    private String formatPorcentaje(double valor) {
        return (valor % 1 == 0) ? String.format("%.0f%%", valor) : String.format("%.2f%%", valor);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    // Método principal para ejecutar la aplicación
    public static void main(String args[]) {

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(InventarioABC.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(InventarioABC.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(InventarioABC.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(InventarioABC.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        //  SwingUtilities.invokeLater(() -> new InventarioABC().setVisible(true));
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new InventarioABC().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
