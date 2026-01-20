# 1. Parti da una base con Java già installato (es. Java 17)
FROM openjdk:21

# 2. Crea una cartella di lavoro dentro il container
WORKDIR /app

# 3. Copia il file .jar creato da Maven (nello step precedente) dentro il container
# Nota: target/*.jar è dove Maven mette il file compilato
COPY target/rest-prova-0.0.1-SNAPSHOT.jar app.jar

# 4. Esponi la porta (quella che usi in application.properties)
EXPOSE 8083

# 5. Il comando finale: "Quando il container parte, esegui questo comando"
ENTRYPOINT ["java", "-jar", "app.jar"]
