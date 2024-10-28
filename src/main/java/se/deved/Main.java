package se.deved;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Objects;
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

            File ironmanFile = new File("ironman.txt");
            ironmanFile.createNewFile();
            saveAnyObjectToFile(ironman, ironmanFile);

            Person ironmanFromFile = readAnyObjectFromFile(Person.class, ironmanFile);
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
        Class clazz = value.getClass();
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

    public static <T> void saveAnyObjectToFile(T object, File file) throws Exception {
        FileWriter writer = new FileWriter(file);

        Class clazz = object.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            Object value = field.get(object);
            writer.append(value.toString()).append("\n");
        }

        writer.close();
    }

    public static <T> T readAnyObjectFromFile(Class<T> clazz, File file) throws Exception {
        // Filen kanske inte finns
        // Filen kan vara tom
        // Filen innehåller ett objekt av fel typ

        FileReader reader = new FileReader(file);
        BufferedReader buffered = new BufferedReader(reader);

        Object object = clazz.getConstructor().newInstance();

        for (Field field : clazz.getDeclaredFields()) {
            String fileValue = buffered.readLine();
            field.setAccessible(true);

            if (field.getType() == String.class) {
                field.set(object, fileValue);
            } else if (field.getType() == Integer.class) {
                int num = Integer.parseInt(fileValue);
                field.set(object, num);
            } else if (field.getType() == Double.class) {
                double num = Double.parseDouble(fileValue);
                field.set(object, num);
            } else if (field.getType() == Boolean.class) {
                boolean bl = Objects.equals(fileValue, "true");
                field.set(object, bl);
            }
        }

    }
}