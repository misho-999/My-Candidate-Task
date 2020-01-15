package com.candidate.task.data.repositories;

import com.candidate.task.data.models.Address;
import com.candidate.task.data.models.Mail;
import com.candidate.task.data.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    Address findByPersonPin(String pin);
}
