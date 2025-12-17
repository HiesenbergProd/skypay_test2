package ma.skypay.service;

import ma.skypay.model.Reservation;
import ma.skypay.model.Room;
import ma.skypay.model.User;

import java.time.LocalDate;
import java.util.ArrayList;

public interface IReservationService {
    Reservation createReservation(ArrayList<Reservation> reservations, User user, Room room, LocalDate checkInDate, LocalDate checkOutDate);
}
