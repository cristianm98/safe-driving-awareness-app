FROM eclipse-temurin:21
WORKDIR /app
COPY target/classes/romania-latest.osm.pbf /app/resources/romania-latest.osm.pbf
COPY target/safe-driving-awareness-0.0.1-SNAPSHOT.jar /app/safe-driving-awareness.jar
ENTRYPOINT [ "java", "-jar", "/app/safe-driving-awareness.jar" ]