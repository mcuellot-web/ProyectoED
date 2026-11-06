// Arista.java
// Clase que representa una arista entre dos vertices.
// Si "curva" es true se dibuja como una curva cuadratica (QuadCurve2D)
// usando el punto de control (cx, cy). Si es false se dibuja como linea recta.

public class Arista {
    public int origen;   // Indice del vertice origen en el arreglo de vertices
    public int destino;  // Indice del vertice destino en el arreglo de vertices
    public boolean curva; // Indica si la arista se dibuja curva
    public int cx;       // Coordenada X del punto de control (solo si es curva)
    public int cy;       // Coordenada Y del punto de control (solo si es curva)

    // Constructor para arista recta
    public Arista(int origen, int destino) {
        this.origen = origen;
        this.destino = destino;
        this.curva = false;
        this.cx = 0;
        this.cy = 0;
    }

    // Constructor para arista curva
    public Arista(int origen, int destino, int cx, int cy) {
        this.origen = origen;
        this.destino = destino;
        this.curva = true;
        this.cx = cx;
        this.cy = cy;
    }
}
