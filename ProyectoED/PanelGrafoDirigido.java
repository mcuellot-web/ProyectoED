// PanelGrafoDirigido.java
// Dibuja el grafo dirigido del Ejercicio 1.
// Vertices: Barranquilla, Cucuta, Medellin, Bogota y Cali.

import javax.swing.*;
import java.awt.*;
import java.awt.geom.QuadCurve2D;

public class PanelGrafoDirigido extends JPanel {

    private Vertice[] vertices;
    private Arista[] aristas;

    public PanelGrafoDirigido() {
        setBackground(Color.WHITE);

        // Posiciones asignadas manualmente para que el grafo se vea ordenado.
        // Se usan coordenadas (x, y) en pixeles dentro del panel.
        vertices = new Vertice[] {
            new Vertice("Barranquilla", 420, 90),   // 0: arriba centro
            new Vertice("Cucuta",        120, 230),  // 1: izquierda
            new Vertice("Medellin",      300, 260),  // 2: centro
            new Vertice("Bogota",        520, 300),  // 3: derecha
            new Vertice("Cali",          300, 430)   // 4: abajo centro
        };

        // Aristas dirigidas (indices de vertices)
        // Bogota -> Medellin -> Barranquilla se representan con dos aristas
        aristas = new Arista[] {
            new Arista(1, 0),                          // Cucuta -> Barranquilla
            new Arista(3, 2),                          // Bogota -> Medellin
            new Arista(2, 0),                          // Medellin -> Barranquilla
            new Arista(4, 0, 150, 260),                // Cali -> Barranquilla (curva a la izquierda)
            new Arista(4, 3)                           // Cali -> Bogota
        };
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // 1) Dibujar aristas con flecha
        for (Arista a : aristas) {
            Vertice o = vertices[a.origen];
            Vertice d = vertices[a.destino];
            if (a.curva) {
                dibujarCurva(g2, o, d, a.cx, a.cy);
            } else {
                dibujarFlechaRecta(g2, o, d);
            }
        }

        // 2) Dibujar vertices (circulos con nombre) encima
        for (Vertice v : vertices) {
            g2.setColor(new Color(173, 216, 230));
            g2.fillOval(v.x - v.radio, v.y - v.radio, v.radio * 2, v.radio * 2);
            g2.setColor(Color.BLACK);
            g2.drawOval(v.x - v.radio, v.y - v.radio, v.radio * 2, v.radio * 2);
            // Centrar el texto dentro del circulo
            FontMetrics fm = g2.getFontMetrics();
            int tx = v.x - fm.stringWidth(v.nombre) / 2;
            int ty = v.y + fm.getAscent() / 2 - 2;
            g2.drawString(v.nombre, tx, ty);
        }
    }

    // Dibuja una arista recta con flecha en el destino.
    private void dibujarFlechaRecta(Graphics2D g2, Vertice o, Vertice d) {
        double dx = d.x - o.x;
        double dy = d.y - o.y;
        double dist = Math.hypot(dx, dy);
        double ux = dx / dist;
        double uy = dy / dist;

        // Inicio en el borde del circulo origen, fin en el borde del circulo destino
        int x1 = (int) (o.x + o.radio * ux);
        int y1 = (int) (o.y + o.radio * uy);
        int x2 = (int) (d.x - d.radio * ux);
        int y2 = (int) (d.y - d.radio * uy);

        g2.setColor(Color.BLACK);
        g2.drawLine(x1, y1, x2, y2);
        dibujarPuntaFlecha(g2, x2, y2, Math.atan2(dy, dx));
    }

    // Dibuja una arista curva (cuadratica) con flecha en el destino.
    private void dibujarCurva(Graphics2D g2, Vertice o, Vertice d, int cx, int cy) {
        double dx = d.x - cx;
        double dy = d.y - cy;
        double dist = Math.hypot(dx, dy);
        double ux = dx / dist;
        double uy = dy / dist;

        // El punto final se mueve un radio hacia atras desde el destino
        int x2 = (int) (d.x - d.radio * ux);
        int y2 = (int) (d.y - d.radio * uy);

        QuadCurve2D curva = new QuadCurve2D.Float(o.x, o.y, cx, cy, x2, y2);
        g2.setColor(Color.BLACK);
        g2.draw(curva);
        dibujarPuntaFlecha(g2, x2, y2, Math.atan2(dy, dx));
    }

    // Calcula y dibuja la punta de la flecha usando trigonometria.
    // angle: angulo de la tangente en el punto de llegada.
    private void dibujarPuntaFlecha(Graphics2D g2, int x, int y, double angle) {
        int largo = 15;          // Longitud de cada lado de la punta
        double phi = Math.toRadians(25); // Angulo de apertura de la punta

        int x3 = (int) (x - largo * Math.cos(angle - phi));
        int y3 = (int) (y - largo * Math.sin(angle - phi));
        int x4 = (int) (x - largo * Math.cos(angle + phi));
        int y4 = (int) (y - largo * Math.sin(angle + phi));

        g2.drawLine(x, y, x3, y3);
        g2.drawLine(x, y, x4, y4);
    }
}
