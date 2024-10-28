package se.deved;

public class Transaction {

    private int id;
    @IgnoreField(skip = false)
    private int amount;
    private String description;

    public Transaction(int id, int amount, String description) {
        this.id = id;
        this.amount = amount;
        this.description = description;
    }
}
