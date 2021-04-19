package hse.vpi19.banking.model;

import java.util.ArrayList;
import java.util.List;

public class User {
    private int id;
    private List<Account> accounts = new ArrayList<>();
    private String firstName;
    private String lastName;

    public User() {
    }

    public User(int id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void addAccount(Account account) {
        accounts.add(account);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", accounts=" + accounts +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
