package ma.skypay.service;

import ma.skypay.exception.RoomNotFoundException;
import ma.skypay.model.Room;
import ma.skypay.model.RoomType;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class RoomServiceTest {

    private final RoomService roomService = new RoomService();

    @Test
    void createNewRoom_addsRoomToList() {
        ArrayList<Room> rooms = new ArrayList<>();

        roomService.createOrUpdate(rooms, 101, RoomType.STANDARD, 200);

        assertEquals(1, rooms.size());
        Room r = rooms.getFirst();
        assertEquals(101, r.getNumber());
        assertEquals(RoomType.STANDARD, r.getType());
        assertEquals(200, r.getPricePerNight());
    }

    @Test
    void updateExistingRoom_updatesTypeAndPrice() {
        ArrayList<Room> rooms = new ArrayList<>();
        rooms.add(new Room(101, RoomType.STANDARD, 150));

        roomService.createOrUpdate(rooms, 101, RoomType.JUNIOR, 300);

        assertEquals(1, rooms.size());
        Room r = rooms.getFirst();
        assertEquals(RoomType.JUNIOR, r.getType());
        assertEquals(300, r.getPricePerNight());
    }

    @Test
    void createOrUpdate_withInvalidArgs_throws() {
        assertThrows(IllegalArgumentException.class, () -> roomService.createOrUpdate(null, 1, RoomType.SUITE, 100));
        assertThrows(IllegalArgumentException.class, () -> roomService.createOrUpdate(new ArrayList<>(), 0, RoomType.SUITE, 100));
        assertThrows(IllegalArgumentException.class, () -> roomService.createOrUpdate(new ArrayList<>(), -1, RoomType.SUITE, 100));
        assertThrows(IllegalArgumentException.class, () -> roomService.createOrUpdate(new ArrayList<>(), 10, RoomType.SUITE, 0));
        assertThrows(IllegalArgumentException.class, () -> roomService.createOrUpdate(new ArrayList<>(), 10, RoomType.SUITE, -5));
    }

    @Test
    void find_returnsRoom_whenPresent() {
        ArrayList<Room> rooms = new ArrayList<>();
        Room expected = new Room(501, RoomType.SUITE, 999);
        rooms.add(expected);

        Room actual = roomService.find(rooms, 501);

        assertSame(expected, actual);
    }

    @Test
    void find_throwsWhenNotFound() {
        ArrayList<Room> rooms = new ArrayList<>();
        rooms.add(new Room(1, RoomType.STANDARD, 100));

        assertThrows(RoomNotFoundException.class, () -> roomService.find(rooms, 2));
    }
}
