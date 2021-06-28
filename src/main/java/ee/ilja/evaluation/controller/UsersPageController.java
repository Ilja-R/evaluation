package ee.ilja.evaluation.controller;

import ee.ilja.evaluation.model.Role;
import ee.ilja.evaluation.model.User;
import ee.ilja.evaluation.model.UserData;
import ee.ilja.evaluation.service.UserDataService;
import ee.ilja.evaluation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class UsersPageController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserDataService userDataService;

    @GetMapping("/users")
    public String viewUsersList(Model model) {
        List<User> users = userService.listAll();
        model.addAttribute("listUsers", users);
        return "user_management/users";
    }

    @GetMapping("/users/edit/{id}")
    public String editUser(@PathVariable("id") Long id, Model model) {
        User user = userService.getUserById(id);
        List<Role> roles = userService.getRoles();
        UserData userData = userDataService.getUserDataByUserId(user.getId());
//        user.setUserData(userData);
        model.addAttribute("user", user);
        model.addAttribute("userData", userData);
        model.addAttribute("listRoles", roles);
        return "user_management/user_form";
    }

    @PostMapping("/users/save")
    public String saveUser(User user, UserData userData){
        /**IR: a workaround for problems with Thymeleaf template
         * TODO: Ask someone for an advice
         * */
        if (user.getIsActive() == null) {
            user.setIsActive(false);
        }
        Long userDataId = userDataService.getUserDataByUserId(user.getId()).getId();
        userData.setId(userDataId);
        user.setUserData(userData);
        userService.updateUser(user);
        return "redirect:/users";
    }
}
