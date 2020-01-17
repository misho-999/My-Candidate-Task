package com.candidate.task.web.controllers;

import com.candidate.task.errors.EmailNotFoundException;
import com.candidate.task.errors.PersonNotFoundException;
import com.candidate.task.service.models.PersonServiceModel;
import com.candidate.task.service.services.PeopleService;
import com.candidate.task.validation.PersonValidator;
import com.candidate.task.web.models.EditPersonModel;
import com.candidate.task.web.models.FindPersonModel;
import com.candidate.task.web.models.InsertPersonModel;
import com.candidate.task.web.models.PersonViewModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HomeController extends BaseController {

    private final PeopleService peopleService;
    private final ModelMapper modelMapper;
    private final PersonValidator personValidator;

    @Autowired
    public HomeController(PeopleService peopleService, ModelMapper modelMapper, PersonValidator personValidator) {
        this.peopleService = peopleService;
        this.modelMapper = modelMapper;
        this.personValidator = personValidator;
    }

    @GetMapping("/")
    public ModelAndView index() {
        return super.view("index.html");
    }

    @PostMapping("/find")
    public ModelAndView findPerson(ModelAndView modelAndView, @ModelAttribute(name = "model") FindPersonModel model) {

        List<PersonViewModel> people = this.peopleService.findPeopleByFullName(model.getFullName()).stream()
                .map(p -> this.modelMapper.map(p, PersonViewModel.class))
                .collect(Collectors.toList());

        modelAndView.addObject("people", people);

        return super.view("all-found-people.html", modelAndView);
    }

    @GetMapping("/add")
    public ModelAndView insertPerson(ModelAndView modelAndView, @ModelAttribute(name = "model") InsertPersonModel model) {
        modelAndView.addObject("model", model);

        return super.view("add-person");
    }

    @PostMapping("/add")
    public ModelAndView insertConfirm(@Valid ModelAndView modelAndView, @ModelAttribute(name = "model") InsertPersonModel model
            , BindingResult bindingResult) {

        this.personValidator.validate(model, bindingResult);

        if (bindingResult.hasErrors()) {
            modelAndView.addObject("model", model);

            return super.view("add-person.html", modelAndView);
        }

        PersonServiceModel personServiceModel = this.peopleService.insertPeople(this.modelMapper.map(model, PersonServiceModel.class));

        modelAndView.addObject("successInsertMessage", personServiceModel.getFullName());

        return super.view("index.html", modelAndView);
    }


    @GetMapping("/edit/{id}")
    public ModelAndView editPerson(@PathVariable Long id, ModelAndView modelAndView, @ModelAttribute(name = "model") EditPersonModel model) {
        PersonServiceModel personServiceModel = this.peopleService.findPersonById(id);
        model = this.modelMapper.map(personServiceModel, EditPersonModel.class);

        modelAndView.addObject("model", model);

        return super.view("edit-person.html", modelAndView);
    }

    @PostMapping("/edit/{id}")
    public ModelAndView editDestinationConfirm(@PathVariable Long id, ModelAndView modelAndView, @ModelAttribute(name = "model") EditPersonModel model
            , BindingResult bindingResult) {

        this.personValidator.validate(model, bindingResult);

        if (bindingResult.hasErrors()) {
            modelAndView.addObject("model", model);

            return super.view("edit-person.html", modelAndView);
        }

        PersonServiceModel personServiceModel = this.peopleService.editPerson(id, this.modelMapper.map(model, PersonServiceModel.class));

        modelAndView.addObject("successEditMessage", personServiceModel.getFullName());

        return super.view("index.html", modelAndView);
    }

    @GetMapping("/delete/{id}")
    public ModelAndView deletePerson(@PathVariable Long id, ModelAndView modelAndView) {
        PersonServiceModel personServiceModel = this.peopleService.findPersonById(id);
        PersonViewModel personViewModel = this.modelMapper.map(personServiceModel, PersonViewModel.class);

        modelAndView.addObject("model", personViewModel);
        modelAndView.addObject("id", id);

        return super.view("delete-person.html", modelAndView);
    }

    @PostMapping("/delete/{id}/{fullName}")
    public ModelAndView deletePersonConfirm(@PathVariable Long id, @PathVariable String fullName) {
        this.peopleService.deletePerson(id);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("successDeleteMessage", fullName);

        return super.view("index.html", modelAndView);
    }



    @ExceptionHandler({PersonNotFoundException.class})
    public ModelAndView handlePersonNotFoundException(PersonNotFoundException e) {
        ModelAndView modelAndView = new ModelAndView("error.html");
        modelAndView.addObject("message", e.getMessage());
        modelAndView.addObject("statusCode", e.getStatusCode());

        return modelAndView;
    }

    @ExceptionHandler({EmailNotFoundException.class})
    public ModelAndView handleEmailNotFoundException(EmailNotFoundException e) {
        ModelAndView modelAndView = new ModelAndView("error.html");
        modelAndView.addObject("message", e.getMessage());
        modelAndView.addObject("statusCode", e.getStatusCode());

        return modelAndView;
    }
}


//TODO Изваждане на повтарящата се логика от валидаторите в базов клас!!!!
//TODO VALIDATION!!!!
//TODO Поазване на вцички параметри на All People 6бр.!!!!
//TODO При натискане на сърч с празен вход мята грешка.!!!!
//TODO При повторно дитване с корекция на .!!!!
//TODO Да се изтрие MailNotFoundException ако не се ползва!!!!