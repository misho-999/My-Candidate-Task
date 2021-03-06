package com.candidate.task.data.models;

import com.candidate.task.constants.ValidationConstrains;

import javax.persistence.*;

@Entity
@Table(name = "T_MAILS")
public class Mail extends BaseEntity {
    private String emailType;
    private String email;
    private Person person;

    public Mail() {
    }

    @Column(name = "EMAIL_TYPE", length = ValidationConstrains.EMAIL_TYPE_MAX_LENGTH, nullable = false)
    public String getEmailType() {
        return emailType;
    }

    public void setEmailType(String emailType) {
        this.emailType = emailType;
    }


    @Column(name = "EMAIL", length = ValidationConstrains.EMAIL_MAX_LENGTH)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "T_PEOPLE_ID", referencedColumnName = "ID")
    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
