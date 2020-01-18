package com.candidate.task.data.models;

import com.candidate.task.constants.ValidationConstrains;

import javax.persistence.*;

@Entity
@Table(name = "T_PEOPLE")
public class Person extends BaseEntity {
    private String fullName;
    private String pin;
    private Mail mail;
    private Address address;

    public Person() {
    }

    @Column(name = "FULL_NAME", length = ValidationConstrains.FULL_NAME_MAX_LENGTH, nullable = false)
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Column(name = "PIN", length = ValidationConstrains.PIN_LENGTH)
    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    @OneToOne(mappedBy = "person", cascade = CascadeType.ALL)
    public Mail getMail() {
        return mail;
    }

    public void setMail(Mail mail) {
        this.mail = mail;
    }

    @OneToOne(mappedBy = "person", cascade = CascadeType.ALL)
    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
