package se.deved;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.function.Predicate;

public class Main {
    public static void main(String[] args) {
        // Reflection
        // Metaprogrammering
        Person person = new Person();
        Class<Person> clazz = Person.class;

        for (Field field : clazz.getDeclaredFields()) {
            System.out.println(field.getName());
        }

        // person.name = "Ironman";

        // Kom åt privat variabel
        try {
            Field nameField = clazz.getDeclaredField("name");

            nameField.setAccessible(true);
            nameField.set(person, "Ironman");
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        // String personName = Main.<Person, String>getFieldValue(person, "name");
        // int amount = Main.<Transaction, Integer>getFieldValue(person, "amount");

        // Kom åt privat metod
        try {
            Method printNameMethod = clazz.getDeclaredMethod("printName");
            printNameMethod.setAccessible(true);
            printNameMethod.invoke(person);
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        // Skapa objekt genom reflection
        Person person2;
        try {
            Constructor<Person> constructor = clazz.getDeclaredConstructor();

            // new Person();
            person2 = constructor.newInstance();
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        System.out.println("-------");

        // Annotations
        person.testing();

        Person ironman = new Person("Ironman", 5, "tony", 4.0);
        Person superman = new Person("Superman", 7, "clark", 7.0);

        try {
            printAnyObject(ironman);
            printAnyObject(new Transaction(4, 600, "Köpte glass."));

            Predicate<Integer> lam = (a) -> a == 5 || person == null;
            printAnyObject(lam);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public static <T, R> R getFieldValue(T value, String fieldName) {
        Class clazz = value.getClass();
        try {
            Field field = clazz.getDeclaredField(fieldName);

            field.setAccessible(true);
            return (R) field.get(value);
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public static <T> void printAnyObject(T value) throws Exception {
        Class clazz =  value.getClass();
        System.out.println(clazz.getSimpleName() + "{");

        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);

            IgnoreField ignoreField = field.getDeclaredAnnotation(IgnoreField.class);
            if (ignoreField == null) {
                Object fieldValue = field.get(value);
                System.out.println(" " + field.getName() + " = " + fieldValue);
            } else if (!ignoreField.skip()) {
                System.out.println(" " + field.getName() + " = ?");
            }
        }

        System.out.println("}");
    }
}