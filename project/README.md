# Projet ANC3 2021 - Trello - Groupe a03


                                                                                    Denis Capece

## Notes de livraison itération 1
* Pas de bug connu;
* Toutes les fonctionnalités sont implémentées;
* Choix de conception:
    + Dans le _model_: 
        - Utilisation d'interfaces, de classe abstraite et de génériques pour factoriser les méthodes.
        - Utilisation d'une façade (_BoardFacade_).
        - Implémentation des _ObservableList\<Column>_ dans _Board_ et _ObservableList\<Card>_ dans _Column_.
        - Implémentation des _BooleanProperties_ utiles à l'activation des différents boutons de déplacement.
        - Enum pour les directions de la méthode _Move()_ dans _BoardFacade_. 
  + Dans les _views_:
    - Tous les éléments implémentés via JavaFx (pas de .fxml).
    - Factorisation de la méthode de configuration des boutons.

    

## Notes de livraison itération 2
* Pas de bug connu <br/>
  Néanmoins:
    + Lourdeur à l'affichage lorsque trop de cartes sont entrées dans la colonne (barre de défilement verticale pas très jolie).
    + Une commande de titre est enregistrée si l'utilisateur active l'edit de titre et ne fait rien.
* Toutes les fonctionnalités sont implémentées;
* Choix de conception:
    + Dans un souci d'abstraction des commandes plus grand, le design façade a été abandonné.
    + Le memento n'en est pas un... il n'enregistre qu'une référence pour la restitution de l'objet impliqué.
    + Le memento des titres pourrait être remplacé par une simple String stockée dans la commande.
    + Le menu est une entité à part entière et bénéficie de sa propre vue et de son modèle de vue.
    


## Notes de livraison itération 3
* Pas de bug connu;
  Néanmoins: 
    + La barre de défilement apparait toujours lorsque trop de cartes sont entrées
* Toutes les fonctionnalités sont implémentées;
    + Plusieurs tableaux sont désormais présents dans la base de données; à l'ouverture, l'utilisateur peut choisir d'ouvrir un de ces tableaux ou d'en créer un nouveau.
    + Le déplacements des cartes est possible depuis le menu.
    + Une fenêtre propose plusieurs options avant de quitter l'application (redondant mais juste pour l'exercice...)
* Choix de conception:
    + Le memento ne fait plus partie d'un package -> package private pour les éléments du model
    + Le label editable est redevenu un label editable (deux composants label et textfield); le titre est édité en poussant sur enter ou lorsque le textfield perd le focus; aucune action n'est enregistrée s'il n'y a pas de modification du titre.
    + Le label editable a son propre modèle de vue.
    + Le DAO a son propre package. 
          - Il est composé de deux interfaces (une pour les _Board_ et une pour les _Column_ et _Card_)
          - Une factory gère l'appel des différents DAO implémentés; ceux-ci sont package-private
          - Les méthodes du DAO gèrent une seule requête SQL
          - La connection est une sorte de singleton appelée par chaque méthode des DAO; si elle a été ouverte par une requête précédente, c'est cette connection qui sera utilisée. Elle sera fermée lorsque l'ensemble des requêtes aura été effectué.
    + Un Repository est implémenté au sein du  _model_. C'est lui qui fait le lien entre _model_ et DAO :
          - Comme le DAO, il y a deux interfaces et une factory
          - Chaque Repository implémenté peut gérer un ensemble de méthodes DAO pour exécuter la tâche requise; lorsque les actions à effectuer en base de données sont terminées, la connection est fermée. 


          
## Notes de livraison itération 4


