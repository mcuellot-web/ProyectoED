// Vertice.java
// Clase que representa un vertice de un grafo.
// Guarda el nombre del vertice y su posicion (x, y) en la ventana.

public class Vertice {
    public String nombre; // Nombre que se mostrara en pantalla
    public int x;         // Posicion horizontal en pixeles
    public int y;         // Posicion vertical en pixeles
    public int radio;     // Radio del circulo que lo representa

    public Vertice(String nombre, int x, int y) {
        this.nombre = nombre;
        this.x = x;
        this.y = y;
        this.radio = 28;
    }
}
