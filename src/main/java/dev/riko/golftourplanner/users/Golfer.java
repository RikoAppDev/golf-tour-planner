package dev.riko.golftourplanner.users;

/**
 * A class representing a Golfer, which is a type of Participant with a first name, last name, age, handicap, and golf club.
 */
public class Golfer extends Participant {
    /**
     * The first name of the golfer.
     */
    private final String firstname;
    /**
     * The last name of the golfer.
     */
    private final String lastname;
    /**
     * The age of the golfer.
     */
    private final int age;
    /**
     * The handicap of the golfer.
     */
    private double hcp;
    /**
     * The golf club of the golfer.
     */
    private String club;

    /**
     * Constructs a Golfer object with the specified parameters.
     *
     * @param firstname the first name of the golfer
     * @param lastname  the last name of the golfer
     * @param age       the age of the golfer
     * @param hcp       the handicap of the golfer
     * @param club      the golf club of the golfer
     */
    public Golfer(String firstname, String lastname, int age, double hcp, String club) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.age = age;
        this.hcp = hcp;
        this.club = club;
    }

    /**
     * Returns the handicap of the golfer.
     *
     * @return the handicap of the golfer
     */
    public double getHcp() {
        return hcp;
    }

    /**
     * Sets the handicap of the golfer to the specified value.
     *
     * @param hcp the new handicap of the golfer
     */
    public void setHcp(double hcp) {
        this.hcp = hcp;
    }

    /**
     * Returns the golf club of the golfer.
     *
     * @return the golf club of the golfer
     */
    public String getClub() {
        return club;
    }

    /**
     * Sets the golf club of the golfer to the specified value.
     *
     * @param club the new golf club of the golfer
     */
    public void setClub(String club) {
        this.club = club;
    }

    /**
     * Returns the first name of the golfer.
     *
     * @return the first name of the golfer
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * Returns the last name of the golfer.
     *
     * @return the last name of the golfer
     */
    public String getLastname() {
        return lastname;
    }

    /**
     * Returns the age of the golfer.
     *
     * @return the age of the golfer
     */
    public int getAge() {
        return age;
    }
}
