
FROM eclipse-temurin:17-jdk-alpine

# 2. (Opzionale) Crea una cartella temporanea per i file di Tomcat
VOLUME /tmp

# 3. Copia il file .jar costruito da Maven dentro l'immagine
# Usiamo *.jar così funziona indipendentemente dal nome del tuo progetto (rest-prova-0.0.1.jar, ecc.)
COPY target/*.jar app.jar

# 4. Indica su quale porta girerà l'applicazione (solo per documentazione)
# Deve corrispondere a server.port=8083 che hai in application.properties
EXPOSE 8083

# 5. Il comando che esegue l'applicazione quando parte il container
ENTRYPOINT ["java", "-jar", "/app.jar"]
