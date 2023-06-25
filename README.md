Nom : MUKAILA ALH SHITTU
Prenom : Abdoulbasti 
N°Etudiant : 22024114




COMPILATION ET EXÉCUTION DU PROJET : 
    taper : 
        1. make compile
        2. make run
        3. Faire une suites de configuration : 
            - taper le la lettre correspondant à l'automates
            - taper le nom du fichier qui contient la configuration initiale à simuler
            - taper entrer successivement pour voir le resulat de la simulation pour chaque etape.
            - taper 'q' pour quitter le programme



FONCTIONALITÉS IMPLEMENTÉS : 
    NB :
        A, B, D : Automtes bidimentionnelles
        C, E    : Automates uinidimentionnelles


    1. LES AUTOMATES : 
        1.1. Jeu de la Vie de Conway(A)

        1.2. Le Jeu de la Vie version HighLife(B) :
            automate cellulaire bidimensionnel dont les cellules peuvent prendre deux états (« vivantes » ou « mortes »). 
            Une cellule morte y naît à l'étape suivante si elle est entourée de 3 ou 6 voisines vivantes, une cellule 
            vivante survit à l'étape suivante si elle est entourée de deux ou trois cellules vivantes.
        
        1.3. Wolfram Règle 110 en unidimentionnel(C) : 
            Les regles pour cet automates sont les suivantes
                111 -> 0
                110 -> 1
                101 -> 1
                100 -> 0
                011 -> 1
                010 -> 1
                001 -> 1
                000 -> 0

        1.4. Brian's Brain(D) :
            C'est un automate cellulaire qui a trois états(bidimentionnel) : on(1), off(0) et dying(2). Chaque cellule 
            suit ces règles :
            Une cellule dans l'état 'on' passe à l'état 'dying'.
            Une cellule dans l'état 'dying' passe à l'état 'off'.
            Une cellule dans l'état 'off' qui a exactement deux voisins 'on' passe à l'état 'on'. Sinon, elle reste 'off'.
            
        1.5. Automates d'exemple improvisé, unidimentionnel(E)  : 
            C'est un automate unidimensionnel à trois états qui produit un motif complexe à partir d'une seule cellule initiale à l'état 1. Les règles sont définies de la manière suivante :
                Si une cellule est à l'état 0 et a un voisin à l'état 2, elle passe à l'état 1.
                Si une cellule est à l'état 1 et a un voisin à l'état 2, elle passe à l'état 2.
                Si une cellule est à l'état 2, elle passe à l'état 0.



    CHOIX D'IMPLEMENTATIONS : 
        Les interfaces fonctionnelles sont des interfaces qui ne contiennent qu'une seule méthode non-défaut. Le programme utilise l'interface fonctionnelle TetraFunction (une variante personnalisée de la BiFunction intégrée de Java) pour représenter une fonction qui prend quatre arguments et renvoie un résultat.

        une structure de données simple et efficace pour représenter un automate cellulaire. Chaque élément du tableau représente l'état d'une cellule.

        Le programme implémente un automate cellulaire spécifique appelé par "jeu_de_la_vie". Cette règle spécifique est codée dans le corps de la tâche soumise à ExecutorService.

        Le programme encapsule la logique spécifique de Brian's Brain et la logique générale de l'automate cellulaire dans des méthodes statiques distinctes, ce qui favorise la réutilisabilité du code.



    LA CONCURENCE :
        Chaque cellule de la grille est traitée comme une tâche séparée, qui est soumise à un ExecutorService.
        L'ExecutorService est un cadre de gestion des threads qui permet de contrôler le nombre de threads qui sont en cours d'exécution à la fois. En l'occurrence, l'ExecutorService est initialisé avec un nombre de threads égal au nombre de cellules dans la grille (rows * cols), ce qui signifie qu'il y aura potentiellement un thread par cellule.

        Chaque tâche soumise à l'ExecutorService est une lambda qui calcule l'état suivant de la cellule en appliquant la fonction de voisinage à la cellule actuelle et en mettant à jour l'état de la cellule dans la nouvelle grille en fonction des règles de l'automate cellulaire.

        L'objet Future en Java représente le résultat d'un calcul qui peut ne pas encore être terminé. Il est utilisé ici pour attendre que toutes les cellules soient mises à jour avant de continuer.
        


    COMMENTAIRE EN JAVADOC : Les methodes et classes commentés en javadoc.
    

    TESTS UNITAIRES

    DIAGRAMME DE CLASSE