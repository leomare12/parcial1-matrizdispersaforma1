package MatrizDispersa;

import java.io.BufferedReader;
import java.io.FileReader;

public class MatrizDipsersaListaLigadaForma1 {

    static int alto;
    static int ancho;

    public static void main(String[] args) {
        dimensionMatriz();
        MatrizEnListaLigadaForma1 matriz = new MatrizEnListaLigadaForma1(alto, ancho);

        int[] nValores = obtenerValores(alto * ancho);

        int nn = alto;
        int mm = ancho;
        int matrix[][] = new int[nn][mm];

        int kk;

        // Recorrer una matriz para llenar los valores desde el archivo
        for (int a = 0; a < nn; a++) {
            for (int b = 0; b < mm; b++) {
                kk = (nn * mm) - 4 * (b);
                int u = (b - a) + kk;
                // System.out.print("[" + a + "," + b + "] = " + u + "\t");
                matrix[a][b] = nValores[u - 1];
            }
            // System.out.println("");
        }

        // Recorrer para crear la matriz representada en lista ligada forma 1
        for (int i = 0; i < nn; i++) {
            for (int j = 0; j < mm; j++) {
                if (matrix[i][j] == 1) {
                    matriz.setCelda(i, j, matrix[i][j]);
                    System.out.print("[" + i + "," + j + "] = " + matrix[i][j] + "\t");
                }
                // System.out.println(matrix[i][j]);
            }
            System.out.println("");
        }

        matriz.toString();
    }

    public static int[] obtenerValores(int tamanio) {
        int[] valores = new int[tamanio];
        try {
            BufferedReader bf = new BufferedReader(new FileReader("pantalla.txt"));
            String bfRead;
            int fila = 0;
            while ((bfRead = bf.readLine()) != null) {
                if (fila > 1) {
                    String rango = bfRead.split("=")[0];
                    int desde = Integer.parseInt(rango.split("-")[0]);
                    int hasta = Integer.parseInt(rango.split("-")[1]);
                    for (int d = desde; d <= hasta; d++) {
                        valores[d - 1] = Integer.parseInt(bfRead.split("=")[1]);
                    }

                }

                fila++;
            }

        } catch (Exception e) {
            System.err.println("No se encontro archivo");
        }

        return valores;

    }

    public static void dimensionMatriz() {
        try {
            BufferedReader bf = new BufferedReader(new FileReader("pantalla.txt"));
            String bfRead;
            int fila = 0;

            while ((bfRead = bf.readLine()) != null && fila < 2) {
                if (fila == 0) {
                    ancho = Integer.parseInt(bfRead);
                } else {
                    alto = Integer.parseInt(bfRead);
                }
                fila++;
            }

        } catch (Exception e) {
            System.err.println("No se encontro archivo");
        }
    }
}
