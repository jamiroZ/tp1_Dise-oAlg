package Arbol_Trie;

public class MianTrie {
    public static void main(String[] args) {

        Arbol_trie diccionario = new Arbol_trie();

        // Agregar palabras
        diccionario.insertar("rápido");
        diccionario.insertar("feliz");
        diccionario.insertar("casa");
        diccionario.insertar("inteligente");

        // Agregar sinónimos
        diccionario.agregarSinonimo("rápido", "veloz");
        diccionario.agregarSinonimo("rápido", "ligero");

        diccionario.agregarSinonimo("feliz", "alegre");
        diccionario.agregarSinonimo("feliz", "contento");

        diccionario.agregarSinonimo("casa", "vivienda");

        diccionario.agregarSinonimo(
            "inteligente",
            "astuto"
        );


        // Mostrar sinónimos
        System.out.println("=== SINÓNIMOS ===");

        diccionario.mostrarSinonimos("rápido");

        System.out.println();

        diccionario.mostrarSinonimos("feliz");


        // Listar palabras
        System.out.println();
        System.out.println("=== PALABRAS DEL DICCIONARIO ===");

        diccionario.listarPalabras();
    }
}
