FROM openjdk:8
EXPOSE 8080
EXPOSE 8080
ARG JAR_FILE=target/evaluation-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

#COPY ./target/evaluation-0.0.1-SNAPSHOT.jar evaluation-0.0.1-SNAPSHOT.jar
#CMD ["java","-jar","evaluation-0.0.1-SNAPSHOT.jar"]