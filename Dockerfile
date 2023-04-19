FROM eclipse-temurin:17

LABEL   metaainer="hello@elizabethdrew.co" \
        version="1.0" \
        description="Restaurant Review Application"

WORKDIR /app

COPY target/restaurant-review-*.jar /app/restaurant-review.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/restaurant-review.jar"]