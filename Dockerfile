FROM openjdk:19
LABEL authors="Vadim Taratonov"
EXPOSE 8083
ARG JAR_FILE=/target/dossier-0.0.1-SNAPSHOT.jar

COPY ${JAR_FILE} dossier

CMD ["java","-jar","dossier"]