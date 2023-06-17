package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    static String red = "\033[0;31m";    // RED
    static String green = "\033[0;32m";  // GREEN
    static String reset = "\033[0m";     // Text Reset


    public static void main(String[] args) {

        try 
        {
            if (args.length > 0) {
                //Vous pouvez maintenant utiliser filePath pour lire le fichier
                String filePath = args[0];
                int[][] matrice = readMatrixFromFile(filePath);
                AffichageEnBoucleMAtrice(matrice,0);
            } else {
                System.out.println("Veuillez fournir un chemin de fichier en argument.");
            }
        }catch(Exception e)
        {
            System.out.println(e.toString());
        }
        
    }



    /*Lire une matrice depuis un fichier filename de configuration, le stocker dans un tab de tab puis le reourner*/
    static int[][] readMatrixFromFile(String filename) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(filename));

        // lire le nombre de lignes et de colonnes
        int rows = scanner.nextInt();
        int cols = scanner.nextInt();
        scanner.nextLine();  // lire le reste de la ligne

        // créer une matrice vide
        int[][] matrix = new int[rows][cols];

        // remplir la matrice
        for (int i = 0; i < rows; i++) {
            String line = scanner.nextLine();
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = Character.getNumericValue(line.charAt(j));
            }
        }

        scanner.close();
        return matrix;
    }

    /*Afficher une matrice depuis ça reprentation en tab de tab */
    static void afficherMatrice(int[][] matrix) {
        int lignes = matrix.length;
        int colonnes = lignes > 0 ? matrix[0].length : 0;

        System.out.println(lignes);
        System.out.println(colonnes);

        
        for (int[] row : matrix) {
            for (int cell : row) {
                System.out.print(cell == 0 ? '0' : '1');
            }
            System.out.println();
        }
    }


    /*Methode appeller recursivement jusqu'a ce que l'utilisateur fait 'q' */
    static void AffichageEnBoucleMAtrice(int[][] matrice, int etape) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println(green +"**********************ETAPE" +etape +"********************"  +reset);
        afficherMatrice(matrice);
        int[][] nouvelle_matrice = generation_suivante(matrice);

        System.out.println("Ecrivez 'next' pour générer une nouvelle matrice ou sur 'q' pour quitter.");
        System.out.println();   System.out.println();


        String input = scanner.nextLine();
        if ("q".equalsIgnoreCase(input)) {
            scanner.close();
            System.out.println("Fin du programme.");
            System.exit(0);
        }
        else if ("next".equalsIgnoreCase(input)) {
            etape++;
            AffichageEnBoucleMAtrice(nouvelle_matrice, etape);
        }
    }    



    /*Compter le nombre de cellules voisin vivant pour une cellule dont les cooredonné ont été passé en parametre */
    static int compter_voisins_vivants(int[][] grid, int row, int col) {
        int count = 0;
        int[] dr = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] dc = {-1, 0, 1, -1, 1, -1, 0, 1};

        for (int i = 0; i < 8; i++) {
            int newRow = row + dr[i];
            int newCol = col + dc[i];

            if (newRow >= 0 && newRow < grid.length && newCol >= 0 && newCol < grid[0].length && grid[newRow][newCol] == 1) {
                count++;
            }
        }

        return count;
    }



    /*Donne la generation suivnate de matrice en respectant les regles de vie des cellules*/
    static int[][] generation_suivante(int[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;
        int[][] newGrid = new int[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int livingNeighbours = compter_voisins_vivants(grid, i, j);

                // If this cell is dead and has exactly 3 living neighbours, it becomes alive
                if (grid[i][j] == 0 && livingNeighbours == 3) {
                    newGrid[i][j] = 1;
                }
                // If this cell is alive and has less than 2 or more than 3 living neighbours, it dies
                else if (grid[i][j] == 1 && (livingNeighbours < 2 || livingNeighbours > 3)) {
                    newGrid[i][j] = 0;
                }
                // Otherwise, the cell remains in the same state
                else {
                    newGrid[i][j] = grid[i][j];
                }
            }
        }
        return newGrid;
    }
}