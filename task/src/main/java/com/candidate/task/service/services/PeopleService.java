package com.candidate.task.service.services;

import com.candidate.task.service.models.PersonServiceModel;

import java.util.List;

public interface PeopleService {

    List<PersonServiceModel> findPeopleByFullName(String username);

    PersonServiceModel findPersonByPin(String pin);

    PersonServiceModel insertPeople(PersonServiceModel personServiceModel);

    PersonServiceModel editPerson(String pin, PersonServiceModel personServiceModel);

}
