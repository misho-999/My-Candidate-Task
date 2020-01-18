package com.candidate.task.data.repositories;

import com.candidate.task.data.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PeopleRepository extends JpaRepository<Person, Long> {

    List<Person> findAllByFullName(String fullName);

    Optional<Person> findById(Long id);
}
