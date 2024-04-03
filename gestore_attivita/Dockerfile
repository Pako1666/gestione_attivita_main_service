FROM openjdk:17-alpine
EXPOSE 8080
COPY ./target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
#ENTRYPOINT ["mvnw","spring-boot:run","-Dspring.profiles.active=default"]
