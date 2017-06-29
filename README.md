# OkeanosRegistrationApp

J'ai pas mal changé les dépendances pour pouvoir tester

-> Ajout de junit pour les unit tests
-> Ajout de jaxws / jersey pour la partie web
-> MàJ de Spark de 2.0.0 à 2.5.5

Un exemple pourri pour partir, mais il a le mérite d'exister.


# déploiement

1. mvn clean install => lance le build et le pk
2. prendre le jar dans : target\okeanosSparkApp-0.0.1-SNAPSHOT-jar-with-dependencies.jar
3. copier au meme niveau le dossier \security
4. copier au meme niveau le dossier \src\main\ressources\public
5. copier au meme niveau le dossier \h2
6. executer le jar : java -jar okeanosSparkApp-0.0.1-SNAPSHOT-jar-with-dependencies.jar

le serveur se lance sur https://localhost:8080
