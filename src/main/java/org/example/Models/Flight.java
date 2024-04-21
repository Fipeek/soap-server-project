package org.example.Models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Flight {
    private String flightNumber;
    private String departure;
    private String destination;
    private String departureTime;
    private String arrivalTime;
    private String price;

    private Date date;
}
