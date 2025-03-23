# Train Reservation System

A modern, scalable train reservation system built with Spring Boot that provides comprehensive APIs for managing train bookings, schedules, fares, and related services.

## Features

- **Booking Management**
  - Create and manage train ticket bookings
  - PNR status tracking
  - Booking cancellation with refund calculation
  - Booking history and statistics

- **Train Management**
  - Train search and availability
  - Schedule management
  - Route management
  - Coach and class configuration

- **Station Management**
  - Station information and facilities
  - Platform management
  - Zone-wise station grouping
  - Station connectivity information

- **Fare Management**
  - Dynamic fare calculation
  - Class-wise fare configuration
  - Bulk fare updates
  - Route-based fare management

- **User Management**
  - User profile management
  - Booking history
  - Preferences management
  - Notification settings

- **Schedule Management**
  - Train schedule tracking
  - Delay management
  - Station-wise timings
  - Platform allocation

- **Notification System**
  - Booking confirmations
  - PNR status updates
  - Schedule change alerts
  - Custom notification preferences

## Technology Stack

- Java 11
- Spring Boot 2.7.x
- Spring Data JPA
- Spring Security
- PostgreSQL
- Maven
- Lombok
- Swagger/OpenAPI

## Getting Started

### Prerequisites

- JDK 11 or later
- Maven 3.6.x or later
- PostgreSQL 12 or later

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/train-reservation-system.git
   ```

2. Navigate to the project directory:
   ```bash
   cd train-reservation-system
   ```

3. Configure the database:
   - Create a PostgreSQL database
   - Update `application.properties` with your database credentials

4. Build the project:
   ```bash
   mvn clean install
   ```

5. Run the application:
   ```bash
   mvn spring-boot:run
   ```

The application will start on `http://localhost:8080`

## API Documentation

### Booking APIs
- `POST /bookings` - Create a new booking
- `GET /bookings/{pnrNumber}` - Get booking details by PNR
- `GET /bookings/user/{userId}` - Get user's booking history
- `POST /bookings/{pnrNumber}/cancel` - Cancel a booking
- `GET /bookings/{pnrNumber}/refund-amount` - Calculate refund amount

### Train APIs
- `GET /trains/search` - Search trains between stations
- `GET /trains/{trainNumber}` - Get train details
- `GET /trains/station/{stationCode}` - Get trains at a station
- `GET /trains/{trainNumber}/schedule` - Get train schedule

### Station APIs
- `GET /stations/search` - Search stations
- `GET /stations/{stationCode}` - Get station details
- `GET /stations/{stationCode}/trains` - Get trains at station
- `GET /stations/{stationCode}/facilities` - Get station facilities

### Schedule APIs
- `GET /schedules/train/{trainNumber}` - Get train schedule
- `GET /schedules/station/{stationCode}` - Get station schedule
- `POST /schedules/train/{trainNumber}` - Update train schedule
- `GET /schedules/train/{trainNumber}/delays` - Get delay history

### Fare APIs
- `GET /fares/train/{trainNumber}` - Get train fares
- `GET /fares/route` - Get route fares
- `POST /fares/train/{trainNumber}` - Update train fares
- `POST /fares/bulk-update` - Bulk update fares

### User APIs
- `GET /users/{userId}` - Get user profile
- `PUT /users/{userId}` - Update user profile
- `GET /users/{userId}/bookings` - Get user bookings
- `GET /users/{userId}/preferences` - Get user preferences

### Notification APIs
- `GET /notifications/user/{userId}` - Get user notifications
- `POST /notifications/send` - Send notification
- `POST /notifications/{notificationId}/mark-read` - Mark notification as read
- `GET /notifications/user/{userId}/preferences` - Get notification preferences

## Error Handling

The API uses standard HTTP status codes and returns error responses in the following format:

```json
{
    "status": 400,
    "error": "Bad Request",
    "message": "Invalid input",
    "details": ["Field 'xyz' is required"]
}
```

Common status codes:
- 200: Success
- 201: Created
- 400: Bad Request
- 401: Unauthorized
- 403: Forbidden
- 404: Not Found
- 500: Internal Server Error

## Security

The API is secured using JWT-based authentication. Include the JWT token in the Authorization header:

```
Authorization: Bearer <your-jwt-token>
```

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Authors

- Milan Rathod - *Initial work* - [milanrathod](https://github.com/milanrathod)

## Acknowledgments

- Spring Boot team for the excellent framework
- The open-source community for various helpful libraries
- Indian Railways for inspiration and domain knowledge
