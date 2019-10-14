# MyERP

## Organisation du répertoire

*   `doc` : documentation
*   `docker` : répertoire relatifs aux conteneurs _docker_ utiles pour le projet
    *   `dev` : environnement de développement
*   `src` : code source de l'application


## Environnement de développement

Les composants nécessaires lors du développement sont disponibles via des conteneurs _docker_.
L'environnement de développement est assemblé grâce à _docker-compose_
(cf docker/dev/docker-compose.yml).

Il comporte :

*   une base de données _PostgreSQL_ contenant un jeu de données de démo (`postgresql://127.0.0.1:9032/db_myerp`)



### Lancement

    cd docker/dev
    docker-compose up


### Arrêt

    cd docker/dev
    docker-compose stop


### Remise à zero

    cd docker/dev
    docker-compose stop
    docker-compose rm -v
    docker-compose up


### Codecoverage

Utilisation de Jacoco https://www.eclemma.org/jacoco/ <br/>
En lançant le goal mvn install dans le module parent, cela merge les rapports des tests dans 
le module myerp-tests, le rapport HTML est généré ici: <br/> 
java_test/src/myerp-tests/target/site/jacoco-aggregate/index.html


### Soutenance

    cd java_test/docker/dev
    docker-compose down && docker-compose up --d
    
##### pour lancer les tests unitaires

    cd java_test/src/
    mvn clean install
    
##### pour lancer les tests d'intégration (avec le profil test-business)

    cd java_test/src/
    mvn clean install -p test-business
    
Les tests utilisent une base de données spécifique de test (voir le fichier docker-compose)
    
##### TODO

    ComptabiliteManagerImpl.java:61 à tester
    ComptabiliteManagerImpl.java:64 à implémenter
    ComptabiliteManagerImpl.java:104 à tester
    ComptabiliteManagerImpl.java:119 à tester
    ComptabiliteManagerImpl.java:157 RG_Compta_5
    
    EcritureComptable.java:83 à tester
    
##### Erreurs à corrigées

    - dans updateEcritureComptable (ComptabiliteManagerImpl ligne 227) il faut vérifier avec 
    checkEcritureComptable au préalable
    - dans le fichier sqlContext il manquait une virgule dans la requête 
    (https://github.com/walterwhites/java_test/commit/3bae7e5ff7dc182adc6890ffb49923dd9f5594d0#diff-1ded7f8287b42f81864a273c25c8a007)
    - dans la méthode getTotalCredit, il faut utiliser getTotalCredit au lieu de getTotalDébit
    - dans la méthode isEquilibree de EcritureComptable il faut utiliser compareTo au lieu de Equals (pour comparer des scales différents)
    - mauvaise régex pour la variable référence
    (https://github.com/walterwhites/java_test/commit/b4252a9c45d77027155b633c186cbfb377c7768c#diff-fd471009e0ef5ecba3fc9df72dd0561c) 

##### pour accéder au code coverage
    
    cd java_test/src/myerp-tests/target/site/jacoco-aggregate
    open index.html