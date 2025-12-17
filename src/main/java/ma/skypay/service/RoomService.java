package ma.skypay.service;

import ma.skypay.exception.RoomNotFoundException;
import ma.skypay.exception.UserNotFoundException;
import ma.skypay.model.Room;
import ma.skypay.model.RoomType;

import java.util.ArrayList;
import java.util.Optional;

public class RoomService implements IRoomService {

    @Override
    public void createOrUpdate(ArrayList<Room> rooms, int roomNumber, RoomType roomType, int roomPricePerNight)
    {
        if (rooms == null)
            throw new IllegalArgumentException("room arrayList can not be null");
        if (roomNumber <= 0 )
            throw new IllegalArgumentException("invalid roomNumber");

        if (roomPricePerNight <= 0)
            throw new IllegalArgumentException("invalid roomPricePerNight");

        Optional<Room> result = rooms.stream()
                .filter(room -> room.getNumber() == roomNumber)
                .findFirst();

        if (result.isPresent())
        {
            result.get().setType(roomType);
            result.get().setPricePerNight(roomPricePerNight);
        }
        else
        {
            rooms.add(new Room(roomNumber,roomType, roomPricePerNight));
        }
    }

    @Override
    public Room find(ArrayList<Room> rooms, int roomNumber)
    {

        return rooms.stream()
                .filter(room -> room.getNumber() == roomNumber)
                .findFirst()
                .orElseThrow(() -> new RoomNotFoundException(roomNumber));
    }
}
