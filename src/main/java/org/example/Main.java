package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main implements AutomateInterface{
    static String red = "\033[0;31m";    // RED
    static String green = "\033[0;32m";  // GREEN
    static String reset = "\033[0m";     // Text Reset
    //static int[] stats = {0, 1, 2, 3, 4, 5}; //Liste des etats posibles pour les automates cellulaire qui seront instancier
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
                automateB.parametrageAutomates(automateB, path, scanner,  Main::voisinages_jeu_de_vie, Main::jeu_de_vie_HighLife);
            }
            else if(userInput.equals("C"))
            {
                automateC.parametrageAutomates(automateC, path, scanner,  Main::voisinages_Wolfram_110, Main::Wolfram_110);
            }
            else if(userInput.equals("D"))
            {
                automateD.parametrageAutomates(automateD, path, scanner,  Main::voisinages_brian_Brain, Main::brian_Brain);
            }

            else if(userInput.equals("E"))
            {
                automateE.parametrageAutomates(automateE, path, scanner,  Main::voisinages_exemple, Main::exemple);
            }
            scanner.close();
        }catch(Exception e)
        {
            System.out.println(e.toString());
        }
    }

    
    public void parametrageAutomates(Main automate, String path, Scanner scanner,  
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

    /*Methode qui appelle recursivement jusqu'a à chaque fois qu'on tape sur entre, l'utilisateur fait 'q' pour quitter*/
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
    static int[][] jeu_de_vie_HighLife(int[][] grid, int[] stats, TetraFunction<int[][], int[], Integer, Integer, Integer> voisinage) {
        int rows = grid.length;
        int cols = grid[0].length;
        int[][] newGrid = new int[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int livingNeighbours = voisinage.apply(grid, stats, i, j);

                // If this cell is dead and has exactly 3 or 6 living neighbours, it becomes alive
                if (grid[i][j] == 0 && (livingNeighbours == 3 || livingNeighbours == 6)) {
                    newGrid[i][j] = stats[1];
                }
                // If this cell is alive and has 2 or 3 living neighbours, it survives
                else if (grid[i][j] == 1 && (livingNeighbours == 2 || livingNeighbours == 3)) {
                    newGrid[i][j] = grid[i][j];
                }
                // Otherwise, the cell dies (becomes dead)
                else {
                    newGrid[i][j] = stats[0];
                }
            }
        }
        return newGrid;
    }


    /******************************************Automate Wolfram Rule 110 (C)*********************************/
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

    static int[][] Wolfram_110(int[][] grid, int[] stats, TetraFunction<int[][], int[], Integer, Integer, Integer> voisinage) {
        int rows = grid.length;
        int cols = grid[0].length;

        // Define the rule set for Wolfram Rule 110
        int[] rules = {0, 1, 1, 1, 0, 1, 1, 0};

        int[][] newGrid = new int[rows][cols];

        // We apply the rule to each cell in the grid.
        for (int j = 0; j < cols; j++) {
            int neighbours = voisinage.apply(grid, stats, 0, j);
            // Ignore cells on the border of the grid.
            if (neighbours == -1) {
                newGrid[0][j] = grid[0][j];
            } else {
                newGrid[0][j] = rules[neighbours];
            }
        }
        return newGrid;
    }


    /******************************************Automate D*********************************/
    static int voisinages_brian_Brain(int[][] grid, int[] stats, int row, int col) {
        return voisinages_jeu_de_vie(grid, stats, row, col);
    }


    //L'automate cellulaire de Majority Rule(Règle de la majorité), unidimentionnel
    static int[][] brian_Brain(int[][] grid, int[] stats, TetraFunction<int[][], int[], Integer, Integer, Integer> voisinage) {
        int rows = grid.length;
        int cols = grid[0].length;
        int[][] newGrid = new int[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int livingNeighbours = voisinage.apply(grid, stats, i, j);

                // Si une cellule est morte et a exactement deux voisins vivants, elle devient vivante
                if (grid[i][j] == stats[0] && livingNeighbours == 2) {
                    newGrid[i][j] = stats[1];
                }
                // Si une cellule est vivante, elle passe à l'état mourant
                else if (grid[i][j] == stats[1]) {
                    newGrid[i][j] = stats[2];
                }
                // Si une cellule est mourante, elle passe à l'état mort
                else if (grid[i][j] == stats[2]) {
                    newGrid[i][j] = stats[0];
                }
                // Dans tous les autres cas, la cellule reste dans le même état
                else {
                    newGrid[i][j] = grid[i][j];
                }
            }
        }
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

    static int[][] exemple(int[][] grid, int[] stats, TetraFunction<int[][], int[], Integer, Integer, Integer> voisinage) {
        int rows = grid.length;
        int cols = grid[0].length;
        int[][] newGrid = new int[rows][cols];


        for (int j = 0; j < cols; j++) {
            int livingNeighbours = voisinage.apply(grid, stats, 0, j);

            if (grid[0][j] == stats[0] && livingNeighbours == 1) {
                    newGrid[0][j] = stats[1];
            }
            else if (grid[0][j] == stats[1] && livingNeighbours == 1) {
                newGrid[0][j] = stats[2];
            } 
            else if (grid[0][j] == stats[2]) {
                newGrid[0][j] = stats[0];
            }
            else{
                newGrid[0][j] = grid[0][j];
            }
        }
        return newGrid;
    }
}