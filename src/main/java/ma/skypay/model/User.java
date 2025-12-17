package ma.skypay.model;

public class User {

    private final int id;
    private int balance;

    public User(int id, int balance)
    {
        if (id <= 0)
            throw  new IllegalArgumentException("invalid user id");
        if (balance < 0)
            throw  new IllegalArgumentException("negative balance");
        this.id = id;
        this.balance = balance;
    }

    public int getBalance()
    {
        return balance;
    }

    public int getId()
    {
        return id;
    }

    public void setBalance(int balance)
    {
        this.balance = balance;
    }
}
