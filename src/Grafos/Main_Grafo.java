package Grafos;
import java.util.*;

public class Main_Grafo {
                public static void main(String[] args) {

                    Grafo grafo = new Grafo(10, true);

                    // Agregamos las aristas
                    grafo.agregarArista(2, 5);
                    grafo.agregarArista(5, 1);

                    grafo.agregarArista(1, 2);
                    grafo.agregarArista(2, 6);

                    grafo.agregarArista(1, 4);
                    grafo.agregarArista(1, 3);

                    grafo.agregarArista(6, 4);

                    grafo.agregarArista(3, 6);
                    grafo.agregarArista(3, 8);
                    grafo.agregarArista(3, 7);

                    grafo.agregarArista(8, 9);
                    grafo.agregarArista(9, 6);

                    grafo.agregarArista(2, 7);

                    grafo.agregarArista(4, 7);

                    grafo.agregarArista(7, 3);

                    // MATRIZ
                    grafo.mostrarMatriz();

                    // BFS
                    grafo.BFS(1);

                    grafo.mostrarResultadoBFS();

                    grafo.mostrarForesta();
                    
                    grafo.DFS(1);
                    // CONEXIDAD

                    System.out.println("\n¿El grafo es conexo? " +grafo.esConexo());


                    // CLASIFICACIÓN DE ARISTAS
                    grafo.clasificarAristas();

                    // DFS
                    grafo.DFS(0);
                }
}

