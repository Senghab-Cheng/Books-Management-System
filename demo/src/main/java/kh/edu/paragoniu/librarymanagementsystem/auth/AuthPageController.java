package kh.edu.paragoniu.librarymanagementsystem.auth;

import kh.edu.paragoniu.librarymanagementsystem.common.BusinessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthPageController {

    private final AuthService authService;

    public AuthPageController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/")
    public String roleSelectionPage() {
        return "index";
    }

    @GetMapping("/login/admin")
    public String adminLoginPage() {
        return "auth/admin-login";
    }

    @GetMapping("/login/user")
    public String userLoginPage() {
        return "auth/user-login";
    }

    @GetMapping("/signup/user")
    public String userSignupPage() {
        return "auth/user-signup";
    }

    @PostMapping("/signup/user")
    public String signupUser(@RequestParam String username,
                             @RequestParam String password,
                             RedirectAttributes redirectAttributes) {
        try {
            authService.signupUser(new UserSignupRequest(username, password));
            redirectAttributes.addFlashAttribute("message", "Signup successful. Please login.");
            return "redirect:/login/user";
        } catch (BusinessException ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
            return "redirect:/signup/user";
        }
    }
}
