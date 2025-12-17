package ma.skypay;

import ma.skypay.model.RoomType;
import ma.skypay.service.*;

import java.time.LocalDate;


public class App 
{
    public static void main( String[] args )
    {
        IRoomService roomService = new RoomService();
        IUserService userService = new UserService();
        IReservationService reservationService = new ReservationService();

        HotelReservationService hotelService = new HotelReservationService(
                roomService,
                reservationService,
                userService);


        // Create rooms
        hotelService.setRoom(1, RoomType.STANDARD, 1000);
        hotelService.setRoom(2, RoomType.JUNIOR, 2000);
        hotelService.setRoom(3, RoomType.SUITE, 3000);

        // Create users
        hotelService.setUser(1, 5000);
        hotelService.setUser(2, 10000);

        // Bookings
        try {
            hotelService.bookRoom(
                    1,
                    2,
                    LocalDate.of(2026, 6, 30),
                    LocalDate.of(2026, 7, 7)
            );
        } catch (Exception e) {
            System.out.println("Booking failed: " + e.getMessage());
        }

        try {
            hotelService.bookRoom(
                    1,
                    2,
                    LocalDate.of(2026, 7, 7),
                    LocalDate.of(2026, 6, 30)
            );
        } catch (Exception e) {
            System.out.println("Booking failed: " + e.getMessage());
        }

        try {
            hotelService.bookRoom(
                    1,
                    1,
                    LocalDate.of(2026, 7, 7),
                    LocalDate.of(2026, 7, 8)
            );
        } catch (Exception e) {
            System.out.println("Booking failed: " + e.getMessage());
        }

        try {
            hotelService.bookRoom(
                    2,
                    1,
                    LocalDate.of(2026, 7, 7),
                    LocalDate.of(2026, 7, 9)
            );
        } catch (Exception e) {
            System.out.println("Booking failed: " + e.getMessage());
        }

        try {
            hotelService.bookRoom(
                    2,
                    3,
                    LocalDate.of(2026, 7, 7),
                    LocalDate.of(2026, 7, 8)
            );
        } catch (Exception e) {
            System.out.println("Booking failed: " + e.getMessage());
        }

        // Update room (must not affect previous bookings)
        hotelService.setRoom(1, RoomType.SUITE, 10000);

        // Print results
        System.out.println("\n===== ALL =====");
        hotelService.printAll();

        System.out.println("\n===== ALL USERS =====");
        hotelService.printAllUsers();
    }
}
