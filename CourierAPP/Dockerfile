# Utilizăm o imagine Java de bază
FROM openjdk:17-jdk
# Setăm directorul de lucru
WORKDIR /app

# Copiem jar-ul Spring Boot în directorul de lucru al containerului
COPY target/CourierAPP-3.0.7.jar /app/CourierAPP-3.0.7.jar


# Specificăm comanda de rulare a aplicației atunci când containerul este lansat
CMD ["java", "-jar", "CourierAPP-3.0.7.jar"]