package org.example;

import java.io.FileNotFoundException;
import java.util.Scanner;

public interface AutomateInterface {



    void parametrageAutomates(Main automate, String path, Scanner scanner,  
    TetraFunction<int[][], int[], Integer, Integer, Integer> voisinage,
    TriFunction<int[][], int[], TetraFunction<int[][], int[] , Integer, Integer, Integer>, 
    int[][]> fonctionUpdate) throws FileNotFoundException;

    int[][] readMatrixFromFile(String filename) throws FileNotFoundException;

    void afficherMatrice(int[][] matrix);

    public void AffichageEnBoucleMAtrice(int[][] matrice, int[] stats, int etape, TetraFunction<int[][], int[], Integer, Integer, Integer> voisinage,
    TriFunction<int[][], int[], TetraFunction<int[][], int[] , Integer, Integer, Integer>, int[][]> matrixUpdateFunction);

}
