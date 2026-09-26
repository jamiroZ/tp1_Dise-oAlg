package Arbol_Trie;

import java.util.*;

class NodoTrie {

    Map<Character, NodoTrie> hijos;//hijo del nodo:letra lleva a otro nodo
    boolean finPalabra;//si es hoja de una palabra este es true, si no es hoja es false
    List<String> sinonimos;//sinonimos asoicados a esta palabra

    public NodoTrie() {//constructor
        hijos = new HashMap<>();
        finPalabra = false;
        sinonimos = new ArrayList<>();
    }
}


public class Arbol_trie {

    private NodoTrie raiz;

    public Arbol_trie() {
        raiz = new NodoTrie();
    }

    public void insertar(String palabra) {

        NodoTrie actual = raiz;//comenzamos desde la raiz
        //convierte la palabra en minusculas y combierte el string en un array de caracteres para recorrer letra por letra
        for (char letra : palabra.toLowerCase().toCharArray()) {
            //cada nodo puede tener distintos hijos identificados por una letra.
            if (!actual.hijos.containsKey(letra)) {//existe la letra en el nodo actual, si no existe se crea un nuevo nodo
                //si no existe la letra en el nodo actual, se crea un nuevo nodo
                actual.hijos.put(letra, new NodoTrie());
            }

            actual = actual.hijos.get(letra);//avanzamos al siguiente nodo correspondiente a la letra actual
        }

        actual.finPalabra = true;//marca el nodo hoja de la rama(palabra)
    }

    private NodoTrie buscarNodo(String palabra) {//busca una palabra dentro del arbol TRIE

        NodoTrie actual = raiz;//comenzamos desde la raiz
        //convierte la palabra en minusculas y combierte el string en un array de caracteres para recorrer letra por letra
        for (char letra : palabra.toLowerCase().toCharArray()) {
            // si desde el nodo actual  no existe un camino con la letra retorna null
            if (!actual.hijos.containsKey(letra)) {
                return null;
            }

            actual = actual.hijos.get(letra);//sigue avanzando en la rama de la palabra
        }

        if (!actual.finPalabra) {//si la palabra no termina y no es hoja retorna null
            return null;
        }

        return actual;//devuelve lenodo si llego al final es hoja y es la palabra retorna el nodo
    }

    public boolean agregarSinonimo(String palabra, String sinonimo) {

        NodoTrie nodo = buscarNodo(palabra);//busca la palabra en el arbol y obtiene el nodo hoja

        if (nodo == null) {//si no existe la palabra retorna falso
            return false;
        }

        nodo.sinonimos.add(sinonimo);//agrega el sinonimo en el nodo hoja de la palabra

        return true;
    }

    public void mostrarSinonimos(String palabra) {

        NodoTrie nodo = buscarNodo(palabra);//buscamos la palabra y obtenemos el nodo final de la palabra

        if (nodo == null) {
            System.out.println( "La palabra no existe en el diccionario.");
            return;
        }

        System.out.println( "Sinónimos de \"" + palabra + "\":" );

        if (nodo.sinonimos.isEmpty()) {
            System.out.println("No tiene sinónimos.");
            return;
        }

        for (String sinonimo : nodo.sinonimos) {
            System.out.println("- " + sinonimo);
        }
    }

    public void listarPalabras() {
        listarPalabrasRecursivo(raiz, "");
    }

    private void listarPalabrasRecursivo( NodoTrie nodo, String palabraActual) {

        if (nodo.finPalabra) {
            System.out.println(palabraActual);
        }

        for (Map.Entry<Character, NodoTrie> entrada: nodo.hijos.entrySet()) {

            char letra = entrada.getKey();

            NodoTrie siguiente = entrada.getValue();

            listarPalabrasRecursivo( siguiente,palabraActual + letra );
        }
    }
}

