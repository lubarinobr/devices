FROM maven:3-amazoncorretto-21

ENV APP_HOME /app
RUN mkdir $APP_HOME logs
WORKDIR $APP_HOME

COPY pom.xml $APP_HOME/pom.xml
COPY src $APP_HOME/src

CMD ["mvn", "clean", "package"]