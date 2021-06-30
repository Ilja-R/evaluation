package ee.ilja.evaluation.controller;

import ee.ilja.evaluation.model.User;
import ee.ilja.evaluation.model.UserData;
import ee.ilja.evaluation.security.UserDetailsImpl;
import ee.ilja.evaluation.service.UserDataService;
import ee.ilja.evaluation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ProfileController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserDataService userDataService;

    @Autowired
    public ProfileController(UserService userService, UserDataService userDataService) {
        this.userService = userService;
        this.userDataService = userDataService;
    }

    @GetMapping("/profile")
    public String displayProfileInfo(@AuthenticationPrincipal UserDetailsImpl userDetails, Model model) {
        String userEmail = userDetails.getUsername();
        User user = userService.getUserByEmail(userEmail);
        model.addAttribute("user", user);
        UserData userData = userDataService.getUserDataByUserId(user.getId());
        model.addAttribute("userData", userData);
        return "profile/profile";
    }

    @GetMapping("/profile/edit")
    public String getEditWindow(@AuthenticationPrincipal UserDetailsImpl userDetails, Model model) {

        String userEmail = userDetails.getUsername();
        User user = userService.getUserByEmail(userEmail);

        UserData userData;
        userData = userDataService.getUserDataByUserId(user.getId());

        model.addAttribute("userData", userData);
        return "profile/edit_profile";
    }

    @GetMapping("/profile/deactivate")
    public String deactivateUser(@AuthenticationPrincipal UserDetailsImpl userDetails){
        String userEmail = userDetails.getUsername();
        User user = userService.getUserByEmail(userEmail);
        userService.deactivateUser(user.getId());
        return "redirect:/performLogout?";
    }

    @PostMapping("/submit_details")
    public String submit(@AuthenticationPrincipal UserDetailsImpl userDetails, UserData userData) {
        String userEmail = userDetails.getUsername();
        User user = userService.getUserByEmail(userEmail);
        user.setUserData(userData);
        userService.saveUser(user);
        return "redirect:/profile";
    }

}
