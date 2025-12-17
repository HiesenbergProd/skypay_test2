package ma.skypay.exception;

public class RoomUnavailableException extends RuntimeException {
    public RoomUnavailableException(int roomNumber) {
        super("Room is unavailable: " + roomNumber);
    }
}