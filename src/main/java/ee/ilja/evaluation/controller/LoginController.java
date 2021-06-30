package ee.ilja.evaluation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    @GetMapping("/login-error")
    public String login(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);
        String errorMessage = null;
        if (session != null) {
            Exception e = (Exception) session.getAttribute("SPRING_SECURITY_LAST_EXCEPTION");
            if (e != null) {
                errorMessage = e.getMessage();
            }
        }
        model.addAttribute("errorMessage", errorMessage);
        return "login";
    }
}
