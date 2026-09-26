package Grafos;
import java.util.*;

public class Grafo {

    // Colores utilizados durante los recorridos
    public enum Color {
        BLANCO,
        GRIS,
        NEGRO
    }

    private int n;                    // cantidad de vértices
    private boolean dirigido;         // indica si el grafo es dirigido
    private int[][] matriz;           // matriz de adyacencia

    //atributos para BFS
    private Color[] color;
    private int[] padre;
    private int[] nivel;

    //atributos para DFS
    private int[] tiempoEntrada;
    private int[] tiempoSalida;
    private int tiempo;

    // Constructor
    public Grafo(int n, boolean dirigido) {
        this.n = n;
        this.dirigido = dirigido;
        this.matriz = new int[n][n];

        this.color = new Color[n];
        this.padre = new int[n];
        this.nivel = new int[n];

        this.tiempoEntrada = new int[n];
        this.tiempoSalida = new int[n];
    }

    // AGREGAR ARISTA
    public void agregarArista(int origen, int destino) {

        matriz[origen][destino] = 1;
        // Si no es dirigido, también agregamos la vuelta
        if (!dirigido) {
            matriz[destino][origen] = 1;
        }
    }

    // MOSTRAR MATRIZ
    public void mostrarMatriz() {

        System.out.println("\nMatriz de adyacencia:");

        System.out.print("    ");
        for (int i = 0; i < n; i++) {
            System.out.print(i + " ");
        }

        System.out.println();

        for (int i = 0; i < n; i++) {
            System.out.print(i + " | ");
            for (int j = 0; j < n; j++) {
                System.out.print(matriz[i][j] + " ");
            }
            System.out.println();
        }
    }

    // INICIALIZAR BFS
    private void inicializarBFS() {

        for (int i = 0; i < n; i++) {

            color[i] = Color.BLANCO;
            padre[i] = -1;
            nivel[i] = -1;
        }
    }

    // BFS
    public void BFS(int raiz) {

        inicializarBFS();

        Queue<Integer> cola = new LinkedList<>();

        // La raíz se descubre
        color[raiz] = Color.GRIS;
        nivel[raiz] = 0;
        padre[raiz] = -1;

        cola.add(raiz);
        System.out.println("\n===== BFS =====");

        while (!cola.isEmpty()) {

            int u = cola.remove();
            System.out.println("\nProcesando nodo: " + u);

            // Revisamos todos los posibles adyacentes
            for (int v = 0; v < n; v++) {

                if (matriz[u][v] == 1) {

                    System.out.println(  "  Revisando arista " + u + " -> " + v);

                    // Si v todavía no fue descubierto
                    if (color[v] == Color.BLANCO) {

                        color[v] = Color.GRIS;
                        padre[v] = u;
                        nivel[v] = nivel[u] + 1;

                        cola.add(v);

                        System.out.println("    " + v + " pasa a GRIS, padre = " + u + ", nivel = " + nivel[v]  );
                    }
                }
            }

            // Terminamos de revisar todos los adyacentes
            color[u] = Color.NEGRO;
            System.out.println(  "  " + u + " pasa a NEGRO"  );
        }
    }

    // MOSTRAR RESULTADO DEL BFS
    public void mostrarResultadoBFS() {

        System.out.println("\n===== RESULTADO BFS =====");
        System.out.println(  "Nodo\tColor\tPadre\tNivel"  );

        for (int i = 0; i < n; i++) {

            System.out.println(  i + "\t" +  color[i] + "\t" +padre[i] + "\t" +   nivel[i] );
        }
    }

    // MOSTRAR FORESTA DE BFS
    public void mostrarForesta() {

        System.out.println("\n===== FORESTA DE RECORRIDO =====");
        boolean tieneAristas = false;

        for (int v = 0; v < n; v++) {
            if (padre[v] != -1) {
                System.out.println( padre[v] + " -> " + v);
                tieneAristas = true;
            }
        }

        if (!tieneAristas) {
            System.out.println("No hay aristas en la foresta.");
        }
    }

    // VERIFICAR SI EL GRAFO ES CONEXO
    public boolean esConexo() {
        BFS(0);// Hacemos BFS desde el nodo 0

        for (int i = 0; i < n; i++) {

            if (color[i] == Color.BLANCO) {
                return false;
            }
        }

        return true;
    }

    // CLASIFICACIÓN DE ARISTAS
    public void clasificarAristas() {

        System.out.println("\n===== CLASIFICACIÓN DE ARISTAS =====");

        for (int u = 0; u < n; u++) {
            for (int v = 0; v < n; v++) {

                if (matriz[u][v] == 1) {

                    if (padre[v] == u) {// Arista de la foresta
                        System.out.println(  u + " -> " + v + " : ARISTA DE LA FORESTA");
                    } else if (esAncestro(v, u)) {// Arista hacia atrás
                        System.out.println(   u + " -> " + v +" : ARISTA HACIA ATRÁS");
                    }else if (esAncestro(u, v)) {// Arista hacia adelante

                        System.out.println( u + " -> " + v +" : ARISTA HACIA ADELANTE");
                    } else {// Arista que cruza
                        System.out.println( u + " -> " + v + " : ARISTA QUE CRUZA");
                    }

                }
            }
        }
    }

    // SABER SI UN NODO ES ANCESTRO DE OTRO
    private boolean esAncestro(int posibleAncestro, int nodo) {

        int actual = padre[nodo];

        while (actual != -1) {
            if (actual == posibleAncestro) {
                return true;
            }
            actual = padre[actual];
        }

        return false;
    }

    // DFS
    public void DFS(int raiz) {

            inicializarBFS();
            tiempo = 0;

            for (int i = 0; i < n; i++) {
                tiempoEntrada[i] = 0;
                tiempoSalida[i] = 0;
            }

            System.out.println("\n===== DFS =====");
            DFSRecursivo(raiz);
    }

    private void DFSRecursivo(int u) {

        color[u] = Color.GRIS;
        tiempo++;
        tiempoEntrada[u] = tiempo;

        System.out.println("Visitando " + u);

        for (int v = 0; v < n; v++) {
            if (matriz[u][v] == 1 && color[v] == Color.BLANCO) {
                padre[v] = u;
                DFSRecursivo(v);
            }
        }

        color[u] = Color.NEGRO;
        tiempo++;
        tiempoSalida[u] = tiempo;

        System.out.println("Terminando " + u);
    }
}