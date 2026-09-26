package Conjuntos_Disjuntos;

public class MainDisjuntos {

    public static void main(String[] args) {

        ConjuntoDisjunto conjuntos = new ConjuntoDisjunto(10);

        // Creamos algunos conjuntos
        conjuntos.union(0, 1);
        conjuntos.union(1, 2);

        conjuntos.union(3, 4);
        conjuntos.union(4, 5);

        conjuntos.union(6, 7);

        // Probamos mismoConjunto
        System.out.println("¿0 y 2 pertenecen al mismo conjunto?");
        System.out.println(conjuntos.mismoConjunto(0, 2));

        System.out.println();

        System.out.println("¿0 y 3 pertenecen al mismo conjunto?");
        System.out.println(conjuntos.mismoConjunto(0, 3));

        System.out.println();

        System.out.println("¿3 y 5 pertenecen al mismo conjunto?");
        System.out.println(conjuntos.mismoConjunto(3, 5));

        System.out.println();

        System.out.println("¿6 y 7 pertenecen al mismo conjunto?");
        System.out.println(conjuntos.mismoConjunto(6, 7));

        System.out.println();

        // Unimos los conjuntos {0,1,2} y {3,4,5}
        conjuntos.union(2, 3);

        System.out.println("Después de unir 2 y 3:");

        System.out.println("¿0 y 5 pertenecen al mismo conjunto?");
        System.out.println(conjuntos.mismoConjunto(0, 5));

        System.out.println();

        // Mostramos las raíces
        System.out.println("Raíz de 0: " + conjuntos.find(0));
        System.out.println("Raíz de 5: " + conjuntos.find(5));
        System.out.println("Raíz de 6: " + conjuntos.find(6));
    }
}
