package org.example;

import org.example.WebServices.FlightWebService;

import javax.xml.ws.Endpoint;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Endpoint.publish("http://localhost:1000/flight", new FlightWebService());
        System.out.println("Service is running at http://localhost:1000/flight");
    }
}