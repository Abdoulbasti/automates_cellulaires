package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


/**
 * La classe principale pour la simulation d'automates cellulaires.
 */
public class Automates implements AutomateInterface{
    static String red = "\033[0;31m";    // RED
    static String green = "\033[0;32m";  // GREEN
    static String reset = "\033[0m";     // Text Reset
    static String comlete_path;


    /**
     * Le point d'entrée du programme.
     *
     * @param args les arguments de ligne de commande
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Veuillez entrer la lettre correspondant à l'automate : ");
        String userInput = scanner.nextLine();
        String path = "src/main/resources/" + userInput +"/";
        Automates automateA = new Automates();   Automates automateB = new Automates();    Automates automateC = new Automates();
        Automates automateD = new Automates();    Automates automateE = new Automates();

        try 
        {
            if(userInput.equals("A"))
            {   automateA.parametrageAutomates(automateA, path, scanner,  Automates::voisinages_jeu_de_vie, Automates::jeu_de_vie);   }
            else if(userInput.equals("B"))
            {   automateB.parametrageAutomates(automateB, path, scanner,  Automates::voisinages_jeu_de_vie, Automates::jeu_de_vie_HighLife);    }
            else if(userInput.equals("C"))
            {   automateC.parametrageAutomates(automateC, path, scanner,  Automates::voisinages_Wolfram_110, Automates::Wolfram_110); }
            else if(userInput.equals("D"))
            {   automateD.parametrageAutomates(automateD, path, scanner,  Automates::voisinages_brian_Brain, Automates::brian_Brain); }
            else if(userInput.equals("E"))
            {   automateE.parametrageAutomates(automateE, path, scanner,  Automates::voisinages_exemple, Automates::exemple); }
            scanner.close();
        }catch(Exception e)
        {
            System.out.println(e.toString());
        }
    }
    
    /**
     * Configure l'automate cellulaire et le lance.
     *
     * @param automate l'automate à configurer
     * @param path le chemin d'accès aux ressources de l'automate
     * @param scanner le scanner pour lire les entrées de l'utilisateur
     * @param voisinage la fonction de voisinage de l'automate
     * @param fonctionUpdate la fonction de mise à jour de l'automate
     */
    public void parametrageAutomates(Automates automate, String path, Scanner scanner,  
    TetraFunction<int[][], int[], Integer, Integer, Integer> voisinage,
    TriFunction<int[][], int[], TetraFunction<int[][], int[] , Integer, Integer, Integer>, 
    int[][]> fonctionUpdate) throws FileNotFoundException
    {
        int[] stats = {0, 1, 2, 3, 4, 5};
        System.out.println("Veuillez entrer le fichier de configuration initiale : ");
        String filename = scanner.nextLine();
        comlete_path = path + filename;
        int[][] matrice = automate.readMatrixFromFile(comlete_path);
        automate.AffichageEnBoucleMAtrice(matrice, stats, 0, voisinage, fonctionUpdate);    
    }

    /**
     * Lit une matrice à partir d'un fichier de configuration.
     *
     * @param filename le nom du fichier à lire
     * @return la matrice lue depuis le fichier
     */
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

    /**
     * Affiche une matrice.
     *
     * @param matrix la matrice à afficher
     */
    public void afficherMatrice(int[][] matrix) {
        int lignes = matrix.length;
        int colonnes = lignes > 0 ? matrix[0].length : 0;

        System.out.println(lignes);
        System.out.println(colonnes);

        for (int[] row : matrix) {
            for (int cell : row) {
                System.out.print(cell);
            }
            System.out.println();
        }
    }
    
    /**
     * Affiche une matrice en boucle jusqu'à ce que l'utilisateur quitte.
     *
     * @param matrice la matrice à afficher
     * @param stats les statistiques de l'automate
     * @param etape le numéro d'étape de l'automate
     * @param voisinage la fonction de voisinage de l'automate
     * @param matrixUpdateFunction la fonction de mise à jour de l'automate
     */
    public void AffichageEnBoucleMAtrice(int[][] matrice, int[] stats, int etape, TetraFunction<int[][], int[], Integer, Integer, Integer> voisinage,
    TriFunction<int[][], int[], TetraFunction<int[][], int[] , Integer, Integer, Integer>, int[][]> matrixUpdateFunction) {
        Scanner scanner = new Scanner(System.in);

        System.out.println(green + "**********************ETAPE" + etape + "********************" + reset);
        //afficherMatrice(matrice, stats);
        afficherMatrice(matrice);
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
      /**
     * Compte les voisins vivants d'une cellule donnée dans le jeu de la vie.
     * 
     * @param grid La grille actuelle de cellules.
     * @param stats Les états possibles pour chaque cellule.
     * @param row La rangée de la cellule en question.
     * @param col La colonne de la cellule en question.
     * @return Le nombre de voisins vivants.
     */
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

    /**
     * Met à jour l'état de toutes les cellules de la grille selon les règles du jeu de la vie.
     * 
     * @param grid La grille actuelle de cellules.
     * @param stats Les états possibles pour chaque cellule.
     * @param voisinage La fonction définissant comment compter les voisins vivants pour chaque cellule.
     * @return La nouvelle grille de cellules après l'application des règles.
     */
    static int[][] jeu_de_vie(int[][] grid, int[] stats, TetraFunction<int[][], int[], Integer, Integer, Integer> voisinage) {
        int rows = grid.length;
        int cols = grid[0].length;
        int[][] newGrid = new int[rows][cols];


        ExecutorService executor = Executors.newFixedThreadPool(rows * cols);
        Future<?>[][] futures = new Future[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int iFinal = i;
                int jFinal = j;

                futures[i][j] = executor.submit(() -> {
                    int livingNeighbours = voisinage.apply(grid, stats, iFinal, jFinal);

                    if(grid[iFinal][jFinal] == 0 && livingNeighbours == 3) {
                        newGrid[iFinal][jFinal] = stats[1];
                    }
                    
                    else if (grid[iFinal][jFinal] == 1 && (livingNeighbours < 2 || livingNeighbours > 3)) {
                        newGrid[iFinal][jFinal] = stats[0];
                    }
                    
                    else {
                        newGrid[iFinal][jFinal] = grid[iFinal][jFinal];
                    }
                });
            }
        }

        // Attendez que toutes les cellules soient mises à jour
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                try {
                    futures[i][j].get();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        executor.shutdown();
        
        return newGrid;
    }


    /******************************************Automate B*********************************/
    
    
    /**
     * Met à jour l'état de toutes les cellules de la grille selon les règles du jeu de la vie HighLife.
     * 
     * @param grid La grille actuelle de cellules.
     * @param stats Les états possibles pour chaque cellule.
     * @param voisinage La fonction définissant comment compter les voisins vivants pour chaque cellule.
     * @return La nouvelle grille de cellules après l'application des règles.
     */
    static int[][] jeu_de_vie_HighLife(int[][] grid, int[] stats, TetraFunction<int[][], int[], Integer, Integer, Integer> voisinage) {
        int rows = grid.length;
        int cols = grid[0].length;
        int[][] newGrid = new int[rows][cols];

        ExecutorService executor = Executors.newFixedThreadPool(rows * cols);
        Future<?>[][] futures = new Future[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {

                int iFinal = i;
                int jFinal = j;

                futures[i][j] = executor.submit(() -> {
                    int livingNeighbours = voisinage.apply(grid, stats, iFinal, jFinal);
                    
                    if (grid[iFinal][jFinal] == 0 && (livingNeighbours == 3 || livingNeighbours == 6)) {
                        newGrid[iFinal][jFinal] = stats[1];
                    }
                    
                    else if (grid[iFinal][jFinal] == 1 && (livingNeighbours == 2 || livingNeighbours == 3)) {
                        newGrid[iFinal][jFinal] = grid[iFinal][jFinal];
                    }
                    
                    else {
                        newGrid[iFinal][jFinal] = stats[0];
                    }
                });
            }
        }

        // Attendez que toutes les cellules soient mises à jour
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                try {
                    futures[i][j].get();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        executor.shutdown();
        

        return newGrid;
    }


    /******************************************Automate Wolfram Rule 110 (C)*********************************/
    /**
     * Calcule l'index de la règle à appliquer pour la cellule en cours, en fonction de son état et de celui de ses voisins.
     * 
     * @param grid La grille actuelle de cellules.
     * @param stats Les états possibles pour chaque cellule.
     * @param row La rangée de la cellule en question.
     * @param col La colonne de la cellule en question.
     * @*/
    static int voisinages_Wolfram_110(int[][] grid, int[] stats, int row, int col) {
        // Assurer que l'index de colonne est valide.
        if(col < 0 || col >= grid[0].length) {
            return -1;
        }
        // Extraire la cellule de gauche, la cellule actuelle et la cellule de droite.
        int gauche = (col == 0) ? -1 : grid[row][col - 1];
        int cellule = grid[row][col];
        int droit = (col == grid[0].length - 1) ? -1 : grid[row][col + 1];
        
        // Si l'une des cellules est une cellule de bord, renvoyer -1.
        if (gauche == -1 || droit == -1) {
            return -1;
        }
        
        // Créer une chaîne de caractères de voisinage en binaire.
        String binaire = String.valueOf(gauche) + String.valueOf(cellule) + String.valueOf(droit);
        
        // Convertir le voisinage en binaire en un entier en base 10.
        return Integer.parseInt(binaire, 2);
    }

    /**
     * Met à jour l'état de toutes les cellules de la grille selon les règles de Wolfram Rule 110.
     * 
     * @param grid La grille actuelle de cellules.
     * @param stats Les états possibles pour chaque cellule.
     * @param voisinage La fonction définissant comment compter les voisins vivants pour chaque cellule.
     * @return La nouvelle grille de cellules après l'application des règles.
     */
    static int[][] Wolfram_110(int[][] grid, int[] stats, TetraFunction<int[][], int[], Integer, Integer, Integer> voisinage) {
        int rows = grid.length;
        int cols = grid[0].length;

        
        int[] rules = {0, 1, 1, 1, 0, 1, 1, 0};
        int[][] newGrid = new int[rows][cols];

        ExecutorService executor = Executors.newFixedThreadPool(rows * cols);
        Future<?>[][] futures = new Future[rows][cols];

        
        for (int j = 0; j < cols; j++) {
            int jFinal = j;

            futures[0][j] = executor.submit(() -> {
                int neighbours = voisinage.apply(grid, stats, 0, jFinal);
                // Ignore cells on the border of the grid.
                if (neighbours == -1) {
                    newGrid[0][jFinal] = grid[0][jFinal];
                }else {
                    newGrid[0][jFinal] = rules[neighbours];
                }
            });
        }


        // Attendez que toutes les cellules soient mises à jour
        for (int j = 0; j < cols; j++) {
            try {
                futures[0][j].get();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        executor.shutdown();
        return newGrid;
    }


    /******************************************Automate D*********************************/
    static int voisinages_brian_Brain(int[][] grid, int[] stats, int row, int col) {
        return voisinages_jeu_de_vie(grid, stats, row, col);
    }


    static int[][] brian_Brain(int[][] grid, int[] stats, TetraFunction<int[][], int[], Integer, Integer, Integer> voisinage) {
        int rows = grid.length;
        int cols = grid[0].length;
        int[][] newGrid = new int[rows][cols];


        ExecutorService executor = Executors.newFixedThreadPool(rows * cols);
        Future<?>[][] futures = new Future[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {

                int iFinal = i;
                int jFinal = j;

                futures[i][j] = executor.submit(() -> {
                    int livingNeighbours = voisinage.apply(grid, stats, iFinal, jFinal);

                    // Si une cellule est morte et a exactement deux voisins vivants, elle devient vivante
                    if (grid[iFinal][jFinal] == stats[0] && livingNeighbours == 2) {
                        newGrid[iFinal][jFinal] = stats[1];
                    }
                    // Si une cellule est vivante, elle passe à l'état mourant
                    else if (grid[iFinal][jFinal] == stats[1]) {
                        newGrid[iFinal][jFinal] = stats[2];
                    }
                    // Si une cellule est mourante, elle passe à l'état mort
                    else if (grid[iFinal][jFinal] == stats[2]) {
                        newGrid[iFinal][jFinal] = stats[0];
                    }
                    // Dans tous les autres cas, la cellule reste dans le même état
                    else {
                        newGrid[iFinal][jFinal] = grid[iFinal][jFinal];
                    }

                });
            }
        }


        // Attendez que toutes les cellules soient mises à jour
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                try {
                    futures[i][j].get();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        executor.shutdown();

        return newGrid;
    }


    /******************************************(automate unidimentionnel)Automate E*********************************/
    static public int voisinages_exemple(int[][] grid, int stats[], int row, int col) {
        int cols = grid[0].length;
        int count = 0;

        // Vérifie la cellule à gauche si elle existe
        if (col - 1 >= 0 && grid[row][col - 1] == stats[2]) {
            count++;
        }
        
        // Vérifie la cellule à droite si elle existe
        if (col + 1 < cols && grid[row][col + 1] == stats[2]) {
            count++;
        }
        return count;
    }

    /**
     * L'automate cellulaire unidimensionnel exemple (Automate E).
     * @param grid Le tableau actuel de cellules.
     * @param stats Les états possibles pour chaque cellule.
     * @param voisinage La fonction définissant comment compter les voisins vivants pour chaque cellule.
     * @return Le nouveau tableau de cellules après l'application de la règle.
     */
    static int[][] exemple(int[][] grid, int[] stats, TetraFunction<int[][], int[], Integer, Integer, Integer> voisinage) {
        int rows = grid.length;
        int cols = grid[0].length;
        int[][] newGrid = new int[rows][cols];


        ExecutorService executor = Executors.newFixedThreadPool(rows * cols);
        Future<?>[][] futures = new Future[rows][cols];

        for (int j = 0; j < cols; j++) {
            int jFinal = j;

            futures[0][j] = executor.submit(() -> {
                int livingNeighbours = voisinage.apply(grid, stats, 0, jFinal);

                if (grid[0][jFinal] == stats[0] && livingNeighbours == 1) {
                        newGrid[0][jFinal] = stats[1];
                }
                else if (grid[0][jFinal] == stats[1] && livingNeighbours == 1) {
                    newGrid[0][jFinal] = stats[2];
                } 
                else if (grid[0][jFinal] == stats[2]) {
                    newGrid[0][jFinal] = stats[0];
                }
                else{
                    newGrid[0][jFinal] = grid[0][jFinal];
                }
            });
        }

        // Attendez que toutes les cellules soient mises à jour
        for (int j = 0; j < cols; j++) {
            try {
                futures[0][j].get();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        executor.shutdown();
        return newGrid;
    }
}