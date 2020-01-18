package com.candidate.task.service.services;

import com.candidate.task.errors.MissingPersonException;
import com.candidate.task.service.models.PersonServiceModel;

import java.util.List;

public interface PeopleService {

    List<PersonServiceModel> findPeopleByFullName(String username);

    PersonServiceModel findPersonById(Long email);

    PersonServiceModel addPeople(PersonServiceModel personServiceModel);

    PersonServiceModel editPerson(Long pin, PersonServiceModel personServiceModel);

    void deletePerson(Long id);

    boolean seedPeopleEmailsAndAddresses();

}
