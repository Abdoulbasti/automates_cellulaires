package org.example;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import org.junit.Assert;

public class AppTest {

    
    @Test
    public void testSomething() {
        int expected = 5;
        int actual = 2 + 3;
        Assert.assertEquals(expected, actual);
    }



    //Pour test de readMatrixFromFile 
   @Test
    public void testReadMatrixFromFile() throws FileNotFoundException {
        // Écrire un fichier de test contenant une matrice connue
        String testFileName = "testMatrix.txt";
        PrintWriter writer = new PrintWriter(testFileName);
        writer.println("3 3");  // dimensions de la matrice
        writer.println("123");  // lignes de la matrice
        writer.println("456");
        writer.println("789");
        writer.close();
        
        // Matrice attendue
        int[][] expectedMatrix = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}
        };

        // Créer une instance de Automates
        Automates automates = new Automates();

        // Appeler readMatrixFromFile
        int[][] resultMatrix = automates.readMatrixFromFile(testFileName);

        // Vérifier que la matrice résultante est égale à la matrice attendue
        assertArrayEquals(expectedMatrix, resultMatrix);

        // Supprimer le fichier de test
        File testFile = new File(testFileName);
        testFile.delete();
    }

    //Tests  Automates.voisinages_jeu_de_vie
    @Test 
    public void testVoisinagesJeuDeVie() {
        // Grille de test : une grille 3x3 avec des cellules "vivantes" au milieu
        int[][] grid = {{0, 0, 0}, {0, 1, 0}, {0, 0, 0}};
        // Statut de test : on considère que la valeur 1 correspond à une cellule "vivante"
        int[] stats = {0, 1};

        // Test 1 : la cellule du milieu a 0 voisin "vivant"
        int count = Automates.voisinages_jeu_de_vie(grid, stats, 1, 1);
        Assert.assertEquals(0, count);

        // Test 2 : la cellule en haut à gauche a 1 voisin "vivant"
        count = Automates.voisinages_jeu_de_vie(grid, stats, 0, 0);
        Assert.assertEquals(1, count);
        
        // On change la grille pour avoir une autre configuration
        grid = new int[][] {{1, 1, 1}, {1, 0, 1}, {1, 1, 1}};

        // Test 3 : la cellule du milieu a maintenant 8 voisins "vivants"
        count = Automates.voisinages_jeu_de_vie(grid, stats, 1, 1);
        Assert.assertEquals(8, count);
    }


    //Test pour Automates.jeu_de_vie
    @Test
    public void testJeuDeVie() {
        int[][] grid = {
            {0, 1, 0},
            {0, 1, 0},
            {0, 1, 0}
        };

        int[] stats = {0, 1};

        // La fonction voisinage que nous allons utiliser pour ce test simplement compte le nombre de cellules vivantes autour
        TetraFunction<int[][], int[], Integer, Integer, Integer> voisinage = (gr, st, r, c) -> {
            int count = 0;
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    if (i != 0 || j != 0) {
                        int newRow = r + i;
                        int newCol = c + j;
                        if (newRow >= 0 && newRow < gr.length && newCol >= 0 && newCol < gr[0].length && gr[newRow][newCol] == st[1]) {
                            count++;
                        }
                    }
                }
            }
            return count;
        };

        int[][] newGrid = Automates.jeu_de_vie(grid, stats, voisinage);

        int[][] expectedGrid = {
            {0, 0, 0},
            {1, 1, 1},
            {0, 0, 0}
        };

        assertArrayEquals(expectedGrid, newGrid);
    }


    //Test pour Automates.voisinages_wolfram110
    @Test
    public void testVoisinagesWolfram110() {
        int[][] grid = {
            {0, 1, 1, 0, 1},
            {1, 0, 1, 1, 0},
            {0, 1, 0, 1, 1},
            {1, 0, 1, 0, 0},
            {0, 1, 1, 1, 0}
        };
        int[] stats = {0, 1};  // Juste pour la compatibilité, non utilisé dans la fonction.
        int actual, expected;

        // Test pour une cellule au milieu
        actual = Automates.voisinages_Wolfram_110(grid, stats, 2, 2);
        expected = Integer.parseInt("101", 2); // Représentation binaire du voisinage {1, 0, 1}
        assertEquals(expected, actual);

        // Test pour une cellule sur le bord gauche
        actual = Automates.voisinages_Wolfram_110(grid, stats, 1, 0);
        expected = -1;  // Car la cellule est sur le bord
        assertEquals(expected, actual);

        // Test pour une cellule sur le bord droit
        actual = Automates.voisinages_Wolfram_110(grid, stats, 1, grid[0].length - 1);
        expected = -1;  // Car la cellule est sur le bord
        assertEquals(expected, actual);
    }


}
