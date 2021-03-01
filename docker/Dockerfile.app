FROM openjdk:8
COPY target/devops-challenge-1.0-SNAPSHOT-jar-with-dependencies.jar devops-challenge-1.0-SNAPSHOT.jar
COPY inputFile/* /inputFile/
CMD java -cp devops-challenge-1.0-SNAPSHOT.jar:libs/* com.au.Importer /inputFile/