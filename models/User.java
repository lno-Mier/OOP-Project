package models;

public abstract class User{
    private String id;
    private String firstName;
    private String lastName;
    private String login;
    private String password;

    public User(String id, String firstName, String lastName, String login, String password){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.password = password;
    }

    //Геттеры
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

    //Для пароля отдельно потому что геттер на пароль это неправильно
    public boolean checkPassword(String input){
        return this.password.equals(input);
    }


    //Сеттеры
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public abstract void displayInfo();
}