package com.candidate.task.web.controllers;

import com.candidate.task.service.models.PersonServiceModel;
import com.candidate.task.service.services.PeopleService;
import com.candidate.task.validation.InsertPersonValidator;
import com.candidate.task.validation.annotation.EditPersonValidator;
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
    private final InsertPersonValidator insertPersonValidator;
    private final EditPersonValidator editPersonValidator;

    @Autowired
    public HomeController(PeopleService peopleService, ModelMapper modelMapper, InsertPersonValidator insertPersonValidator, EditPersonValidator editPersonValidator) {
        this.peopleService = peopleService;
        this.modelMapper = modelMapper;
        this.insertPersonValidator = insertPersonValidator;
        this.editPersonValidator = editPersonValidator;
    }

    @GetMapping("/")
    public ModelAndView index() {
        return super.view("index.html");
    }

    @PostMapping("/find-person")
    public ModelAndView findPerson(ModelAndView modelAndView, @ModelAttribute(name = "model") FindPersonModel model) {

        List<PersonViewModel> people = this.peopleService.findPeopleByFullName(model.getFullName()).stream()
                .map(p -> this.modelMapper.map(p, PersonViewModel.class))
                .collect(Collectors.toList());

        modelAndView.addObject("people", people);

        return super.view("found-people.html", modelAndView);
    }

    @GetMapping("/details-person")
    public ModelAndView detailsPerson() {
        return super.view("details-person");
    }

    @GetMapping("/insert-person")
    public ModelAndView insertPerson(ModelAndView modelAndView, @ModelAttribute(name = "model") InsertPersonModel model) {
        modelAndView.addObject("model", model);

        return super.view("insert-person");
    }

    @PostMapping("/insert-person")
    public ModelAndView insertConfirm(@Valid ModelAndView modelAndView, @ModelAttribute(name = "model") InsertPersonModel model
            , BindingResult bindingResult) {

        this.insertPersonValidator.validate(model, bindingResult);

        if (bindingResult.hasErrors()) {
            modelAndView.addObject("model", model);

            return super.view("insert-person.html", modelAndView);
        }

        PersonServiceModel personServiceModel = this.modelMapper.map(model, PersonServiceModel.class);

        this.peopleService.insertPeople(personServiceModel);
        modelAndView.addObject("successMessage", personServiceModel.getFullName());

        return super.view("index.html", modelAndView);
    }

    @GetMapping("/edit-person/{pin}")
    public ModelAndView editPerson(@PathVariable String pin, ModelAndView modelAndView, @ModelAttribute(name = "model") EditPersonModel model) {

        //Principal principal, ModelAndView modelAndView, @ModelAttribute(name = "model") UserEditBindingModel model
        PersonServiceModel personServiceModel = this.peopleService.findPersonByPin(pin);
        model = this.modelMapper.map(personServiceModel, EditPersonModel.class);

        modelAndView.addObject("model", model);

        return super.view("edit-person.html", modelAndView);
    }

    @PostMapping("/edit-person/{pin}")
    public ModelAndView editDestinationConfirm(@PathVariable String pin, ModelAndView modelAndView, @ModelAttribute(name = "model") EditPersonModel model
            , BindingResult bindingResult) {

        this.editPersonValidator.validate(model, bindingResult);

        if (bindingResult.hasErrors()) {
            modelAndView.addObject("model", model);
            modelAndView.addObject("lastPin", pin);
            return super.view("edit-person.html", modelAndView);
        }

        this.peopleService.editPerson(pin, this.modelMapper.map(model, PersonServiceModel.class));

        //TODO VALIDATION!!!!
        //TODO Поазване на вцички параметри на All People 6бр.!!!!
        //TODO При натискане на сърч с празен вход мята грешка.!!!!
        //TODO При повторно дитване с корекция на .!!!!


        return super.redirect("/");
    }
}
