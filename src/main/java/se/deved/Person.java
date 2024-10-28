package se.deved;

import java.util.ArrayList;
import java.util.List;

@MyAnnotation(value = "Hej!", amount = 4)
public class Person implements Printable {

    @MyAnnotation(value = "Hej igen!", amount = 9)
    private String name;
    private int age;
    private String email;
    private double height;
    @IgnoreField(skip = true)
    private String password = "superman";

    private Transaction transaction = new Transaction(4, 4, "4");
    private List<Transaction> transactionList = new ArrayList<>();

    public Person() {
    }

    public Person(String name, int age, String email, double height) {
        this.name = name;
        this.age = age;
        this.email = email;
        this.height = height;
        transactionList.add(transaction);
        transactionList.add(transaction);
    }

    private void printName() {
        System.out.println(this.name);
    }

    @Override
    public void print() {

    }

    @Deprecated
    public void testing() {
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", height=" + height +
                '}';
    }
}
