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
    private void inicializar() {

        for (int i = 0; i < n; i++) {
            color[i] = Color.BLANCO;
            padre[i] = -1;
            nivel[i] = -1;
        }
    }

    // BFS
    public void BFS(int raiz) {

        inicializar();
        Queue<Integer> cola = new LinkedList<>();

        // La raíz se descubre
        color[raiz] = Color.GRIS;
        nivel[raiz] = 0;
        padre[raiz] = -1;

        cola.add(raiz);//colocamos la raiz en la cola
        System.out.println("\n===== BFS =====");

        while (!cola.isEmpty()) {//MIENTRAS queden nodos pendientes seguir (hasta [])

            int u = cola.remove();//obtiene el siguiente nodo del nivel(primer nodo de la cola)
            System.out.println("\nProcesando nodo: " + u);
            
            for (int v = 0; v < n; v++) {// Revisamos todos los posibles adyacentes al nodo de la cola

                if (matriz[u][v] == 1) {//revisa la matriz de adyacencia

                    System.out.println(  "  Revisando arista " + u + " -> " + v);
                    
                    if (color[v] == Color.BLANCO) {// Si v todavía no fue descubierto

                        color[v] = Color.GRIS;//colocamos en nodo descubierto en gris
                        padre[v] = u;//si llegamos de u --> v entonces construimos el arbol/foresta 
                        nivel[v] = nivel[u] + 1;//si el padre estaba en un nivel anterior sumamos un nivel mas al hijo

                        cola.add(v);//colocamos los nodos de ese nivel en la cola.

                        System.out.println("    " + v + " pasa a GRIS, padre = " + u + ", nivel = " + nivel[v]  );
                    }
                }
            }

            // Terminamos de revisar todos los adyacentes
            color[u] = Color.NEGRO;//colocamos el nodo de la cola en negro
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
            if (padre[v] != -1) {//sino es el nodo 0 que apunta al nodo raiz que muestre
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

            inicializar();//INICIA color=BLANCO ,padre[]=-1 y nivel[]=0
            tiempo = 0;

            for (int i = 0; i < n; i++) {
                tiempoEntrada[i] = 0;
                tiempoSalida[i] = 0;
            }

            System.out.println("\n===== DFS =====");
            DFSRecursivo(raiz);
    }

    private void DFSRecursivo(int u) {

        color[u] = Color.GRIS;//Marca el nodo como conocido
        tiempo++;
        tiempoEntrada[u] = tiempo;//tiempo de entrada

        System.out.println("Visitando " + u);

        for (int v = 0; v < n; v++) {//visitamos todos todos los adyacentes no conocidos
            if (matriz[u][v] == 1 && color[v] == Color.BLANCO) {//existe un adyacente no visitado 
                padre[v] = u;//guardamos el nodo padre del adyacente desconocido
                DFSRecursivo(v);//sigue buscando los hijos de ese nodo hasta llegar a la hoja
            }
        }

        color[u] = Color.NEGRO;//visito todos los hijos del nodo 
        tiempo++;
        tiempoSalida[u] = tiempo;//tiempo de salida

        System.out.println("Terminando " + u);
    }
}