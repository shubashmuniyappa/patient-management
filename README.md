Java Spring Boot Microservices Project
======================================

This project is a hands-on implementation of a Java Spring Boot Microservices architecture inspired by a YouTube course. It includes key components such as patient management, billing, notification, authentication, and communication between services using gRPC and Kafka.

Microservices
-------------

1. Patient Service
- Communicates with Billing Service via gRPC.
- Sends messages to Kafka for further processing (e.g., notifications).
- Connected to PostgreSQL.
- Environment variables used:
  SPRING_DATASOURCE_URL=jdbc:postgresql://patient-service-db:5432/db
  SPRING_DATASOURCE_USERNAME=admin_user
  SPRING_DATASOURCE_PASSWORD=password
  SPRING_JPA_HIBERNATE_DDL_AUTO=update
  SPRING_KAFKA_BOOTSTRAP_SERVERS=kafka:9092
  BILLING_SERVICE_ADDRESS=billing-service
  BILLING_SERVICE_GRPC_PORT=9005

2. Billing Service
- Exposes a gRPC endpoint to be consumed by Patient Service.
- Uses Protocol Buffers to define services and message contracts.

3. Notification Service
- Consumes Kafka messages.
- Uses Protobuf for message format.

4. Auth Service
- Handles authentication using Spring Security.

Technologies Used
-----------------
- Java 17
- Spring Boot
- Spring Security
- Spring Kafka
- gRPC & Protocol Buffers
- PostgreSQL
- Docker & Docker Compose
- Maven

Setup Instructions
------------------

Prerequisites
- Java 17+
- Docker & Docker Compose
- Maven

1. Clone the Repository
   git clone https://github.com/yourusername/your-repo-name.git
   cd your-repo-name

2. Build the Project
   Navigate to each service folder and build the project:
   mvn clean install

3. Run with Docker Compose
   docker-compose up --build

4. Kafka Environment Variables (if running locally)
   KAFKA_CFG_ADVERTISED_LISTENERS=PLAINTEXT://kafka:9092,EXTERNAL://localhost:9094
   KAFKA_CFG_CONTROLLER_LISTENER_NAMES=CONTROLLER
   KAFKA_CFG_CONTROLLER_QUORUM_VOTERS=0@kafka:9093
   KAFKA_CFG_LISTENER_SECURITY_PROTOCOL_MAP=CONTROLLER:PLAINTEXT,EXTERNAL:PLAINTEXT,PLAINTEXT:PLAINTEXT
   KAFKA_CFG_LISTENERS=PLAINTEXT://:9092,CONTROLLER://:9093,EXTERNAL://:9094
   KAFKA_CFG_NODE_ID=0
   KAFKA_CFG_PROCESS_ROLES=controller,broker

Build Tools
-----------

gRPC / Proto Plugin Setup
Included in the pom.xml:
- protobuf-maven-plugin

Dependencies
Each service includes dependencies for:
- grpc-netty-shaded
- grpc-protobuf
- grpc-stub
- protobuf-java
- spring-kafka
- grpc-spring-boot-starter

Community
---------
This project is based on a course from YouTube and supported by a Discord Community. Feel free to join for help and discussions.

License
-------
This project is for educational purposes only.
