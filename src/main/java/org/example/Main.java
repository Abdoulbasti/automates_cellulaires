package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    static String red = "\033[0;31m";    // RED
    static String green = "\033[0;32m";  // GREEN
    static String reset = "\033[0m";     // Text Reset
    static int[] stats = {0, 1, 2, 3, 4, 5}; //Liste des etats posibles pour les automates cellulaire qui seront instancier
    static String comlete_path;


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Veuillez entrer la lettre correspondant à l'automate : ");
        String userInput = scanner.nextLine();
        String path = "src/main/resources/" + userInput +"/";
        Main automateA = new Main();   Main automateB = new Main();    Main automateC = new Main();
        Main automateD = new Main();    Main automateE = new Main();

        try 
        {
            if(userInput.equals("A"))
            {
                automateA.parametrageAutomates(automateA, path, scanner,  Main::voisinages_jeu_de_vie, Main::jeu_de_vie);
            }
            else if(userInput.equals("B"))
            {
                
            }

            else if(userInput.equals("C"))
            {
              
            }
            else if(userInput.equals("D"))
            {
             
            }

            else if(userInput.equals("E"))
            {
              
            }
            scanner.close();    
        }catch(Exception e)
        {
            System.out.println(e.toString());
        }
    }

    public void parametrageAutomates(Main automate, String path, Scanner scanner,  TetraFunction<int[][], int[], Integer, Integer, Integer> voisinages_jeu_de_vie,
    TriFunction<int[][], int[], TetraFunction<int[][], int[] , Integer, Integer, Integer>, int[][]> matrixUpdateFunction) throws FileNotFoundException
    {
        System.out.println("Veuillez entrer le fichier de configuration initiale : ");
        String filename = scanner.nextLine();
        comlete_path = path + filename;
        int[][] matrice = automate.readMatrixFromFile(comlete_path);
        automate.AffichageEnBoucleMAtrice(matrice, stats, 0, Main::voisinages_jeu_de_vie, Main::jeu_de_vie);        
    }

    //Lire une matrice depuis un fichier filename de configuration, le stocker dans un tab de tab puis le reourner
    public int[][] readMatrixFromFile(String filename) throws FileNotFoundException {
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

    //Afficher une matrice depuis ça reprentation en tab de tab 
    public void afficherMatrice(int[][] matrix, int[] stats) {
        int lignes = matrix.length;
        int colonnes = lignes > 0 ? matrix[0].length : 0;

        System.out.println(lignes);
        System.out.println(colonnes);

        
        for (int[] row : matrix) {
            for (int cell : row) {
                System.out.print(cell == stats[0] ? stats[0] : stats[1]);
            }
            System.out.println();
        }
    }

    /*Methode qui appelle recursivement jusqu'a à chaque fois qu'on tape sur entre, l'utilisateur fait 'q' pour quitter*/
    public void AffichageEnBoucleMAtrice(int[][] matrice, int[] stats, int etape, TetraFunction<int[][], int[], Integer, Integer, Integer> voisinage,
    TriFunction<int[][], int[], TetraFunction<int[][], int[] , Integer, Integer, Integer>, int[][]> matrixUpdateFunction) {
        Scanner scanner = new Scanner(System.in);

        System.out.println(green + "**********************ETAPE" + etape + "********************" + reset);
        afficherMatrice(matrice, stats);
        int[][] nouvelle_matrice = matrixUpdateFunction.apply(matrice, stats, voisinage);

        System.out.println("Taper sur entre pour générer une nouvelle matrice ou sur 'q' pour quitter.");
        System.out.println();
        System.out.println();

        String input = scanner.nextLine();
        if ("q".equalsIgnoreCase(input)) {
            scanner.close();
            System.out.println("Fin du programme.");
            System.exit(0);
        }
        //Si on clique sur entrer seul, on passe à l'etape suivante
        else if ("".equalsIgnoreCase(input)) {
            etape++;
            AffichageEnBoucleMAtrice(nouvelle_matrice, stats, etape, voisinage, matrixUpdateFunction);
        }
    }



















    /**********************************************JEU DE LA VIE(Automate A)*********************************/
    static public int voisinages_jeu_de_vie(int[][] grid, int stats[], int row, int col) {
        int count = 0;
        int[] dr = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] dc = {-1, 0, 1, -1, 1, -1, 0, 1};

        for (int i = 0; i < 8; i++) {
            int newRow = row + dr[i];
            int newCol = col + dc[i];

            if (newRow >= 0 && newRow < grid.length && newCol >= 0 && newCol < grid[0].length && grid[newRow][newCol] == stats[1]) {
                count++;
            }
        }
        return count;
    }

    static int[][] jeu_de_vie(int[][] grid, int[] stats, TetraFunction<int[][], int[], Integer, Integer, Integer> voisinage) {
        int rows = grid.length;
        int cols = grid[0].length;
        int[][] newGrid = new int[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int livingNeighbours = voisinage.apply(grid, stats, i, j);

                // If this cell is dead and has exactly 3 living neighbours, it becomes alive
                if (grid[i][j] == 0 && livingNeighbours == 3) {
                    newGrid[i][j] = stats[1];
                }
                // If this cell is alive and has less than 2 or more than 3 living neighbours, it dies
                else if (grid[i][j] == 1 && (livingNeighbours < 2 || livingNeighbours > 3)) {
                    newGrid[i][j] = stats[0];
                }
                // Otherwise, the cell remains in the same state
                else {
                    newGrid[i][j] = grid[i][j];
                }
            }
        }
        return newGrid;
    }

    /******************************************Automate B*********************************/



    /******************************************Automate C*********************************/



    /******************************************Automate D*********************************/




    /******************************************Automate E*********************************/



    /******************************************Automate F*********************************/

}