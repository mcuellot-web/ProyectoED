// GrafoListaAdyacencia.java
// Grafo representado mediante listas de adyacencia.
// Usa un Map<Integer, List<Integer>> donde la clave es el vertice
// y el valor es la lista de vertices adyacentes.

import java.util.*;

public class GrafoListaAdyacencia {

    // Lista de adyacencia: vertice -> lista de vecinos
    private Map<Integer, List<Integer>> adyacencia;
    // Contador de aristas (para no duplicar en grafos no dirigidos)
    private int numeroAristas;

    public GrafoListaAdyacencia() {
        adyacencia = new TreeMap<Integer, List<Integer>>();
        numeroAristas = 0;
    }

    // Agrega un vertice si no existe
    public void agregarVertice(int v) {
        if (!adyacencia.containsKey(v)) {
            adyacencia.put(v, new ArrayList<Integer>());
        }
    }

    // Agrega una arista no dirigida u-v
    public void agregarArista(int u, int v) {
        agregarVertice(u);
        agregarVertice(v);
        adyacencia.get(u).add(v);
        adyacencia.get(v).add(u);
        numeroAristas++;
    }

    // Numero de aristas del grafo
    public int numeroDeAristas() {
        return numeroAristas;
    }

    // Conjunto de vertices de la componente conexa que contiene al vertice dado (BFS)
    public Set<Integer> componenteConexa(int inicio) {
        Set<Integer> visitados = new TreeSet<Integer>();
        if (!adyacencia.containsKey(inicio)) {
            return visitados;
        }
        Queue<Integer> cola = new LinkedList<Integer>();
        cola.add(inicio);
        visitados.add(inicio);
        while (!cola.isEmpty()) {
            int actual = cola.poll();
            for (int vecino : adyacencia.get(actual)) {
                if (!visitados.contains(vecino)) {
                    visitados.add(vecino);
                    cola.add(vecino);
                }
            }
        }
        return visitados;
    }

    // Grado de un vertice = cantidad de aristas incidentes (tamano de su lista)
    public int gradoVertice(int v) {
        if (!adyacencia.containsKey(v)) return 0;
        return adyacencia.get(v).size();
    }

    // Grado del grafo de una componente conexa = grado maximo entre sus vertices
    public int gradoComponente(int inicio) {
        Set<Integer> comp = componenteConexa(inicio);
        int maxGrado = 0;
        for (int v : comp) {
            maxGrado = Math.max(maxGrado, gradoVertice(v));
        }
        return maxGrado;
    }

    // Devuelve los vertices
    public Set<Integer> vertices() {
        return adyacencia.keySet();
    }

    // Devuelve los vecinos de un vertice (para dibujo)
    public List<Integer> vecinos(int v) {
        if (!adyacencia.containsKey(v)) return new ArrayList<Integer>();
        return adyacencia.get(v);
    }

    // Devuelve la lista de adyacencia como texto
    public String mostrarListas() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Integer, List<Integer>> e : adyacencia.entrySet()) {
            sb.append(e.getKey()).append(" -> ").append(e.getValue()).append("\n");
        }
        return sb.toString();
    }
}
