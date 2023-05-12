package dev.riko.golftourplanner.users;

/**
 * A class representing a person.
 */
public class Person {
    /**
     * Firstname of the person
     */
    private final String firstname;
    /**
     * Lastname of the person
     */
    private final String lastname;
    /**
     * Age of the person
     */
    private final int age;

    /**
     * Constructs a new Person object with the given first name, last name, and age.
     *
     * @param firstname the first name of the person
     * @param lastname  the last name of the person
     * @param age       the age of the person
     */
    public Person(String firstname, String lastname, int age) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.age = age;
    }

    /**
     * Returns the first name of the person.
     *
     * @return the first name of the person
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * Returns the last name of the person.
     *
     * @return the last name of the person
     */
    public String getLastname() {
        return lastname;
    }

    /**
     * Returns the age of the person.
     *
     * @return the age of the person
     */
    public int getAge() {
        return age;
    }
}
