package models;

import java.io.Serializable;
import java.util.Objects;

import interfaces.Printable;

/**
 * Abstract base class for all users in the university system.
 * <p>
 * Defines common fields shared by every user — identifier, full name,
 * and login credentials — along with authentication and printing
 * behavior. Concrete user types (Student, Employee and its subclasses)
 * extend this class.
 * <p>
 * Implements {@link Serializable} so that user data can be persisted
 * by the Database, and {@link Printable} so that any user can print
 * its own information.
 */
public abstract class User implements Serializable, Printable {
    private static final long serialVersionUID = 1L;

    /** Unique identifier of the user (e.g. "S001", "T002"). */
    private String id;

    /** First name of the user. */
    private String firstName;

    /** Last name of the user. */
    private String lastName;

    /** Login string used for authentication. */
    private String login;

    /** Password used for authentication. */
    private String password;

    /**
     * Creates a new user with the given identity and credentials.
     *
     * @param id        unique identifier of the user
     * @param firstName first name of the user
     * @param lastName  last name of the user
     * @param login     login used for authentication
     * @param password  password used for authentication
     */
    public User(String id, String firstName, String lastName, String login, String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.password = password;
    }

    /** @return the user's unique identifier */
    public String getId() {
        return id;
    }

    /** @return the user's first name */
    public String getFirstName() {
        return firstName;
    }

    /** @return the user's last name */
    public String getLastName() {
        return lastName;
    }

    /** @return the user's login */
    public String getLogin() {
        return login;
    }

    /**
     * Updates the user's first name.
     *
     * @param firstName new first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Updates the user's last name.
     *
     * @param lastName new last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Sets a new password for the user. Used by Admin during
     * password reset operations.
     *
     * @param password new password value
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Compares the given input with the user's stored password.
     *
     * @param input password candidate entered during login
     * @return {@code true} if the input matches the stored password,
     *         {@code false} otherwise
     */
    public boolean checkPassword(String input) {
        return this.password.equals(input);
    }

    /**
     * Two users are considered equal if they share the same id
     * and login.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(login, user.login);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login);
    }

    @Override
    public String toString() {
        return String.format("User[ID='%s', Name='%s %s']", id, firstName, lastName);
    }

    /**
     * Prints a one-line summary of the user (id and full name) to
     * standard output. Subclasses extend this to add role-specific
     * information.
     */
    @Override
    public void printInfo() {
        System.out.println("ID: " + id + " | Name: " + firstName + " " + lastName);
    }
}
