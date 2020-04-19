FROM openjdk:14.0.1-slim

EXPOSE 80

RUN mkdir /exam-app

COPY target/exam-app-1.0-SNAPSHOT.jar /exam-app/exam-app.jar

CMD java -jar /exam-app/exam-app.jar
