package pl.excercise.model.room;

import javax.persistence.Embeddable;

@Embeddable
public enum RoomType {
    StandardRoom,
    SpecialRoom,
    ExclusiveRoom
}
