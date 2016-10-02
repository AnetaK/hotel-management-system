package pl.excercise.web;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.excercise.model.GuestSessionScoped;
import pl.excercise.model.Reservation;
import pl.excercise.model.room.ParametrizedRoom;
import pl.excercise.service.ReservarionService;
import pl.excercise.service.Validate;

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
    ReservarionService reservarionService;

    @EJB
    Validate validate;


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

        long id = Long.parseLong(roomArray[0]);

        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");

        guest.withFirstName(firstName)
                .withLastName(lastName)
                .build();

        if (validate.validateNameContent(firstName,lastName)) {
            reservarionService.createReservation(guest, room, id);

            LOGGER.debug("Room is booked");

            List<Reservation> reservationList = reservarionService.extractReservationsForGuest(guest);

            request.getSession().setAttribute("emptyList",reservationList.isEmpty());
            request.getSession().setAttribute("reservation", reservationList);
            request.getSession().setAttribute("guest", guest);


            RequestDispatcher dispatcher = request.getRequestDispatcher("ViewReservations.jsp");
            dispatcher.forward(request, response);

        } else {

            LOGGER.error("Wrong form data ");

            RequestDispatcher dispatcher = request.getRequestDispatcher("Index.jsp");
            dispatcher.forward(request, response);
        }

    }


}
