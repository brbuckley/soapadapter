FROM openjdk:11

COPY target/soapadapter.jar /target/soapadapter.jar

EXPOSE 8080

CMD ["java","-jar","-Dspring.profiles.active=deploy","/target/soapadapter.jar"]
