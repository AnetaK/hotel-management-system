package pl.excercise.web;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.excercise.model.room.ParametrizedRoom;
import pl.excercise.model.room.RoomEntity;
import pl.excercise.service.RoomService;
import pl.excercise.service.Validate;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@WebServlet(urlPatterns = "/availableRooms")
public class AvailableRoomsServlet extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger(AvailableRoomsServlet.class);

    @EJB
    RoomService roomService;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOGGER.trace("Available rooms viewing");

        String roomType = request.getParameter("roomType");
        String windowsExposure = request.getParameter("windowsExposure");
        String availableFrom = request.getParameter("availableFrom");
        String availableTo = request.getParameter("availableTo");

        Validate validate = new Validate();
        if (validate.validateDate(availableFrom, availableTo)) {
            ParametrizedRoom parametrizedRoom = new ParametrizedRoom()
                    .withRoomType(roomType)
                    .withWindowsExposure(windowsExposure)
                    .withAvailableFrom(availableFrom)
                    .withAvailableTo(availableTo)
                    .build();

            List<RoomEntity> availableRooms = roomService.findAvailableRooms(parametrizedRoom);

            if(availableRooms.isEmpty()){
                request.setAttribute("errorMessage", "There is no available rooms for selected criteria!");
            }

            request.setAttribute("availableFrom", availableFrom);
            request.setAttribute("availableTo", availableTo);
            request.setAttribute("availableRooms", availableRooms);


            RequestDispatcher dispatcher = request.getRequestDispatcher("ViewRooms.jsp");
            dispatcher.forward(request, response);
        } else {
            LOGGER.error("Ending date should be before starting date");

            RequestDispatcher dispatcher = request.getRequestDispatcher("Index.jsp");
            dispatcher.forward(request, response);
        }

    }


}
