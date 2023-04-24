FROM eclipse-temurin:17

LABEL   metaainer="hello@elizabethdrew.co" \
        version="1.0" \
        description="Restaurant Review Application"

WORKDIR /app

COPY target/restaurant-review-api-*.jar /app/restaurant-review-api.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/restaurant-review-api.jar"]