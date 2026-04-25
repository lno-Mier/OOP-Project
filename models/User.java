package models;

import java.io.Serializable;
import java.util.Objects;

import interfaces.Printable;

public abstract class User implements Serializable, Printable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String firstName;
    private String lastName;
    private String login;
    private String password; // на данный момент просто строка

    public User(String id, String firstName, String lastName, String login, String password){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.password = password;
    }

    // Геттеры
    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getLogin() {
        return login;
    }

    // Сеттеры
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    // Пользователи могут менять собственные пароли, а админ и другие
    public void setPassword(String password) {
        this.password = password;
    }

    public boolean checkPassword(String input) {
        return this.password.equals(input);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

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

    @Override
    public void printInfo() {
        System.out.println("ID: " + id + " | Name: " + firstName + " " + lastName);
    }
}