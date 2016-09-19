package pl.excercise.web;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.excercise.model.Guest;
import pl.excercise.model.room.ParametrizedRoom;
import pl.excercise.service.PersistReservation;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/bookRoom")
public class BookingServlet extends HttpServlet{

    private static final Logger LOGGER = LogManager.getLogger(BookingServlet.class);

    @Inject
    Guest guest;

    @EJB
    PersistReservation persistReservation;


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOGGER.debug("Creating room reservation...");
        String roomArray[] = request.getParameter("availableRooms").split(";");
        String availableFrom = request.getParameter("availableFrom");
        String availableTo = request.getParameter("availableTo");
        LOGGER.trace("Reservation for perion from: {} to: {}",availableFrom,availableTo);
        ParametrizedRoom room = new ParametrizedRoom()
                .withRoomType(roomArray[1])
                .withWindowsExposure(roomArray[2])
                .withAvailableFrom(availableFrom)
                .withAvailableTo(availableTo)
                .build();

        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        LOGGER.trace("Data retrieved from post - firstName.length: {}, lastName.length: {}, roomArray.length: {} ",
                firstName.length(), lastName.length(),roomArray.length );
        int id = Integer.parseInt(roomArray[0]);

        guest.withFirstName(firstName)
                .withLastName(lastName)
                .build();

        persistReservation.persist(guest,room,id);

        LOGGER.debug("Room is booked");
        response.sendRedirect("Contact.jsp");


    }
}
