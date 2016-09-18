package pl.excercise.service;


import pl.excercise.model.room.RoomType;
import pl.excercise.model.room.WindowsExposure;

import java.time.LocalDate;
import java.util.*;

public class RandomRooms {

    private List<RoomType> roomTypes = new ArrayList<RoomType>(Arrays.asList(RoomType.values()));
    private List<WindowsExposure> windowsExposures = new ArrayList<WindowsExposure>(Arrays.asList(WindowsExposure.values()));
    private List<String> localDates = new ArrayList<>();

    public List<String> getRandomDates() {
        LocalDate now = LocalDate.now();
        Random rand = new Random();

        for (int i = 0; i < 5; i++) {
            LocalDate date = now.plusDays(rand.nextInt(365));
            localDates.add(date.toString());
        }

        return localDates;
    }

    public String getRandomType() {

        int index = Integer.MAX_VALUE;
        if (index >= roomTypes.size()) {
            Collections.shuffle(roomTypes);
            index = 0;
        }
        return roomTypes.get(index++).toString();
    }

    public String getRandomExposure() {

        int index = Integer.MAX_VALUE;
        if (index >= windowsExposures.size()) {
            Collections.shuffle(windowsExposures);
            index = 0;
        }
        return windowsExposures.get(index++).toString();
    }

    @Override
    public String toString() {
        return "RandomRooms{" +
                "roomTypes=" + roomTypes +
                ", windowsExposures=" + windowsExposures +
                ", localDates=" + localDates +
                '}';
    }
}
