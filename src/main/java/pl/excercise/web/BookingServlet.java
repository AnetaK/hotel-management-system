package pl.excercise.web;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.excercise.model.GuestSessionScoped;
import pl.excercise.model.Reservation;
import pl.excercise.model.room.ParametrizedRoom;
import pl.excercise.service.ExtractReservations;
import pl.excercise.service.PersistReservation;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = "/bookRoom")
public class BookingServlet extends HttpServlet {

    private static final Logger LOGGER = LogManager.getLogger(BookingServlet.class);

    @Inject
    GuestSessionScoped guest;

    @EJB
    PersistReservation persistReservation;

    @EJB
    ExtractReservations extractReservations;


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOGGER.debug("Creating room reservation...");
        String roomArray[] = request.getParameter("availableRooms").split(";");
        String availableFrom = request.getParameter("availableFrom");
        String availableTo = request.getParameter("availableTo");
        LOGGER.trace("Reservation for period from: {} to: {}", availableFrom, availableTo);
        ParametrizedRoom room = new ParametrizedRoom()
                .withRoomType(roomArray[1])
                .withWindowsExposure(roomArray[2])
                .withAvailableFrom(availableFrom)
                .withAvailableTo(availableTo)
                .build();

        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        // TODO: 20.09.16 dodaj walidację - pola firsName i lastName nie mogę być puste

        LOGGER.trace("Data retrieved from post - firstName.length: {}, lastName.length: {}, roomArray.length: {} ",
                firstName.length(), lastName.length(), roomArray.length);
        long id = Long.parseLong(roomArray[0]);

        guest.withFirstName(firstName)
                .withLastName(lastName)
                .build();

        persistReservation.persist(guest, room, id);

        LOGGER.debug("Room is booked");

        List<Reservation> reservationList = extractReservations.extract(guest);

        request.getSession().setAttribute("emptyList",reservationList.isEmpty());
        request.getSession().setAttribute("reservation", reservationList);
        request.getSession().setAttribute("guest", guest);

        RequestDispatcher dispatcher = request.getRequestDispatcher("ViewReservations.jsp");
        dispatcher.forward(request, response);


    }
}
