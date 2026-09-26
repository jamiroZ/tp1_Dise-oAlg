package Conjuntos_Disjuntos;
public class ConjuntoDisjunto {
    private int[] padre;
    public ConjuntoDisjunto(int n) {
    padre = new int[n];
     for (int i = 0; i < n; i++) {
          padre[i] = i;
     }
     }
     public int find(int x) {
          while (padre[x] != x) {
               x = padre[x];
          }
          return x;
     }
     public void union(int a, int b) {
          int raizA = find(a);
          int raizB = find(b);
          if (raizA != raizB) {
               padre[raizA] = raizB;
          }
    }
    public boolean mismoConjunto(int a, int b) {
         return find(a) == find(b);
    }
}