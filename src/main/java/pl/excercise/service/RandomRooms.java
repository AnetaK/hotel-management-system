package pl.excercise.service;


import pl.excercise.model.room.RoomType;
import pl.excercise.model.room.WindowsExposure;

import javax.ejb.Stateless;
import java.time.LocalDate;
import java.util.*;

@Stateless
public class RandomRooms {

    public List<String> getRandomDates() {
        List<String> localDates = new ArrayList<>();
        LocalDate now = LocalDate.now();
        Random rand = new Random();

        for (int i = 0; i < 5; i++) {
            LocalDate date = now.plusDays(rand.nextInt(365));
            localDates.add(date.toString());
        }

        return localDates;
    }

    public String getRandomType() {
        List<RoomType> roomTypes = new ArrayList<RoomType>(Arrays.asList(RoomType.values()));


        int index = Integer.MAX_VALUE;
        if (index >= roomTypes.size()) {
            Collections.shuffle(roomTypes);
            index = 0;
        }
        return roomTypes.get(index++).toString();
    }

    public String getRandomExposure() {
        List<WindowsExposure> windowsExposures = new ArrayList<WindowsExposure>(Arrays.asList(WindowsExposure.values()));

        int index = Integer.MAX_VALUE;
        if (index >= windowsExposures.size()) {
            Collections.shuffle(windowsExposures);
            index = 0;
        }
        return windowsExposures.get(index++).toString();
    }

}
