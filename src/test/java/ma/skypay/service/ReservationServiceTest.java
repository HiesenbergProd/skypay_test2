package ma.skypay.service;

import ma.skypay.exception.RoomUnavailableException;
import ma.skypay.model.Reservation;
import ma.skypay.model.Room;
import ma.skypay.model.RoomType;
import ma.skypay.model.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ReservationServiceTest {

    private final ReservationService reservationService = new ReservationService();

    @Test
    void createReservation_withValidInput_returnsReservationAndCorrectTotal() {
        ArrayList<Reservation> reservations = new ArrayList<>();
        User user = new User(1, 10_000);
        Room room = new Room(101, RoomType.JUNIOR, 250);
        LocalDate checkIn = LocalDate.now().plusDays(1);
        LocalDate checkOut = checkIn.plusDays(3);

        Reservation r = reservationService.createReservation(reservations, user, room, checkIn, checkOut);

        assertNotNull(r);
        assertEquals(user.getId(), r.userId());
        assertEquals(room.getNumber(), r.roomNumber());
        assertEquals(room.getPricePerNight(), r.pricePerNight());
        assertEquals(RoomType.JUNIOR, r.roomType());
        assertEquals(3 * 250, r.totalPrice());
    }

    @Test
    void createReservation_withInvalidDateRange_throws() {
        ArrayList<Reservation> reservations = new ArrayList<>();
        User user = new User(1, 10_000);
        Room room = new Room(101, RoomType.SUITE, 500);

        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(1);

        // check-in today (before allowed advance day)
        assertThrows(IllegalArgumentException.class, () ->
                reservationService.createReservation(reservations, user, room, today, tomorrow));

        // checkout before checkin
        LocalDate checkIn = LocalDate.now().plusDays(2);
        LocalDate checkOut = checkIn.minusDays(1);
        assertThrows(IllegalArgumentException.class, () ->
                reservationService.createReservation(reservations, user, room, checkIn, checkOut));
    }

    @Test
    void createReservation_whenRoomOverlaps_throwsRoomUnavailable() {
        ArrayList<Reservation> reservations = new ArrayList<>();
        User user = new User(1, 10_000);
        Room room = new Room(101, RoomType.STANDARD, 100);

        LocalDate base = LocalDate.now().plusDays(1);
        reservations.add(new Reservation(user.getId(), room.getNumber(), room.getPricePerNight(), room.getType(), 200,
                base, base.plusDays(2)));

        // Overlapping new reservation
        assertThrows(RoomUnavailableException.class, () ->
                reservationService.createReservation(reservations, user, room, base.plusDays(1), base.plusDays(3)));
    }
}
