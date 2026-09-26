package Heap_Binomial;
import java.util.NoSuchElementException;

/**
 * Nodo del monticulo binomial.
 * hijo apunta al primer hijo (el ultimo agregado) y hermano enlaza
 * a los hijos de un mismo padre entre si, como una lista simple.
 */
class NodoBinomial<T extends Comparable<T>> {
    T clave;
    int grado;
    NodoBinomial<T> padre;
    NodoBinomial<T> hijo;
    NodoBinomial<T> hermano;

    NodoBinomial(T clave) {
        this.clave = clave;
        this.grado = 0;
        this.padre = null;
        this.hijo = null;
        this.hermano = null;
    }
}

/**
 * Representa un arbol binomial dentro de la lista de raices del heap.
 * "siguiente" enlaza con el proximo arbol de la lista, ordenados por grado.
 */
class ArbolBinomial<T extends Comparable<T>> {
    NodoBinomial<T> raiz;
    ArbolBinomial<T> siguiente;

    ArbolBinomial(NodoBinomial<T> raiz) {
        this.raiz = raiz;
        this.siguiente = null;
    }
}

/**
 * Monticulo binomial: una lista de arboles binomiales de grados distintos.
 */
public class HeapBinomial<T extends Comparable<T>> {

    private ArbolBinomial<T> cabeza;

    public HeapBinomial() {
        this.cabeza = null;
    }

    public boolean estaVacio() {
        return cabeza == null;
    }

    // ---------------------------------------------------------------
    // insertar
    // ---------------------------------------------------------------
    public NodoBinomial<T> insertar(T x) {
        NodoBinomial<T> nodo = new NodoBinomial<>(x);
        HeapBinomial<T> temporal = new HeapBinomial<>();
        temporal.cabeza = new ArbolBinomial<>(nodo);

        this.cabeza = unir(this, temporal).cabeza;
        return nodo; // util para despues llamar a disminuirClave/eliminar sobre el mismo nodo
    }

    // ---------------------------------------------------------------
    // unir
    // ---------------------------------------------------------------
    public static <T extends Comparable<T>> HeapBinomial<T> unir(HeapBinomial<T> h1, HeapBinomial<T> h2) {
        HeapBinomial<T> resultado = new HeapBinomial<>();
        resultado.cabeza = mezclarListasPorGrado(h1.cabeza, h2.cabeza);

        if (resultado.cabeza == null) {
            return resultado;
        }

        ArbolBinomial<T> prev = null;
        ArbolBinomial<T> actual = resultado.cabeza;
        ArbolBinomial<T> siguiente = actual.siguiente;

        while (siguiente != null) {
            boolean gradosDistintos = actual.raiz.grado != siguiente.raiz.grado;
            boolean vieneUnTercerIgual = siguiente.siguiente != null
                    && siguiente.siguiente.raiz.grado == actual.raiz.grado;

            if (gradosDistintos || vieneUnTercerIgual) {
                prev = actual;
                actual = siguiente;
            } else if (actual.raiz.clave.compareTo(siguiente.raiz.clave) <= 0) {
                actual.siguiente = siguiente.siguiente;
                enlazar(siguiente, actual);
            } else {
                if (prev == null) {
                    resultado.cabeza = siguiente;
                } else {
                    prev.siguiente = siguiente;
                }
                enlazar(actual, siguiente);
                actual = siguiente;
            }
            siguiente = actual.siguiente;
        }

        return resultado;
    }

    /** y pasa a ser hijo de z. Se asume y.raiz.clave >= z.raiz.clave */
    private static <T extends Comparable<T>> void enlazar(ArbolBinomial<T> y, ArbolBinomial<T> z) {
        y.raiz.padre = z.raiz;
        y.raiz.hermano = z.raiz.hijo;
        z.raiz.hijo = y.raiz;
        z.raiz.grado++;
    }

    /** Mezcla (como un merge de listas ordenadas) dos listas de raices por grado creciente. */
    private static <T extends Comparable<T>> ArbolBinomial<T> mezclarListasPorGrado(
            ArbolBinomial<T> l1, ArbolBinomial<T> l2) {

        if (l1 == null) return l2;
        if (l2 == null) return l1;

        ArbolBinomial<T> cabezaResultado;
        if (l1.raiz.grado <= l2.raiz.grado) {
            cabezaResultado = l1;
            l1 = l1.siguiente;
        } else {
            cabezaResultado = l2;
            l2 = l2.siguiente;
        }

        ArbolBinomial<T> actual = cabezaResultado;
        while (l1 != null && l2 != null) {
            if (l1.raiz.grado <= l2.raiz.grado) {
                actual.siguiente = l1;
                l1 = l1.siguiente;
            } else {
                actual.siguiente = l2;
                l2 = l2.siguiente;
            }
            actual = actual.siguiente;
        }
        actual.siguiente = (l1 != null) ? l1 : l2;

        return cabezaResultado;
    }

    // ---------------------------------------------------------------
    // buscarMin
    // ---------------------------------------------------------------
    public T buscarMin() {
        NodoBinomial<T> min = buscarNodoMin();
        return min.clave;
    }

    private NodoBinomial<T> buscarNodoMin() {
        if (cabeza == null) {
            throw new NoSuchElementException("El heap esta vacio");
        }
        NodoBinomial<T> min = cabeza.raiz;
        ArbolBinomial<T> actual = cabeza.siguiente;

        while (actual != null) {
            if (actual.raiz.clave.compareTo(min.clave) < 0) {
                min = actual.raiz;
            }
            actual = actual.siguiente;
        }
        return min;
    }

    // ---------------------------------------------------------------
    // extraerMin
    // ---------------------------------------------------------------
    public T extraerMin() {
        if (cabeza == null) {
            throw new NoSuchElementException("El heap esta vacio");
        }

        ArbolBinomial<T> prevMin = null;
        ArbolBinomial<T> min = cabeza;
        ArbolBinomial<T> prev = cabeza;
        ArbolBinomial<T> actual = cabeza.siguiente;

        while (actual != null) {
            if (actual.raiz.clave.compareTo(min.raiz.clave) < 0) {
                min = actual;
                prevMin = prev;
            }
            prev = actual;
            actual = actual.siguiente;
        }

        // sacar el arbol minimo de la lista de raices
        if (prevMin == null) {
            cabeza = min.siguiente;
        } else {
            prevMin.siguiente = min.siguiente;
        }

        T claveMin = min.raiz.clave;
        this.cabeza = unir(this, separarHijosComoHeap(min.raiz)).cabeza;
        return claveMin;
    }

    /** Convierte los hijos de un nodo en un heap binomial independiente (lista invertida). */
    private HeapBinomial<T> separarHijosComoHeap(NodoBinomial<T> raiz) {
        HeapBinomial<T> hijos = new HeapBinomial<>();
        NodoBinomial<T> hijo = raiz.hijo;
        ArbolBinomial<T> listaHijos = null;

        while (hijo != null) {
            NodoBinomial<T> siguienteHijo = hijo.hermano;
            hijo.hermano = null;
            hijo.padre = null;

            ArbolBinomial<T> nuevoArbol = new ArbolBinomial<>(hijo);
            nuevoArbol.siguiente = listaHijos;
            listaHijos = nuevoArbol;

            hijo = siguienteHijo;
        }
        hijos.cabeza = listaHijos;
        return hijos;
    }

    // ---------------------------------------------------------------
    // disminuirClave
    // ---------------------------------------------------------------
    public void disminuirClave(NodoBinomial<T> x, T k) {
        if (k.compareTo(x.clave) > 0) {
            throw new IllegalArgumentException("La nueva clave es mayor que la actual");
        }
        x.clave = k;
        subirMientrasRompaElOrden(x);
    }

    private void subirMientrasRompaElOrden(NodoBinomial<T> y) {
        NodoBinomial<T> z = y.padre;
        while (z != null && y.clave.compareTo(z.clave) < 0) {
            T claveTemp = y.clave;
            y.clave = z.clave;
            z.clave = claveTemp;
            y = z;
            z = y.padre;
        }
    }

    // ---------------------------------------------------------------
    // eliminar
    // ---------------------------------------------------------------
    // En vez de pedir un valor "menos infinito" (que no siempre existe para
    // un T generico), subimos el nodo hasta que el mismo quede en la raiz
    // de su arbol, intercambiando datos en el camino, y desde ahi lo
    // extraemos igual que en extraerMin pero apuntando a esa raiz puntual.
    public void eliminar(NodoBinomial<T> x) {
        NodoBinomial<T> raizDeSuArbol = subirHastaConvertirseEnRaiz(x);

        ArbolBinomial<T> prev = null;
        ArbolBinomial<T> actual = cabeza;
        while (actual.raiz != raizDeSuArbol) {
            prev = actual;
            actual = actual.siguiente;
        }

        if (prev == null) {
            cabeza = actual.siguiente;
        } else {
            prev.siguiente = actual.siguiente;
        }

        this.cabeza = unir(this, separarHijosComoHeap(raizDeSuArbol)).cabeza;
    }

    private NodoBinomial<T> subirHastaConvertirseEnRaiz(NodoBinomial<T> y) {
        NodoBinomial<T> z = y.padre;
        while (z != null) {
            T claveTemp = y.clave;
            y.clave = z.clave;
            z.clave = claveTemp;
            y = z;
            z = y.padre;
        }
        return y;
    }
}