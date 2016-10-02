package pl.excercise.web;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.excercise.model.GuestSessionScoped;
import pl.excercise.model.Reservation;
import pl.excercise.model.room.ReservationDate;
import pl.excercise.service.DaysCount;
import pl.excercise.service.ReservarionService;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@WebServlet(urlPatterns = "/cancelReservation")
public class CancellingServlet extends HttpServlet {

    private static final Logger LOGGER = LogManager.getLogger(CancellingServlet.class);

    @Inject
    GuestSessionScoped guest;

    @EJB
    ReservarionService reservationService;


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOGGER.debug("Canceling room reservation...");

        String[] cancel = request.getParameter("cancel").split(";");
        long reservationId = Long.parseLong(cancel[0]);
        String cancelFrom = cancel[1];
        String cancelTo = cancel[2];

        DaysCount daysCount = new DaysCount();
        List<ReservationDate> datesToCancel = daysCount.returnDaysList(cancelFrom, cancelTo);

        reservationService.cancelReservation(reservationId,datesToCancel);

        List<Reservation> reservationList = reservationService.extractReservationsForGuest(guest);

        request.getSession().setAttribute("emptyList",reservationList.isEmpty());
        request.getSession().setAttribute("reservation", reservationList);
        request.getSession().setAttribute("guest", guest);

        LOGGER.trace("Cancelled flag[0]: " + reservationList.get(0).getCancelledFlag());

        RequestDispatcher dispatcher = request.getRequestDispatcher("ViewReservations.jsp");
        dispatcher.forward(request, response);


    }
}
