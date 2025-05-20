# Dockerfile for Spring Boot backend
FROM openjdk:17-jdk-slim

WORKDIR /app

# Copy the JAR file
COPY target/vehicle-tracking-0.0.1-SNAPSHOT.jar app.jar

# Set Spring Profile
ENV SPRING_PROFILES_ACTIVE=prod

# Add Prometheus JMX exporter agent for additional metrics
RUN mkdir -p /opt/jmx_exporter
ADD https://repo1.maven.org/maven2/io/prometheus/jmx/jmx_prometheus_javaagent/0.18.0/jmx_prometheus_javaagent-0.18.0.jar /opt/jmx_exporter/jmx_prometheus_javaagent.jar
COPY jmx-config.yaml /opt/jmx_exporter/config.yaml

# Run the application with the JMX exporter
ENTRYPOINT ["java", \
            "-javaagent:/opt/jmx_exporter/jmx_prometheus_javaagent.jar=8090:/opt/jmx_exporter/config.yaml", \
            "-jar", "/app/app.jar"]

# Expose Spring Boot and JMX exporter ports
EXPOSE 8080 8090