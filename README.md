Les automates : 
    Jeu de la Vie de Conway(A) : 


    Le Jeu de la Vie de HighLife(B) : 
    jeu de la vie Immigration

    Automate de Wolfram Règle 110, unidimentionnel(C):
        111 -> 0
        110 -> 1
        101 -> 1
        100 -> 0
        011 -> 1
        010 -> 1
        001 -> 1
        000 -> 0
        La notation "101 -> 1" est une règle de transition pour l'automate cellulaire de Wolfram Rule 110. Cela signifie que si une cellule et ses deux voisins sont dans une configuration où la cellule de gauche est dans l'état "1", la cellule centrale est dans l'état "0", et la cellule de droite est dans l'état "1" (d'où "101"), alors la cellule centrale sera dans l'état "1" à la prochaine étape de temps.


    L'automate cellulaire de Majority Rule(Règle de la majorité), unidimentionnel :
        est un type d'automate cellulaire unidimensionnel. Dans cet automate, l'état de chaque cellule à l'étape suivante est déterminé par l'état majoritaire de la cellule et de ses voisins immédiats à l'étape actuelle.
        Pour une cellule d'état binaire (0 ou 1), la règle s'applique de la manière suivante :
        Si une cellule a un plus grand nombre de voisins immédiats (incluant elle-même) dans l'état 1, alors elle passe ou reste à l'état 1 lors de la prochaine génération.
        Si une cellule a un plus grand nombre de voisins immédiats (incluant elle-même) dans l'état 0, alors elle passe ou reste à l'état 0 lors de la prochaine génération.
        Si une cellule a un nombre égal de voisins immédiats dans l'état 0 et dans l'état 1, alors son état reste inchangé lors de la prochaine génération.



    Brian's Brain(Automates D) : C'est un automate cellulaire qui a trois états(bidimentionnel) : on, off et dying. Chaque cellule suit ces règles :
            Une cellule dans l'état 'on' passe à l'état 'dying'.
            Une cellule dans l'état 'dying' passe à l'état 'off'.
            Une cellule dans l'état 'off' qui a exactement deux voisins 'on' passe à l'état 'on'. Sinon, elle reste 'off'.


    Automates de graine de cactus(Exemple) (E)  : C'est un automate unidimensionnel à trois états qui produit un motif complexe à partir d'une seule cellule initiale à l'état 1. Les règles sont définies de la manière suivante :

        Si une cellule est à l'état 0 et a un voisin à l'état 2, elle passe à l'état 1.
        Si une cellule est à l'état 1 et a un voisin à l'état 2, elle passe à l'état 2.
        Si une cellule est à l'état 2, elle passe à l'état 0.