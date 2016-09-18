package pl.excercise.web;

import org.slf4j.LoggerFactory;
import pl.excercise.model.Location;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/contact")
public class ContactServlet extends HttpServlet {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(ContactServlet.class);

    @EJB
    HotelParamsCache cache;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOGGER.trace("Hotel contact page opened");

        Location hotelLocation = cache.getHotelLocation();

        request.setAttribute("hotelName",hotelLocation.getHotelName());
        request.setAttribute("street",hotelLocation.getAddress().getStreet());
        request.setAttribute("zipcode",hotelLocation.getAddress().getZipCode());
        request.setAttribute("city",hotelLocation.getAddress().getCity());

        RequestDispatcher dispatcher = request.getRequestDispatcher("Contact.jsp");
        dispatcher.forward(request, response);

    }
}
