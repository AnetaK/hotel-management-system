package pl.excercise.web;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(urlPatterns = "/availableRooms")
public class AvailableRoomsForPeriodServlet extends HttpServlet{
    private static final Logger LOGGER = LogManager.getLogger(HotelParamsCache.class);

    @EJB
    RoomsListCache cache;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOGGER.trace("Available rooms viewing");


        String roomType = request.getParameter("roomType");
        String roomExposure = request.getParameter("roomExposure");
        String availableFrom = request.getParameter("availableFrom");
        String availableTo = request.getParameter("availableTo");

//        TODO dodaj walidację daty - from nie może byc późniejsza niż to


        RequestDispatcher dispatcher = request.getRequestDispatcher("ViewRooms.jsp");
        dispatcher.forward(request, response);

    }
}
