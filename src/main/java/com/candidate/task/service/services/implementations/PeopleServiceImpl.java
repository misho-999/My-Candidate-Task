package com.candidate.task.service.services.implementations;

import com.candidate.task.constants.ExceptionConstants;
import com.candidate.task.constants.ValidationConstrains;
import com.candidate.task.data.models.Address;
import com.candidate.task.data.models.Mail;
import com.candidate.task.data.models.Person;
import com.candidate.task.data.repositories.AddressRepository;
import com.candidate.task.data.repositories.MailRepository;
import com.candidate.task.data.repositories.PeopleRepository;
import com.candidate.task.data.repositories.SeedRepository;
import com.candidate.task.errors.MissingPersonException;
import com.candidate.task.errors.PersonNotFoundException;
import com.candidate.task.service.models.PersonServiceModel;
import com.candidate.task.service.services.PeopleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PeopleServiceImpl implements PeopleService {


    private final PeopleRepository peopleRepository;
    private final MailRepository mailRepository;
    private final AddressRepository addressRepository;
    private final ModelMapper modelMapper;
    private final SeedRepository seedRepository;


    @Autowired
    public PeopleServiceImpl(PeopleRepository peopleRepository, MailRepository mailRepository, AddressRepository addressRepository, ModelMapper modelMapper, SeedRepository seedRepository) {
        this.peopleRepository = peopleRepository;
        this.mailRepository = mailRepository;
        this.addressRepository = addressRepository;
        this.modelMapper = modelMapper;
        this.seedRepository = seedRepository;
    }

    @Override
    public List<PersonServiceModel> findPeopleByFullName(String fullName) {
        List<Person> people = this.peopleRepository.findAllByFullName(fullName);

        if (people.size() == 0) {
            throw new PersonNotFoundException(ExceptionConstants.PERSON_NOT_FOUND);
        }
        return people.stream()
                .map(p -> this.modelMapper.map(p, PersonServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public PersonServiceModel findPersonById(Long id) {
        Person person = this.peopleRepository.findById(id)
                .orElseThrow(() -> new PersonNotFoundException(ExceptionConstants.PERSON_NOT_FOUND));

        PersonServiceModel personServiceModel = this.modelMapper.map(person, PersonServiceModel.class);

        Mail mail = this.mailRepository.findByPersonId(id);
        Address address = this.addressRepository.findByPersonId(id);

        if (mail != null) {
            personServiceModel.setEmailType(mail.getEmailType());
            personServiceModel.setEmail(mail.getEmail());
        }

        if (address != null) {
            personServiceModel.setAddressType(address.getAddressType());
            personServiceModel.setAddressInfo(address.getAddressInfo());
        }

        return personServiceModel;
    }

    @Override
    public PersonServiceModel addPeople(PersonServiceModel personServiceModel) {
        Person person = new Person();
        person.setFullName(personServiceModel.getFullName());
        person.setPin(personServiceModel.getPin());

        Mail mail = new Mail();
        mail.setEmailType(personServiceModel.getEmailType());
        mail.setEmail(personServiceModel.getEmail());

        Address address = new Address();
        address.setAddressType(personServiceModel.getAddressType());
        address.setAddressInfo(personServiceModel.getAddressInfo());

        person.setMail(mail);
        person.setAddress(address);

        address.setPerson(person);
        mail.setPerson(person);

        this.peopleRepository.save(person);
        this.addressRepository.save(address);
        this.mailRepository.save(mail);

        return personServiceModel;
    }

    @Override
    public PersonServiceModel editPerson(Long id, PersonServiceModel personServiceModel) {
        Person person = this.peopleRepository.findById(id)
                .orElseThrow(() -> new PersonNotFoundException(ExceptionConstants.PERSON_NOT_FOUND));

        Mail mail = this.mailRepository.findByPersonId(id);
        Address address = this.addressRepository.findByPersonId(id);

        person.setFullName(personServiceModel.getFullName());
        person.setPin(personServiceModel.getPin());

        if (mail == null) {
            this.seedRepository.createMail(id, personServiceModel.getEmailType(), personServiceModel.getEmail());
        } else {
            mail.setEmailType(personServiceModel.getEmailType());
            mail.setEmail(personServiceModel.getEmail());
        }

        if (address == null) {
            this.seedRepository.createAddress(id, personServiceModel.getAddressType(), personServiceModel.getAddressInfo());

        } else {
            address.setAddressType(personServiceModel.getAddressType());
            address.setAddressInfo(personServiceModel.getAddressInfo());
        }


        this.mailRepository.save(mail);
        this.addressRepository.save(address);
        this.peopleRepository.save(person);

        return personServiceModel;
    }

    @Override
    public void deletePerson(Long id) {
        Person person = this.peopleRepository.findById(id)
                .orElseThrow(() -> new PersonNotFoundException(ExceptionConstants.PERSON_NOT_FOUND));

        this.peopleRepository.delete(person);
    }

    @Override
    public boolean seedPeopleEmailsAndAddresses() {
        boolean isSeeded = false;
        try {
            if (this.peopleRepository.count() < ValidationConstrains.SEED_MAX_COUNT) {
                this.seedRepository.seedPeople();
                isSeeded = true;
            }

            if (this.mailRepository.count() < ValidationConstrains.SEED_MAX_COUNT) {
                this.seedRepository.seedEmails();
                isSeeded = true;
            }

            if (this.addressRepository.count() < ValidationConstrains.SEED_MAX_COUNT) {
                this.seedRepository.seedAddresses();
                isSeeded = true;
            }
        } catch (RuntimeException e) {
            throw new MissingPersonException(ExceptionConstants.MISSING_PERSON_ID);
        }

        return isSeeded;
    }
}
