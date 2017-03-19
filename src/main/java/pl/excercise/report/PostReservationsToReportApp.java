package pl.excercise.report;

import pl.excercise.model.Reservation;
import pl.excercise.model.ReservationDTO;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class PostReservationsToReportApp {

    @Inject
    ReportPropsConfiguration configuration;

    public void postReportData(List<Reservation> reservations) {

        URI uri = UriBuilder.fromUri("http://" + configuration.getReportHost() + ":" + configuration.getReportHostPort())
                .segment("hms-report")
                .segment("api")
                .segment("input")
                .segment("reservations")
                .build();

        List<ReservationDTO> reservationDTOs = new ArrayList<>();
        System.out.println("uri = " + uri.toString());

        for (Reservation r :
                reservations) {
            reservationDTOs.add(new ReservationDTO()
                    .withGuestName(r.getGuest().getFirstName())
                    .withGuestSurname(r.getGuest().getLastName())
                    .withId(r.getId())
                    .withRoomId(r.getRoom().getId())
                    .build()
            );
        }

        Response post = ClientBuilder.newClient()
                .target(uri)
                .request()
                .acceptEncoding("UTF-8")
                .post(Entity.json(reservationDTOs));


        System.out.println("response = " + post.getStatus() + " from " + uri);
    }
}
