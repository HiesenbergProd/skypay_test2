package ma.skypay.service;

import ma.skypay.model.Room;
import ma.skypay.model.RoomType;

import java.util.ArrayList;

public interface IRoomService {
    void createOrUpdate(ArrayList<Room> rooms,int roomNumber, RoomType roomType, int roomPricePerNight);

    Room find(ArrayList<Room> rooms, int roomNumber);
}
