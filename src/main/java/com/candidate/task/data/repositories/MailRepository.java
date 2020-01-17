package com.candidate.task.data.repositories;

import com.candidate.task.data.models.Mail;
import com.candidate.task.data.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MailRepository extends JpaRepository<Mail, Long> {
    Optional<Mail> findByEmail(String email);

    Mail findByPersonId(Long personId);
}
