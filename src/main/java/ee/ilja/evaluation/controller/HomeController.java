package ee.ilja.evaluation.controller;

import ee.ilja.evaluation.model.User;
import ee.ilja.evaluation.service.UserDataService;
import ee.ilja.evaluation.validator.UserValidator;
import ee.ilja.evaluation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class HomeController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    UserDataService userDataService;

    @GetMapping("")
    public String viewHomePage() {
        return "index";
    }

    @GetMapping("/register")
    public String showSignUpForm(Model model) {
        model.addAttribute("user", new User());
        return "registration/signup_form";
    }

    @GetMapping("/login")
    public String showLoginForm(){
        return "login";
    }

    @PostMapping("/process_register")
    public String processRegistration(@ModelAttribute("user") User user, BindingResult bindingResult) {

        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()){
            return "registration/signup_form";
        }

        userService.saveNewUser(user, "User");
        return "registration/register_success";
    }

    @GetMapping("/add_admin")
    public String loadAdminForm(Model model){
        model.addAttribute("user", new User());
        return "registration/admin_form";
    }

    @PostMapping("/process_admin")
    public String addAdmin(@ModelAttribute("user") User user, BindingResult bindingResult){

        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()){
            return "registration/signup_form";
        }

        userService.saveNewUser(user, "Admin");

        return "index";
    }
}
