FROM openjdk:8u162

EXPOSE 80

RUN mkdir /exam-app

COPY target/exam-app-0.0.1-SNAPSHOT.jar /exam-app/exam-app.jar

CMD java -jar /exam-app/exam-app.jar
