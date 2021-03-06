package com.candidate.task.data.models;

import com.candidate.task.constants.ValidationConstrains;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "T_ADDRESSES")
public class Address extends BaseEntity {
    private String addressType;
    private String addressInfo;
    private Person person;

    public Address() {
    }

    @Column(name = "ADDR_TYPE", length = ValidationConstrains.ADDRESS_TYPE_MAX_LENGTH, nullable = false)
    public String getAddressType() {
        return addressType;
    }

    public void setAddressType(String addressType) {
        this.addressType = addressType;
    }

    @Column(name = "ADDR_INFO", length = ValidationConstrains.ADDRESS_INFO_MAX_LENGTH)
    public String getAddressInfo() {
        return addressInfo;
    }

    public void setAddressInfo(String addressInfo) {
        this.addressInfo = addressInfo;
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
