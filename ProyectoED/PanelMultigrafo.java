// PanelMultigrafo.java
// Dibuja el multigrafo del Ejercicio 2.
// Vertices: A, B, C, D.
// Varias aristas entre los mismos vertices se dibujan como curvas
// con puntos de control distintos para que no se superpongan.

import javax.swing.*;
import java.awt.*;
import java.awt.geom.QuadCurve2D;

public class PanelMultigrafo extends JPanel {

    private Vertice[] vertices;
    private Arista[] aristas;

    public PanelMultigrafo() {
        setBackground(Color.WHITE);

        vertices = new Vertice[] {
            new Vertice("A", 150, 120),  // 0
            new Vertice("B", 450, 120),  // 1
            new Vertice("C", 150, 380),  // 2
            new Vertice("D", 450, 380)   // 3
        };

        // Las aristas curvas usan puntos de control para separarse.
        // Para A-B (3 aristas): una arriba, una recta, una abajo.
        // Para C-D (2 aristas): una arriba y una abajo.
        aristas = new Arista[] {
            new Arista(0, 1, 300, 40),    // A-B curva superior
            new Arista(0, 1),             // A-B recta
            new Arista(0, 1, 300, 200),   // A-B curva inferior
            new Arista(0, 2),             // A-C
            new Arista(2, 3, 300, 300),   // C-D curva superior
            new Arista(2, 3, 300, 460),   // C-D curva inferior
            new Arista(1, 2)              // B-C
        };
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Dibujar aristas
        g2.setColor(Color.BLACK);
        for (Arista a : aristas) {
            Vertice o = vertices[a.origen];
            Vertice d = vertices[a.destino];
            if (a.curva) {
                QuadCurve2D q = new QuadCurve2D.Float(o.x, o.y, a.cx, a.cy, d.x, d.y);
                g2.draw(q);
            } else {
                g2.drawLine(o.x, o.y, d.x, d.y);
            }
        }

        // Dibujar vertices
        for (Vertice v : vertices) {
            g2.setColor(new Color(255, 200, 150));
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
