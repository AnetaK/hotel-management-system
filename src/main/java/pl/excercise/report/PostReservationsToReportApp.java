package pl.excercise.report;

import pl.excercise.model.Reservation;

import javax.ejb.Stateless;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.List;

@Stateless
public class PostReservationsToReportApp {

    public void postReportData(List<Reservation> reservations) {

//        This uri should be used if docker in this app is runned
//        URI uri = UriBuilder.fromUri("http://jboss_report:8080/hms-report/api/input/reservations").build();

//        This uri should be used in case this app is runned with mvn package wildfly:run
//        TODO: Parameter file with optional address
        URI uri = UriBuilder.fromUri("http://localhost:18080/hms-report/api/input/reservations").build();

//        String json = new Gson().toJson(reservations);



        Response post = ClientBuilder.newClient()
                .target(uri)
                .request()
                .acceptEncoding("UTF-8")
                .post(Entity.json(reservations.get(0)));


        System.out.println("response = " + post.getStatus() + " from " + uri);
    }
}