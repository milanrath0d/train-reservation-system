package com.milan.reservation;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Application startup class
 *
 * @author Milan Rathod
 */
@SpringBootApplication
@OpenAPIDefinition(
    info = @Info(
        title = "Train Reservation System API",
        version = "1.0.0",
        description = "API documentation for Train Reservation System"
    )
)
public class TrainReservationSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(TrainReservationSystemApplication.class, args);
    }
}