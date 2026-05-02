FROM openjdk:17-jdk

WORKDIR /app

COPY . .

RUN chmod +x mvnw
RUN ./mvnw clean package -DskipTests

CMD ["java", "-jar", "target/parking-api-0.0.1-SNAPSHOT.jar"]