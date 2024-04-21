package org.example.WebServices;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import org.example.Models.Flight;
import org.example.Models.Ticket;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.ws.soap.MTOM;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;




@MTOM
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT)
@WebService
public class FlightWebService {

    private List<Flight> flights;

    private List<Ticket> tickets;

    public FlightWebService() {
        tickets = new ArrayList<>();
        flights = new ArrayList<>();
        flights.add(new Flight("FN101", "New York", "London", "10:00", "20:00", "500", new Date(2022, 12, 1)));
        flights.add(new Flight("FN102", "Paris", "Berlin", "12:00", "14:00", "200", new Date(2022, 12, 2)));
        flights.add(new Flight("FN103", "Tokyo", "Sydney", "22:00", "10:00", "800", new Date(2022, 12, 3)));
        flights.add(new Flight("FN104", "Berlin", "Paris", "15:00", "17:00", "300", new Date(2022, 12, 4)));
        flights.add(new Flight("FN105", "London", "New York", "16:00", "26:00", "600", new Date(2022, 12, 5)));
        flights.add(new Flight("FN106", "Sydney", "Tokyo", "23:00", "11:00", "900", new Date(2022, 12, 6)));
        flights.add(new Flight("FN107", "New York", "Paris", "10:00", "20:00", "400", new Date(2022, 12, 7)));
        flights.add(new Flight("FN108", "Paris", "New York", "12:00", "22:00", "500", new Date(2022, 12, 8)));
        flights.add(new Flight("FN109", "Tokyo", "Berlin", "22:00", "08:00", "700", new Date(2022, 12, 9)));
        flights.add(new Flight("FN110", "Berlin", "Tokyo", "23:00", "09:00", "800", new Date(2022, 12, 10)));
    }
    @WebMethod
    public DataHandler buyTicket(String flightNumber) {
        UUID randomUUID = UUID.randomUUID();
        String ticketNumber = randomUUID.toString();
        Flight flight = flights.stream()
                .filter(f -> f.getFlightNumber().equals(flightNumber))
                .findFirst()
                .orElse(null);

        if (flight == null) {
            return new DataHandler("Flight not found", "text/plain");
        }

        Ticket ticket = new Ticket(ticketNumber, flight);
        tickets.add(ticket);
        String path = "C:\\Users\\bialowasf\\Desktop\\Studia\\Rsi\\SOAP_SERVER_PROJECTV2\\src\\main\\resources\\" + ticketNumber + ".pdf";

        try (PdfWriter writer = new PdfWriter(path);
             PdfDocument pdf = new PdfDocument(writer);
             Document document = new Document(pdf)) {

            document.add(new Paragraph("Ticket number: " + ticketNumber));
            document.add(new Paragraph("Flight number: " + flight.getFlightNumber()));
            document.add(new Paragraph("Departure: " + flight.getDeparture()));
            document.add(new Paragraph("Destination: " + flight.getDestination()));
            document.add(new Paragraph("Departure time: " + flight.getDepartureTime()));
            document.add(new Paragraph("Arrival time: " + flight.getArrivalTime()));
            document.add(new Paragraph("Price: " + flight.getPrice()));
            document.add(new Paragraph("Date: " + flight.getDate()));
        } catch (Exception e) {
            e.printStackTrace();
            return new DataHandler("Error generating ticket", "text/plain");
        }

        File pdfFile = new File(path);
        return new DataHandler(new FileDataSource(pdfFile));
    }


    @WebMethod
    public Ticket getTicketByNumber(String ticketNumber) {
        for (Ticket ticket : tickets) {
            if (ticket.getTicketNumber().equals(ticketNumber)) {
                return ticket;
            }
        }
        return null;
    }


    @WebMethod
    public List<Flight> getFlights() {
        return flights;
    }

    @WebMethod
    public List<Flight> getFlightsByDeparture(String departure) {
        List<Flight> result = new ArrayList<>();
        for (Flight flight : flights) {
            if (flight.getDeparture().equals(departure)) {
                result.add(flight);
            }
        }
        return result;
    }

    @WebMethod
    public List<Flight> getFlightsByDestination(String destination) {
        List<Flight> result = new ArrayList<>();
        for (Flight flight : flights) {
            if (flight.getDestination().equals(destination)) {
                result.add(flight);
            }
        }
        return result;
    }

    @WebMethod
    public List<Flight> getFlightsByDepartureAndDestination(String departure, String destination) {
        List<Flight> result = new ArrayList<>();
        for (Flight flight : flights) {
            if (flight.getDeparture().equals(departure) && flight.getDestination().equals(destination)) {
                result.add(flight);
            }
        }
        return result;
    }

    @WebMethod
    public List<Flight> getFlightsByDepartureAndDestinationAndDate(String departure, String destination, Date date) {
        List<Flight> result = new ArrayList<>();
        for (Flight flight : flights) {
            if (flight.getDeparture().equals(departure) && flight.getDestination().equals(destination) && flight.getDate().equals(date)) {
                result.add(flight);
            }
        }
        return result;
    }
}