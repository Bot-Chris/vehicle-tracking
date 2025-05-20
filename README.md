### Currently Implemented Features

1. **Vehicle Management**
   - Create, read, update, and delete vehicle records
   - Update vehicle location in real-time
   - Vehicle metadata storage (registration, model, capacity, etc.)

2. **User Authentication & Authorization**
   - JWT-based authentication
   - Role-based access control (USER and ADMIN roles)
   - Secure password storage with BCrypt
   
3. **API Security**
   - Stateless API design
   - Request validation
   - Exception handling

4. **Containerization**
   - Docker configuration
   - Kubernetes deployment manifests# Real-Time Logistics and Analytics Platform

A comprehensive platform for tracking delivery vehicles with real-time analytics, built with modern technologies and a microservices architecture.

## Overview

This platform provides a complete solution for logistics companies to track their delivery vehicles, monitor telemetry data, and analyze performance metrics in real-time. The current implementation includes:

- **REST API Backend**: Spring Boot application with CRUD operations for vehicle management
- **JWT Authentication**: Secure API access with role-based permissions
- **PostgreSQL Database**: Persistent storage for vehicle and user data
- **Docker & Kubernetes Configuration**: Ready for containerized deployment

The platform is designed with a modular architecture to allow for future expansion with additional services and capabilities.

## Tech Stack

### Currently Implemented
- **Java Spring Boot**: Core API services with JWT authentication
- **PostgreSQL**: Relational database for vehicle and user data
- **Docker**: Containerization for consistent deployment
- **Kubernetes**: Container orchestration configuration

### Planned Implementation
- **Python**: ETL processes and Kafka stream processing
- **Apache Kafka**: Event streaming platform for real-time data pipeline
- **MongoDB**: Semi-structured data storage for analytics
- **Cassandra**: Time-series database for historical telemetry
- **Prometheus & Grafana**: Monitoring and visualization

## Getting Started

### Prerequisites
- JDK 17
- Docker and Docker Compose
- PostgreSQL
- Maven
- Kubernetes cluster (for production deployment)

### Local Development

1. **Clone the repository**
   ```bash
   git clone https://github.com/yourusername/vehicle-tracking.git
   cd vehicle-tracking
   ```

2. **Set up PostgreSQL database**
   ```bash
   # Create logistics database
   createdb -U postgres logistics
   ```

3. **Build and run the Spring Boot application**
   ```bash
   ./mvnw clean package -DskipTests
   ./mvnw spring-boot:run
   ```

4. **Register a user and test the API**
   ```bash
   # Register an admin user
   curl -X POST -H "Content-Type: application/json" -d '{"firstName":"Admin","lastName":"User","username":"admin","email":"admin@example.com","password":"password123","roles":["admin"]}' http://localhost:8080/api/auth/register

   # Login to get JWT token
   curl -X POST -H "Content-Type: application/json" -d '{"username":"admin","password":"password123"}' http://localhost:8080/api/auth/login

   # Create a vehicle (replace YOUR_JWT_TOKEN with the token from login response)
   curl -X POST -H "Content-Type: application/json" -H "Authorization: Bearer YOUR_JWT_TOKEN" -d '{"registrationNumber":"ABC123","model":"Tesla Model 3","type":"Sedan","capacityKg":500,"status":"ACTIVE"}' http://localhost:8080/api/vehicles
   ```

### Docker Deployment

1. **Build the Docker image**
   ```bash
   docker build -t logistics-api:1.0.0 .
   ```

2. **Run with Docker Compose**
   Create a `docker-compose.yml` file and run:
   ```bash
   docker-compose up
   ```

### Kubernetes Deployment

1. **Create Kubernetes namespace**
   ```bash
   kubectl create namespace logistics
   ```

2. **Apply database manifests**
   ```bash
   kubectl apply -f k8s/databases/postgres/ -n logistics
   ```

3. **Apply backend API manifests**
   ```bash
   kubectl apply -f k8s/backend/ -n logistics
   ```

4. **Access the API**
   ```bash
   # Get service URL
   kubectl get svc -n logistics
   ```

## API Endpoints

### Authentication
- `POST /api/auth/register` - Register a new user
- `POST /api/auth/login` - Login and get JWT token

### Vehicles
- `GET /api/vehicles` - Get all vehicles
- `GET /api/vehicles/{id}` - Get vehicle by ID
- `POST /api/vehicles` - Create a new vehicle
- `PUT /api/vehicles/{id}` - Update a vehicle
- `DELETE /api/vehicles/{id}` - Delete a vehicle
- `PATCH /api/vehicles/{id}/location` - Update vehicle location

## Security

- JWT-based authentication
- Role-based authorization (USER and ADMIN roles)
- BCrypt password encryption
- Stateless API design

## Monitoring

The platform includes a comprehensive monitoring stack:
- **Prometheus**: Metrics collection and alerting
- **Grafana**: Dashboards and visualization
- **JMX Exporter**: JVM metrics collection
- **Actuator**: Application health and metrics endpoints

## Future Features

### Phase 1: Data Processing Pipeline (Next)
- **Python ETL Implementation**: Data extraction, transformation, and loading processes
- **Kafka Stream Processing**: Real-time event processing for telemetry data
- **Apache Kafka Integration**: Message broker setup for event-driven architecture
- **MongoDB Integration**: Semi-structured data storage for telemetry analytics

### Phase 2: Additional Databases
- **Cassandra Implementation**: Time-series database for historical telemetry data
- **Database Synchronization**: Data consistency across multiple database types
- **Data Retention Policies**: Automated management of historical data

### Phase 3: Monitoring Stack
- **Prometheus Integration**: Metrics collection and alerting system
- **Grafana Dashboards**: Custom visualization for system and business metrics
- **JMX Metrics Collection**: Detailed JVM monitoring
- **Alerting Rules**: Proactive notification system for system issues

### Phase 4: Extended Features
- **Predictive Maintenance**: Machine learning algorithms to predict vehicle maintenance needs
- **Route Optimization**: AI-powered route suggestions based on traffic patterns
- **Real-time Vehicle Tracking Map**: Interactive dashboard with live vehicle positions
- **Mobile Applications**: Driver and manager mobile apps

### Phase 5: Enterprise Integration
- **Customer Notification System**: Automated delivery status updates
- **Third-party API Connectors**: Weather, traffic, and map service integrations
- **Advanced Security Features**: Multi-factor authentication and enhanced encryption

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## License

Distributed under the MIT License. See `LICENSE` for more information.

## Acknowledgements

- Spring Boot team for the excellent framework
- Apache Kafka community
- Kubernetes for container orchestration
- All open source libraries and tools used in this project
