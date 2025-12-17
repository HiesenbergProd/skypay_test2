package ma.skypay.service;

import ma.skypay.exception.RoomUnavailableException;
import ma.skypay.model.Reservation;
import ma.skypay.model.Room;
import ma.skypay.model.User;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class ReservationService implements IReservationService {


    private final int ADVANCE_RESERVATION_DAYS = 1;

    @Override
    public Reservation createReservation(ArrayList<Reservation> reservations, User user, Room room, LocalDate checkInDate, LocalDate checkOutDate)
    {

        if (!isValidDateRange(checkInDate, checkOutDate))
            throw new IllegalArgumentException("invalid date range");

        if (!isRoomAvailable(reservations, room, checkInDate, checkOutDate))
            throw new RoomUnavailableException(room.getNumber());

        int totalPrice = calculateTotalPrice(room, checkInDate, checkOutDate);

        return new Reservation(user.getId(), room.getNumber(), room.getPricePerNight(), room.getType(), totalPrice,checkInDate, checkOutDate);
    }

    private int calculateTotalPrice(Room room, LocalDate checkInDate, LocalDate checkOutDate)
    {
        long totalNights = ChronoUnit.DAYS.between(checkInDate, checkOutDate);
        return (int) totalNights * room.getPricePerNight();
    }


    private boolean isValidDateRange(LocalDate checkInDate, LocalDate checkOutDate)
    {
        LocalDate advanceReservationDate = LocalDate.now().plusDays(ADVANCE_RESERVATION_DAYS);
        return (checkInDate.isEqual(advanceReservationDate) || checkInDate.isAfter(advanceReservationDate)) &&
                checkInDate.isBefore(checkOutDate);
    }

    private boolean isRoomAvailable(ArrayList<Reservation> reservations, Room room, LocalDate checkInDate, LocalDate checkOutDate)
    {
         return reservations.stream()
                 .noneMatch(r -> r.roomNumber() == room.getNumber() &&
                                            (checkInDate.isBefore(r.checkOutDate()) && checkOutDate.isAfter(r.checkInDate()))
                 );
    }
}
