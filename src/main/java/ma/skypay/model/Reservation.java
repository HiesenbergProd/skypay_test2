package ma.skypay.model;

import java.time.LocalDate;

public record Reservation(
        int userId,
        int roomNumber,
        int pricePerNight,
        RoomType roomType,
        int totalPrice,
        LocalDate checkInDate,
        LocalDate checkOutDate
){}
