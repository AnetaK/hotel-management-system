package pl.excercise.web;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.excercise.model.room.RoomEntity;
import pl.excercise.model.room.RoomType;
import pl.excercise.model.room.WindowsExposure;
import pl.excercise.service.RoomService;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(urlPatterns = "/select")
public class SelectRoomParams extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger(HotelParamsCache.class);

    @EJB
    RoomService roomService;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOGGER.trace("Room parameter selection page opened");

        List<LocalDate> dates = new ArrayList<>();
        LocalDate now = LocalDate.now();
        for (long i = 0; i < 365; i++) {
            dates.add(now.plusDays(i));
        }

        request.setAttribute("roomType", Arrays.asList(RoomType.StandardRoom, RoomType.SpecialRoom, RoomType.ExclusiveRoom));
        request.setAttribute("windowsExposure", Arrays.asList(WindowsExposure.NORTH, WindowsExposure.WEST, WindowsExposure.SOUTH, WindowsExposure.EAST));
        request.setAttribute("calendar", dates);

        List<RoomEntity> allRooms = roomService.findAllRooms();

        Comparator<RoomEntity> comparator = Comparator.comparing(roomEntity -> roomEntity.getRoomType());
        comparator = comparator.thenComparing(Comparator.comparing(roomEntity -> roomEntity.getWindowsExposure()));

        List<RoomEntity> sortedRooms = allRooms.stream().sorted(comparator).collect(Collectors.toList());

        request.setAttribute("allRooms", sortedRooms);

        RequestDispatcher dispatcher = request.getRequestDispatcher("SelectRoomParams.jsp");
        dispatcher.forward(request, response);

    }


}
