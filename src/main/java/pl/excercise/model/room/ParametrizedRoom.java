package pl.excercise.model.room;


public class ParametrizedRoom {

    String roomType;
    String windowsExposure;
    String availableFrom;
    String availableTo;

    public ParametrizedRoom() {
    }

    public ParametrizedRoom(String roomType, String windowsExposure, String availableFrom, String availableTo) {
        this.roomType = roomType;
        this.windowsExposure = windowsExposure;
        this.availableFrom = availableFrom;
        this.availableTo = availableTo;
    }

    public ParametrizedRoom withRoomType(String roomType) {
        this.roomType = roomType;
        return this;
    }

    public ParametrizedRoom withRoomExposure(String roomExposure) {
        this.windowsExposure = roomExposure;
        return this;
    }

    public ParametrizedRoom withAvailableFrom(String availableFrom) {
        this.availableFrom = availableFrom;
        return this;
    }

    public ParametrizedRoom withAvailableTo(String availableTo) {
        this.availableTo = availableTo;
        return this;
    }

    public ParametrizedRoom build() {
        return new ParametrizedRoom(roomType, windowsExposure, availableFrom, availableTo);
    }

    public String getRoomType() {
        return roomType;
    }

    public String getWindowsExposure() {
        return windowsExposure;
    }

    public String getAvailableFrom() {
        return availableFrom;
    }

    public String getAvailableTo() {
        return availableTo;
    }

    @Override
    public String toString() {
        return "ParametrizedRoom{" +
                "roomType='" + roomType + '\'' +
                ", windowsExposure='" + windowsExposure + '\'' +
                ", availableFrom='" + availableFrom + '\'' +
                ", availableTo='" + availableTo + '\'' +
                '}';
    }
}
