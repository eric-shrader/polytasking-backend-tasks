FROM amazoncorretto:17
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8080
ENV SPRING_DATASOURCE_URL="jdbc:mysql://polytasking.c95grb4xebfn.us-east-2.rds.amazonaws.com/polytasking"
ENV SPRING_DATASOURCE_USERNAME="admin"
ENV SPRING_DATASOURCE_PASSWORD="secret"
ENV SPRING_DATASOURCE_DRIVER-CLASS-NAME="com.mysql.cj.jdbc.Driver"
ENTRYPOINT ["java","-jar","/app.jar"]