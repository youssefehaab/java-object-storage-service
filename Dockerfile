FROM openjdk:11-jre-slim
EXPOSE 5555
ADD ./target/java-object-storage-1.0.0-SNAPSHOT.jar java-object-storage-1.0.0-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "java-object-storage-1.0.0-SNAPSHOT.jar"]