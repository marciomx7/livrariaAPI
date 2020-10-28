# Alpine Linux with OpenJDK JRE
FROM openjdk:8-jre-alpine

# Copy war file
COPY livraria.jar /livraria.war

# run the app
CMD ["/usr/bin/java", "-jar", "/livraria.war"]
