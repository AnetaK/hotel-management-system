package pl.excercise.report;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.excercise.model.Reservation;
import pl.excercise.service.ReservationService;

import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.util.List;

@Stateless
public class ReportTrigger {

    @Inject
    ReportPropsConfiguration configuration;

    @EJB
    PostReservationsToReportApp post;

    @EJB
    ReservationService reservationService;

    private static final Logger LOGGER = LogManager.getLogger(ReportTrigger.class);

    @Schedule( hour = "*", minute = "*/2")
    public void postReservations() {

        List<Reservation> reservations = reservationService.extractAllReservations();

        if (reservations.size() > 0) {
            Response response = this.post.post(reservations);

            if (response.getStatus()==201) {
                LOGGER.trace(reservations.size() + " servations posted to reporting app");
            } else {
                LOGGER.warn("Reservations weren't posted correctly");
            }
        } else {
            LOGGER.trace("There is no reservations to send");
        }
    }

}
