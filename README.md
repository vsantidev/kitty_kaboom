# KittyKaboom
KittyKaboom est un projet développé avec libGDX, un framework de développement de jeux multiplateformes. Ce projet est généré avec gdx-liftoff et comprend une structure de base avec des écrans et des fonctionnalités permettant de démarrer un jeu de manière simple.

Structure du projet
Le projet KittyKaboom est divisé en plusieurs modules :

core : Ce module contient la logique du jeu partagée entre toutes les plateformes.
lwjgl3 : La plateforme principale pour le bureau utilisant LWJGL3. Ce module permet de lancer l'application sur un environnement de bureau.
Plateformes supportées
Desktop (lwjgl3) : La plateforme de bureau utilisant LWJGL3.
Le projet peut être étendu pour supporter d'autres plateformes comme Android et iOS en utilisant les modules appropriés dans libGDX.
Dépendances et gestion du projet avec Gradle
Ce projet utilise Gradle pour gérer les dépendances et la construction des projets. Le wrapper Gradle est inclus, vous pouvez donc utiliser les commandes suivantes :

./gradlew (Linux/macOS) ou gradlew.bat (Windows) pour exécuter les tâches Gradle.
Tâches utiles de Gradle :
--continue : Continue l'exécution même en cas d'erreurs.
--daemon : Utilise le démon Gradle pour exécuter les tâches choisies, accélérant ainsi le processus.
--offline : Utilise les archives de dépendances en cache pour éviter de télécharger de nouveau les dépendances.
--refresh-dependencies : Force la validation de toutes les dépendances, utile pour les versions snapshot.
Commandes principales :
build : Construit les sources et archive tous les projets.
clean : Supprime les dossiers de construction, où les classes compilées et les archives construites sont stockées.
eclipse : Génère les données de projet pour Eclipse.
idea : Génère les données de projet pour IntelliJ IDEA.
lwjgl3:jar : Construit l'archive exécutable du jeu. Le fichier JAR peut être trouvé dans lwjgl3/build/libs.
lwjgl3:run : Lance l'application sur la plateforme de bureau.
test : Exécute les tests unitaires (si disponibles).
Démarrage rapide
Clonez ce projet ou téléchargez-le.
Ouvrez un terminal ou un invite de commandes à la racine du projet.
Exécutez la commande suivante pour construire et lancer l'application :
Pour build : ./gradlew build (ou gradlew.bat build sur Windows).
Pour lancer l'application : ./gradlew lwjgl3:run (ou gradlew.bat lwjgl3:run sur Windows).
Développement et contributions
Si vous souhaitez contribuer au projet KittyKaboom, voici quelques étapes que vous pouvez suivre :

Forkez le dépôt.
Créez une branche pour votre fonctionnalité ou correction de bug.
Appliquez vos modifications.
Soumettez une pull request avec une description détaillée de vos modifications.
Contact
Pour toute question ou suggestion, n'hésitez pas à contacter l'équipe de développement via les forums ou le suivi des problèmes sur le dépôt GitHub.
