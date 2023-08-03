FROM openjdk:17-alpine
EXPOSE 8090
ADD target/url-shortener-0.0.1.jar url-shortener-0.0.1.jar
ENTRYPOINT ["java", "-jar", "url-shortener-0.0.1.jar"]
