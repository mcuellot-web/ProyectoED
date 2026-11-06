// PanelListasAdyacencia.java
// Interfaz del Ejercicio 4: listas de adyacencia con BFS.
// Permite agregar vertices, aristas, consultar componente conexa y grado.

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class PanelListasAdyacencia extends JPanel {

    private GrafoListaAdyacencia grafo;
    private JTextField campoVertice;
    private JTextField campoOrigen;
    private JTextField campoDestino;
    private JTextField campoConsulta;
    private JTextArea areaResultado;
    private JPanel panelDibujo;

    public PanelListasAdyacencia() {
        setLayout(new BorderLayout());
        grafo = new GrafoListaAdyacencia();

        // Grafo de ejemplo precargado con 2 componentes conexas:
        // Componente 1: 1-2-3     Componente 2: 4-5
        for (int v : new int[]{1, 2, 3, 4, 5}) {
            grafo.agregarVertice(v);
        }
        grafo.agregarArista(1, 2);
        grafo.agregarArista(2, 3);
        grafo.agregarArista(4, 5);

        // Panel superior con controles
        JPanel panelControles = new JPanel();
        panelControles.setLayout(new GridLayout(4, 1));

        // Fila 1: agregar vertice
        JPanel fila1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        fila1.add(new JLabel("Vertice:"));
        campoVertice = new JTextField(5);
        fila1.add(campoVertice);
        JButton btnVertice = new JButton("Agregar vertice");
        fila1.add(btnVertice);
        panelControles.add(fila1);

        // Fila 2: agregar arista
        JPanel fila2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        fila2.add(new JLabel("Origen:"));
        campoOrigen = new JTextField(5);
        fila2.add(campoOrigen);
        fila2.add(new JLabel("Destino:"));
        campoDestino = new JTextField(5);
        fila2.add(campoDestino);
        JButton btnArista = new JButton("Agregar arista");
        fila2.add(btnArista);
        panelControles.add(fila2);

        // Fila 3: consultar
        JPanel fila3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        fila3.add(new JLabel("Vertice a consultar:"));
        campoConsulta = new JTextField(5);
        campoConsulta.setText("1");
        fila3.add(campoConsulta);
        JButton btnConsultar = new JButton("Calcular");
        fila3.add(btnConsultar);
        panelControles.add(fila3);

        // Fila 4: botones extra
        JPanel fila4 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnMostrar = new JButton("Mostrar listas");
        fila4.add(btnMostrar);
        JButton btnLimpiar = new JButton("Limpiar resultados");
        fila4.add(btnLimpiar);
        panelControles.add(fila4);

        add(panelControles, BorderLayout.NORTH);

        areaResultado = new JTextArea();
        areaResultado.setEditable(false);
        areaResultado.setFont(new Font("Monospaced", Font.PLAIN, 13));

        // Panel que dibuja el grafo creado a partir de las listas de adyacencia
        panelDibujo = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                dibujarGrafo(g2);
            }
        };
        panelDibujo.setBackground(Color.WHITE);

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                new JScrollPane(areaResultado), panelDibujo);
        split.setDividerLocation(420);
        add(split, BorderLayout.CENTER);

        // Acciones
        btnVertice.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int v = Integer.parseInt(campoVertice.getText().trim());
                    grafo.agregarVertice(v);
                    areaResultado.append("Vertice " + v + " agregado.\n");
                    panelDibujo.repaint();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(PanelListasAdyacencia.this, "Numero invalido.");
                }
            }
        });

        btnArista.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int u = Integer.parseInt(campoOrigen.getText().trim());
                    int v = Integer.parseInt(campoDestino.getText().trim());
                    grafo.agregarArista(u, v);
                    areaResultado.append("Arista " + u + " - " + v + " agregada.\n");
                    panelDibujo.repaint();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(PanelListasAdyacencia.this, "Numeros invalidos.");
                }
            }
        });

        btnConsultar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int v = Integer.parseInt(campoConsulta.getText().trim());
                    Set<Integer> comp = grafo.componenteConexa(v);
                    int aristas = grafo.numeroDeAristas();
                    int grado = grafo.gradoComponente(v);
                    areaResultado.append("\n--- Consulta para vertice " + v + " ---\n");
                    areaResultado.append("Numero de aristas del grafo: " + aristas + "\n");
                    areaResultado.append("Componente conexa: " + comp + "\n");
                    areaResultado.append("Grado del grafo de la componente: " + grado + "\n");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(PanelListasAdyacencia.this, "Numero invalido.");
                }
            }
        });

        btnMostrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                areaResultado.append("\n--- Listas de adyacencia ---\n");
                areaResultado.append(grafo.mostrarListas());
            }
        });

        btnLimpiar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                areaResultado.setText("");
            }
        });

        // Mostrar estado inicial
        areaResultado.append("Grafo de ejemplo cargado.\n");
        areaResultado.append("Componente 1: 1-2-3   Componente 2: 4-5\n");
        areaResultado.append(grafo.mostrarListas());
        panelDibujo.repaint();
    }

    // Dibuja el grafo a partir de las listas de adyacencia.
    private void dibujarGrafo(Graphics2D g2) {
        List<Integer> vertices = new ArrayList<Integer>(grafo.vertices());
        int n = vertices.size();
        if (n == 0) return;

        int w = panelDibujo.getWidth();
        int h = panelDibujo.getHeight();
        if (w <= 0 || h <= 0) return;

        int cx = w / 2;
        int cy = h / 2;
        int r = Math.min(w, h) / 2 - 40;
        if (r < 20) r = 20;

        // Posiciones de los vertices en un circulo
        java.util.Map<Integer, Point> pos = new java.util.HashMap<Integer, Point>();
        for (int i = 0; i < n; i++) {
            int v = vertices.get(i);
            double ang = 2 * Math.PI * i / n;
            int x = cx + (int) (r * Math.cos(ang));
            int y = cy - (int) (r * Math.sin(ang));
            pos.put(v, new Point(x, y));
        }

        // Dibujar aristas (sin duplicar)
        g2.setColor(Color.GRAY);
        for (int u : vertices) {
            for (int v : grafo.vecinos(u)) {
                if (u < v) {
                    Point pu = pos.get(u);
                    Point pv = pos.get(v);
                    g2.drawLine(pu.x, pu.y, pv.x, pv.y);
                }
            }
        }

        // Dibujar vertices
        for (int v : vertices) {
            Point p = pos.get(v);
            g2.setColor(new Color(200, 220, 255));
            g2.fillOval(p.x - 18, p.y - 18, 36, 36);
            g2.setColor(Color.BLACK);
            g2.drawOval(p.x - 18, p.y - 18, 36, 36);
            String s = String.valueOf(v);
            FontMetrics fm = g2.getFontMetrics();
            g2.drawString(s, p.x - fm.stringWidth(s) / 2, p.y + fm.getAscent() / 2 - 2);
        }
    }

}

