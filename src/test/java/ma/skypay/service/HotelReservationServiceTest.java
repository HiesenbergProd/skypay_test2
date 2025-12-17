package ma.skypay.service;

import ma.skypay.model.RoomType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class HotelReservationServiceTest {

    private final PrintStream originalOut = System.out;
    private ByteArrayOutputStream outContent;

    @BeforeEach
    void setupStreams()
    {
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void restoreStreams()
    {
        System.setOut(originalOut);
    }

    @Test
    void constructor_nullGuards()
    {
        IRoomService roomService = new RoomService();
        IReservationService reservationService = new ReservationService();
        IUserService userService = new UserService();

        assertThrows(IllegalArgumentException.class, () -> new HotelReservationService(null, reservationService, userService));
        assertThrows(IllegalArgumentException.class, () -> new HotelReservationService(roomService, null, userService));
        assertThrows(IllegalArgumentException.class, () -> new HotelReservationService(roomService, reservationService, null));
    }

    @Test
    void endToEnd_booking_flow_printsExpectedTablesAndUpdatesBalance()
    {
        HotelReservationService hotel = new HotelReservationService(new RoomService(), new ReservationService(), new UserService());

        hotel.setRoom(101, RoomType.SUITE, 400);
        hotel.setUser(1, 2000);

        LocalDate checkIn = LocalDate.now().plusDays(1);
        LocalDate checkOut = checkIn.plusDays(2); // 2 nights

        hotel.bookRoom(1, 101, checkIn, checkOut);

        // Print and verify contents
        hotel.printAll();
        hotel.printAllUsers();
        String printed = outContent.toString();

        // Rooms table contains the room
        assertTrue(printed.contains("ROOMS:"));
        assertTrue(printed.contains("101"));
        assertTrue(printed.contains("SUITE"));
        assertTrue(printed.contains("400"));

        // Reservations table contains the booking with correct dates
        assertTrue(printed.contains("RESERVATIONS:"));
        assertTrue(printed.contains(checkIn.toString()));
        assertTrue(printed.contains(checkOut.toString()));

        // Users table shows updated balance: 2000 - (2 * 400) = 1200
        assertTrue(printed.contains("USERS:"));
        assertTrue(printed.contains("1"));
        assertTrue(printed.contains("1200"));
    }
}
