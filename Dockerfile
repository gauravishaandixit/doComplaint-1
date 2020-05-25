FROM openjdk:8
EXPOSE 8080
ADD target/doComplaint.jar doComplaint.jar
ENTRYPOINT ["java", "-jar", "/doComplaint.jar"]