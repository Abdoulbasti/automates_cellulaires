package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            // Création de la matrice
            int[][] matrix = new int[10][10];

            // Remplir la matrice avec des valeurs aléatoires
            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix[i].length; j++) {
                    matrix[i][j] = (int) (Math.random() * 2); // génère 0 ou 1 aléatoirement
                }
            }

            // Affichage de la matrice
            for (int[] row : matrix) {
                for (int cell : row) {
                    if (cell == 0) {
                        System.out.print('0');
                    } else {
                        System.out.print('1');
                    }
                }
                System.out.println();
            }

            System.out.println("\n\nAppuyez sur ENTREE pour générer une nouvelle matrice ou sur 'q' pour quitter.");

            String input = scanner.nextLine();
            if ("q".equalsIgnoreCase(input)) {
                break;
            }
        }

        scanner.close();
        System.out.println("Fin du programme.");
    }
}