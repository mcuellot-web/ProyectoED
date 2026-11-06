// PanelGrafoComplejo.java
// Dibuja un grafo complejo de 6 vertices (1..6) en forma de hexagono.
// Las posiciones se calculan con seno y coseno.
// Las aristas se guardan en una lista y se dibujan con un ciclo.

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class PanelGrafoComplejo extends JPanel {

    private Vertice[] vertices;
    private ArrayList<Arista> aristas;

    public PanelGrafoComplejo() {
        setBackground(Color.WHITE);

        int cx = 400;   // Centro X
        int cy = 300;   // Centro Y
        int r = 160;    // Radio del hexagono

        // Angulos (en grados) para que 1 y 2 queden arriba,
        // 3 a la izquierda, 4 a la derecha, 5 y 6 abajo.
        int[] angulos = {120, 60, 180, 0, 240, 300};

        vertices = new Vertice[6];
        for (int i = 0; i < 6; i++) {
            double ang = Math.toRadians(angulos[i]);
            int x = cx + (int) (r * Math.cos(ang));
            int y = cy - (int) (r * Math.sin(ang)); // restamos porque Y crece hacia abajo
            vertices[i] = new Vertice(String.valueOf(i + 1), x, y);
        }

        // Guardar todas las aristas posibles (grafo completo K6) en una lista.
        aristas = new ArrayList<Arista>();
        for (int i = 0; i < 6; i++) {
            for (int j = i + 1; j < 6; j++) {
                aristas.add(new Arista(i, j));
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Dibujar aristas con un ciclo
        g2.setColor(Color.GRAY);
        for (Arista a : aristas) {
            Vertice o = vertices[a.origen];
            Vertice d = vertices[a.destino];
            g2.drawLine(o.x, o.y, d.x, d.y);
        }

        // Dibujar vertices
        for (Vertice v : vertices) {
            g2.setColor(new Color(144, 238, 144));
            g2.fillOval(v.x - v.radio, v.y - v.radio, v.radio * 2, v.radio * 2);
            g2.setColor(Color.BLACK);
            g2.drawOval(v.x - v.radio, v.y - v.radio, v.radio * 2, v.radio * 2);
            FontMetrics fm = g2.getFontMetrics();
            int tx = v.x - fm.stringWidth(v.nombre) / 2;
            int ty = v.y + fm.getAscent() / 2 - 2;
            g2.drawString(v.nombre, tx, ty);
        }
    }
}
