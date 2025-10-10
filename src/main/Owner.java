package main;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;

public class Owner {

    private String firstName;
    private String lastName;
    private String eMail;
    private String telephone;

    public Owner(String firstName, String lastName, String eMail, String telephone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.eMail = eMail;
        this.telephone = telephone;
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

    public String getMail() {
        return eMail;
    }

    public void setMail(String eMail) {
        this.eMail = eMail;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Owner)) return false;
        Owner owner = (Owner) o;
        return telephone.equalsIgnoreCase(owner.telephone)
                && firstName.equalsIgnoreCase(owner.firstName)
                && lastName.equalsIgnoreCase(owner.lastName)
                && eMail.equalsIgnoreCase(owner.eMail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, eMail, telephone);
    }
}
