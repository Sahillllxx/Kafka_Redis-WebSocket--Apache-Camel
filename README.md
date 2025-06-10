# ✈️ Flight Tracking with Kafka, Redis, WebSocket & Apache Camel

**Live Flight Tracking System**  
This project is a real-time flight tracking solution built using Spring Boot, Apache Camel, Kafka, Redis, and WebSocket. It demonstrates an end-to-end pipeline where flight status updates are consumed from Kafka, processed and stored in Redis, and instantly broadcast to web clients via WebSocket.

---

## 🗺️ How It Works

![architecture](https://github.com/user-attachments/assets/e73c12b0-f95e-4376-a81b-dc56c9e59ecf)


1. **Kafka Integration:**  
   - The system listens to a Kafka topic for incoming flight status updates (JSON format).

2. **Apache Camel Routing:**  
   - Apache Camel routes the messages, unmarshals the JSON, and hands them off to a custom processor.
   - The processor converts raw flight status data into a DTO object and serializes it as JSON.

3. **Redis Storage:**  
   - Each processed flight update is stored in Redis, keyed by flight number for fast retrieval.

4. **Real-Time WebSocket Broadcasting:**  
   - The processed data is sent to all connected clients through a WebSocket endpoint.
   - Clients receive live flight updates instantly without polling.

---

## 🚀 Getting Started

### Prerequisites

- Java 17+
- Kafka broker running
- Redis server running

### Run the Application

```sh
# In the root directory
./mvnw spring-boot:run
```

### Kafka Setup

- Configure your Kafka broker and input topic in `application.properties` (see `camel.component.kafka.brokers` and `camel.component.kafka.input-topic`).

### Redis Setup

- Ensure Redis is running and reachable by your application.

---

## 💻 Live Demo: Web Interface

An interactive web page (`flight-tracker.html`) is provided to view real-time updates.  
Open [http://localhost:8081/flight-tracker.html](http://localhost:8081/flight-tracker.html) after starting the app.

**WebSocket endpoint:**  
`ws://localhost:8081/websocket/flight/live`

Incoming flight updates are displayed live!

---

## 🧩 Core Components

- **Kafka Consumer:** Consumes real-time flight updates.
- **Apache Camel Route:** Routes data, handles errors, and processes business logic.
- **Redis:** Stores the latest status of each flight.
- **WebSocket Server:** Broadcasts updates to all connected clients.
- **HTML/JS Client:** Receives and displays updates in real-time.

---

## 🏗️ Project Structure

- `src/main/java/com/learning/route/FlightTrackingRoute.java` — Camel route for processing pipeline.
- `src/main/java/com/learning/process/FlightDataProcessor.java` — Processes and stores flight data.
- `src/main/java/com/learning/utils/FlightWebSocketHandler.java` — WebSocket broadcast handler.
- `src/main/resources/static/flight-tracker.html` — Interactive live-update web client.

---

## ⚡ Example Flow

1. Producer sends a flight status update to Kafka.
2. Camel route consumes, processes, and stores the update in Redis.
3. The update is broadcast over WebSocket to all web clients.
4. Web clients instantly display the new flight status.

---

## 🛠️ Tech Stack

- **Java / Spring Boot**
- **Apache Camel**
- **Kafka**
- **Redis**
- **WebSocket**
- **HTML/JavaScript (for live client)**
