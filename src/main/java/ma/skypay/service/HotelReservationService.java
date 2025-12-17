package ma.skypay.service;


import ma.skypay.model.Reservation;
import ma.skypay.model.Room;
import ma.skypay.model.RoomType;
import ma.skypay.model.User;

import java.time.LocalDate;
import java.util.ArrayList;


public class HotelReservationService
{
    private final ArrayList<Room> rooms;
    private final ArrayList<User> users;
    private final ArrayList<Reservation> reservations;

    private final IRoomService roomService;
    private final IReservationService reservationService;
    private final IUserService userService;


    public HotelReservationService(IRoomService roomService, IReservationService reservationService, IUserService userService)
    {
        this.rooms = new ArrayList<>();
        this.users = new ArrayList<>();
        this.reservations = new ArrayList<>();

        if (roomService == null)
            throw  new IllegalArgumentException("userService cant not be null");
        if (reservationService == null)
            throw  new IllegalArgumentException("reservationService cant not be null");
        if (userService == null)
            throw  new IllegalArgumentException("userService cant not be null");


        this.roomService = roomService;
        this.reservationService = reservationService;
        this.userService = userService;
    }


    public void setRoom(int roomNumber, RoomType roomType, int roomPricePerNight)
    {
        roomService.createOrUpdate(rooms,roomNumber, roomType,roomPricePerNight);
    }

    public void setUser(int userId, int balance)
    {
        userService.createOrUpdate(users, userId, balance);
    }

    public void bookRoom(int userId, int roomNumber, LocalDate checkInDate, LocalDate checkOutDate)
    {
        User user = userService.find(users, userId);
        Room room = roomService.find(rooms, roomNumber);
        Reservation reservation = reservationService.createReservation(reservations, user, room, checkInDate, checkOutDate);
        userService.charge(user,reservation.totalPrice());
        reservations.add(reservation);
    }

    public void printAll()
    {
        printRoomsTable();
        printBookingsTable();
    }


    public void printAllUsers()
    {
        printUsersTable();
    }


    private  void printRoomsTable()
    {
        System.out.println("ROOMS:");
        System.out.println("+--------+----------+---------------+");
        System.out.println("| Number | Type     | Price/Night   |");
        System.out.println("+--------+----------+---------------+");

        for (int i = rooms.size() - 1; i >= 0; i--) {
            Room r = rooms.get(i);
            System.out.printf("| %-6d | %-8s | %-13d |\n",
                    r.getNumber(), r.getType(), r.getPricePerNight());
        }

        System.out.println("+--------+----------+---------------+");
    }


    private  void printBookingsTable()
    {
        System.out.println("RESERVATIONS:");
        System.out.println("+--------+--------+----------+------------+------------+------------+");
        System.out.println("| UserID | Room # | Type     | Price/Night| CheckIn    | CheckOut   |");
        System.out.println("+--------+--------+----------+------------+------------+------------+");

        for (int i = reservations.size() - 1; i >= 0; i--) {
            Reservation reservation = reservations.get(i);
            System.out.printf("| %-6d | %-6d | %-8s | %-10d | %-10s | %-10s |\n",
                    reservation.userId(), reservation.roomNumber(), reservation.roomType(),
                    reservation.pricePerNight(), reservation.checkInDate(), reservation.checkOutDate());
        }

        System.out.println("+--------+--------+----------+------------+------------+------------+");
    }

    private void printUsersTable()
    {
        System.out.println("USERS:");
        System.out.println("+--------+--------+");
        System.out.println("| UserID | Balance|");
        System.out.println("+--------+--------+");

        for (int i = users.size() - 1; i >= 0; i--) {
            User u = users.get(i);
            System.out.printf("| %-6d | %-6d |\n", u.getId(), u.getBalance());
        }

        System.out.println("+--------+--------+");
    }

}
