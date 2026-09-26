package Heap_Binomial;
public class MainHeap {

    public static void main(String[] args) {

        HeapBinomial<Integer> heap = new HeapBinomial<>();

        // Insertar elementos
        heap.insertar(10);
        heap.insertar(5);
        heap.insertar(20);
        heap.insertar(3);
        heap.insertar(8);

        // Buscar mínimo
        System.out.println("Mínimo: " + heap.buscarMin());

        // Extraer elementos
        System.out.println("Extraído: " + heap.extraerMin());
        System.out.println("Mínimo: " + heap.buscarMin());

        System.out.println("Extraído: " + heap.extraerMin());
        System.out.println("Mínimo: " + heap.buscarMin());

        System.out.println("Extraído: " + heap.extraerMin());
        System.out.println("Extraído: " + heap.extraerMin());
        System.out.println("Extraído: " + heap.extraerMin());
    }
}
