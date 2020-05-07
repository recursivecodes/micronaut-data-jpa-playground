FROM openjdk:14-alpine
COPY build/libs/data-jpa-demo-*-all.jar data-jpa-demo.jar
EXPOSE 8080
CMD ["java", "-Dcom.sun.management.jmxremote", "-Xmx128m", "-jar", "data-jpa-demo.jar"]