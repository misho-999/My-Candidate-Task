package com.candidate.task.service.services.implementations;

import com.candidate.task.data.models.Address;
import com.candidate.task.data.models.Mail;
import com.candidate.task.data.models.Person;
import com.candidate.task.data.repositories.AddressRepository;
import com.candidate.task.data.repositories.MailRepository;
import com.candidate.task.data.repositories.PeopleRepository;
import com.candidate.task.errors.EmailNotFoundException;
import com.candidate.task.errors.PersonNotFoundException;
import com.candidate.task.service.models.PersonServiceModel;
import com.candidate.task.service.services.PeopleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PeopleServiceImpl implements PeopleService {

    private final PeopleRepository peopleRepository;
    private final MailRepository mailRepository;
    private final AddressRepository addressRepository;
    private final ModelMapper modelMapper;


    @Autowired
    public PeopleServiceImpl(PeopleRepository peopleRepository, MailRepository mailRepository, AddressRepository addressRepository, ModelMapper modelMapper) {
        this.peopleRepository = peopleRepository;
        this.mailRepository = mailRepository;
        this.addressRepository = addressRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<PersonServiceModel> findPeopleByFullName(String fullName) {
        List<Person> people = this.peopleRepository.findAllByFullName(fullName);

        if (people.size() == 0) {
            throw new PersonNotFoundException("Person Not Found");
        }
        return people.stream()
                .map(p -> this.modelMapper.map(p, PersonServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public PersonServiceModel findPersonById(Long id) {
        Person person = this.peopleRepository.findById(id)
                .orElseThrow(() -> new PersonNotFoundException("Person not found"));

        PersonServiceModel personServiceModel = this.modelMapper.map(person, PersonServiceModel.class);

        Mail mail = this.mailRepository.findByPersonId(id);
        Address address = this.addressRepository.findByPersonId(id);

        personServiceModel.setEmailType(mail.getEmailType());
        personServiceModel.setEmail(mail.getEmail());
        personServiceModel.setAddressType(address.getAddressType());
        personServiceModel.setAddressInfo(address.getAddressInfo());

        return personServiceModel;
    }

    @Override
    public PersonServiceModel insertPeople(PersonServiceModel personServiceModel) {
        Person person = this.modelMapper.map(personServiceModel, Person.class);
        Mail mail = this.modelMapper.map(personServiceModel, Mail.class);
        Address address = this.modelMapper.map(personServiceModel, Address.class);

        address.setPerson(person);
        mail.setPerson(person);

        person.getMails().add(mail);
        person.getAddresses().add(address);

        this.peopleRepository.save(person);

        this.addressRepository.save(address);

        this.mailRepository.save(mail);

        return personServiceModel;
    }

    @Override
    public PersonServiceModel editPerson(Long id, PersonServiceModel personServiceModel) {
        Person person = this.peopleRepository.findById(id)
                .orElseThrow(() -> new PersonNotFoundException("Person not found"));

        Mail mail = this.mailRepository.findByPersonId(id);
        Address address = this.addressRepository.findByPersonId(id);

        person.setFullName(personServiceModel.getFullName());
        person.setPin(personServiceModel.getPin());

        mail.setEmailType(personServiceModel.getEmailType());
        mail.setEmail(personServiceModel.getEmail());

        address.setAddressType(personServiceModel.getAddressType());
        address.setAddressInfo(personServiceModel.getAddressInfo());

        this.mailRepository.save(mail);
        this.addressRepository.save(address);
        this.peopleRepository.save(person);

        return personServiceModel;
    }

    @Override
    public void deletePerson(Long id) {
        Person person = this.peopleRepository.findById(id)
                .orElseThrow(() -> new PersonNotFoundException("Person not found"));

        this.peopleRepository.delete(person);
    }
}
