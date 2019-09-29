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
    
##### pour accéder au code coverage
    
    cd java_test/src/myerp-tests/target/site/jacoco-aggregate/index.html
    open index.html