// Main.java
// Ventana principal del programa.
// Contiene un JTabbedPane con las 4 pestanas de los ejercicios
// y un menu de ayuda que muestra como funciona el programa.

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Font;

public class Main {
    public static void main(String[] args) {
        JFrame ventana = new JFrame("Trabajo Colaborativo - Estructura de Datos");
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setSize(900, 650);

        // Menu de ayuda
        JMenuBar barraMenu = new JMenuBar();
        JMenu menuAyuda = new JMenu("Ayuda");
        JMenuItem itemComoFunciona = new JMenuItem("Como funciona");
        itemComoFunciona.addActionListener(e -> mostrarExplicacion(ventana));
        menuAyuda.add(itemComoFunciona);
        barraMenu.add(menuAyuda);
        ventana.setJMenuBar(barraMenu);

        // Panel con pestañas
        JTabbedPane pestañas = new JTabbedPane();
        pestañas.addTab("EJ1 Grafo Dirigido", new PanelGrafoDirigido());
        pestañas.addTab("EJ2 Multigrafo", new PanelMultigrafo());
        pestañas.addTab("EJ3 Grafo Complejo", new PanelGrafoComplejo());
        pestañas.addTab("EJ4 Listas Adyacencia", new PanelListasAdyacencia());

        ventana.add(pestañas);
        ventana.setLocationRelativeTo(null); // Centrar en pantalla
        ventana.setVisible(true);
    }

    // Muestra una ventana con la explicacion de como funciona el programa.
    private static void mostrarExplicacion(JFrame padre) {
        JDialog dialogo = new JDialog(padre, "Como funciona el programa", true);
        dialogo.setSize(600, 500);
        dialogo.setLocationRelativeTo(padre);

        JTextArea texto = new JTextArea();
        texto.setEditable(false);
        texto.setFont(new Font("Monospaced", Font.PLAIN, 12));
        texto.setText(
            "COMO FUNCIONA EL PROGRAMA\n" +
            "=========================\n\n" +
            "Este programa tiene 4 pestanas (ejercicios). Usa el menu superior\n" +
            "para cambiar entre ellas.\n\n" +

            "EJ1 - Grafo dirigido:\n" +
            "- Muestra un grafo con vertices Barranquilla, Cucuta, Medellin,\n" +
            "  Bogota y Cali.\n" +
            "- Las aristas dirigidas se dibujan con flecha calculada con\n" +
            "  trigonometria (atan2).\n" +
            "- La arista Cali -> Barranquilla se dibuja como arco curvo.\n\n" +

            "EJ2 - Multigrafo:\n" +
            "- Vertices A, B, C, D con varias aristas entre los mismos vertices.\n" +
            "- Las aristas paralelas se separan usando curvas cuadraticas\n" +
            "  (QuadCurve2D) con distintos puntos de control.\n\n" +

            "EJ3 - Grafo complejo:\n" +
            "- 6 vertices numerados del 1 al 6 en forma de hexagono.\n" +
            "- Las posiciones se calculan con seno y coseno.\n" +
            "- Las aristas se guardan en una lista y se dibujan con un ciclo.\n\n" +

            "EJ4 - Listas de adyacencia:\n" +
            "- Permite crear un grafo agregando vertices y aristas.\n" +
            "- Calcula el numero de aristas del grafo.\n" +
            "- Dado un vertice, encuentra su componente conexa (BFS) y\n" +
            "  calcula el grado del grafo de esa componente.\n" +
            "- Muestra las listas de adyacencia y dibuja el grafo.\n" +
            "- Incluye un grafo de ejemplo con 2 componentes conexas.\n\n" +
            "Compilar y ejecutar:\n" +
            "  javac *.java\n" +
            "  java Main\n"
        );
        texto.setCaretPosition(0);

        JButton botonCerrar = new JButton("Cerrar");
        botonCerrar.addActionListener(e -> dialogo.dispose());
        JPanel panelBoton = new JPanel();
        panelBoton.add(botonCerrar);

        dialogo.add(new JScrollPane(texto), BorderLayout.CENTER);
        dialogo.add(panelBoton, BorderLayout.SOUTH);
        dialogo.setVisible(true);
    }
}
