FROM maven:3.8.3-openjdk-17
EXPOSE 8080
COPY . .
RUN mvn clean install
COPY ./target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
#ENTRYPOINT ["mvnw","spring-boot:run","-Dspring.profiles.active=default"]
