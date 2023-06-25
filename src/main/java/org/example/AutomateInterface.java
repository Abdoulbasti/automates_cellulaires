package org.example;

import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * L'interface AutomateInterface fournit des méthodes pour la configuration, la lecture, l'affichage 
 * et la mise à jour d'un automate cellulaire.
 *
 */
public interface AutomateInterface {


    /**
     * Configure les paramètres de l'automate spécifié.
     *
     * @param automate l'automate à configurer
     * @param path le chemin d'accès au fichier contenant les paramètres de configuration
     * @param scanner un Scanner pour lire les paramètres de configuration
     * @param voisinage la fonction pour déterminer le nombre de voisins vivants d'une cellule
     * @param fonctionUpdate la fonction pour mettre à jour l'état de la matrice (grille de l'automate)
     * @throws FileNotFoundException si le fichier spécifié par le chemin d'accès n'est pas trouvé
     */
    void parametrageAutomates(Automates automate, String path, Scanner scanner,  
    TetraFunction<int[][], int[], Integer, Integer, Integer> voisinage,
    TriFunction<int[][], int[], TetraFunction<int[][], int[] , Integer, Integer, Integer>, 
    int[][]> fonctionUpdate) throws FileNotFoundException;


    /**
     * Lit une matrice à partir d'un fichier spécifié.
     *
     * @param filename le nom du fichier à partir duquel lire la matrice
     * @return la matrice lue à partir du fichier
     * @throws FileNotFoundException si le fichier spécifié par le nom de fichier n'est pas trouvé
     */
    int[][] readMatrixFromFile(String filename) throws FileNotFoundException;



    /**
     * Affiche la matrice spécifiée.
     *
     * @param matrix la matrice à afficher
     */
    void afficherMatrice(int[][] matrix);




    /**
     * Met à jour et affiche une matrice spécifiée de manière répétée.
     *
     * @param matrice la matrice à mettre à jour et afficher
     * @param stats les statistiques (états) pour les cellules de la matrice
     * @param etape le nombre d'itérations à effectuer
     * @param voisinage la fonction pour déterminer le nombre de voisins vivants d'une cellule
     * @param matrixUpdateFunction la fonction pour mettre à jour l'état de la matrice
     */
    public void AffichageEnBoucleMAtrice(int[][] matrice, int[] stats, int etape, TetraFunction<int[][], int[], Integer, Integer, Integer> voisinage,
    TriFunction<int[][], int[], TetraFunction<int[][], int[] , Integer, Integer, Integer>, int[][]> matrixUpdateFunction);

}
