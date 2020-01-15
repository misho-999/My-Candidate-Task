package com.candidate.task.data.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "T_PEOPLE")
public class Person extends BaseEntity {
    private String fullName;
    private String pin;
    private List<Mail> mails;
    private List<Address> addresses;

    public Person() {
        this.mails = new ArrayList<>();
        this.addresses = new ArrayList<>();
    }

    @Column(name = "FULL_NAME", length = 90, nullable = false)
    @Pattern(regexp = "[a-zA-Zа-яА-Я -]*", message = "Name is not valid")
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Column(name = "PIN", length = 10, unique = true)
    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    @OneToMany(mappedBy = "person")
    public List<Mail> getMails() {
        return mails;
    }

    public void setMails(List<Mail> mails) {
        this.mails = mails;
    }

    @OneToMany(mappedBy = "person")
    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

}
