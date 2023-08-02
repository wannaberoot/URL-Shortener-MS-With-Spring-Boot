FROM openjdk:17-alpine
ARG APP_NAME="url-shortener"
ARG APP_VERSION="0.0.1"
ENV APP_PATH="/${APP_NAME}-${APP_VERSION}.jar"
EXPOSE 8090
ADD target/$APP_PATH $APP_PATH
ENTRYPOINT ["java", "-jar", "$APP_PATH"]
