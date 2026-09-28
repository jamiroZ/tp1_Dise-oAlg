package reinas;

public class Backtracking {

    private static final int N = 4;
    private int[][] tablero = new int[N][N];

    public void resolver() {

        System.out.println("===== PROBLEMA DE LAS 4 REINAS =====\n");

        if (resolverFila(0)) {
            System.out.println("\nSolución encontrada:");
            mostrarTablero();
        } else {
            System.out.println("No existe solución.");
        }
    }

    private boolean resolverFila(int fila) {

        // Si llegamos a la fila 4,
        // todas las reinas fueron colocadas.
        if (fila == N) {
            return true;
        }

        // Probamos todas las columnas de esta fila
        for (int columna = 0; columna < N; columna++) {

            System.out.println("Probando fila " + (fila + 1) +", columna " + (columna + 1));

            if (esValida(fila, columna)) {

                // Colocamos la reina
                tablero[fila][columna] = 1;

                System.out.println("  Posición válida. Colocamos la reina.");
                mostrarTablero();

                // Intentamos resolver la siguiente fila
                if (resolverFila(fila + 1)) {
                    return true;
                }

                // No funcionó → BACKTRACKING
                System.out.println( "  No se pudo continuar. " + "Retrocedemos y quitamos la reina.");

                tablero[fila][columna] = 0;
                mostrarTablero();

            } else {

                System.out.println("  Posición inválida. Se descarta.");
            }
        }

        // Ninguna columna funcionó
        return false;
    }

    private boolean esValida(int fila, int columna) {
        // Revisar columna
        for (int i = 0; i < fila; i++) {
            if (tablero[i][columna] == 1) {
                return false;
            }
        }

        // Revisar diagonal izquierda
        for (int i = fila - 1, j = columna - 1; i >= 0 && j >= 0; i--, j--) {

            if (tablero[i][j] == 1) {
                return false;
            }
        }


        // Revisar diagonal derecha
        for (int i = fila - 1, j = columna + 1;  i >= 0 && j < N; i--, j++) {

            if (tablero[i][j] == 1) {
                return false;
            }
        }

        return true;
    }

    private void mostrarTablero() {

        for (int i = 0; i < N; i++) {

            for (int j = 0; j < N; j++) {

                if (tablero[i][j] == 1) {
                    System.out.print("Q ");
                } else {
                    System.out.print(". ");
                }
            }

            System.out.println();
        }

        System.out.println();
    }
}