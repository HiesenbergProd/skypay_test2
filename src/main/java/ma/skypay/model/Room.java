package ma.skypay.model;

public class Room {

    private final int number;
    private int pricePerNight;
    private RoomType type;


    public Room(int number,RoomType type, int pricePerNight) {
        this.number = number;
        this.pricePerNight = pricePerNight;
        this.type = type;
    }


    public int getNumber() {
        return number;
    }

    public int getPricePerNight() {
        return pricePerNight;
    }

    public RoomType getType() {
        return type;
    }

    public void setPricePerNight(int pricePerNight) {
        this.pricePerNight = pricePerNight;
    }

    public void setType(RoomType type) {
        this.type = type;
    }

}
