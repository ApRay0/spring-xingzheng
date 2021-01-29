package anjtech.demo.web;

import anjtech.demo.po.Role;
import anjtech.demo.po.User;
import anjtech.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class SigninController {

    @Autowired
    private UserService userService;



    @GetMapping("/signin")
    public String signinPage() {
        return "signin";
    }




    @GetMapping("/signout")
    public String signout(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:/";
    }

    @PostMapping("/signin")
    private String signin(@RequestParam String email,
                          @RequestParam String password,
                          HttpSession session,
                          RedirectAttributes attributes){
        User user = userService.checkUser(email, password);

        if (user != null) {
            user.setPassword(null);
            session.setAttribute("user", user);
            session.setAttribute("admin", null);
            List<Role> roles = userService.getRoles(email, password);
            for (int i = 0; i <roles.size(); i++) {
                if (roles.get(i).getName().equals("admin")) {
                    session.setAttribute("admin", roles.get(i).getName());
                    return "redirect:/admin/";
                }
            }
            return "redirect:/task";
        } else {
            attributes.addFlashAttribute("fail","用户名或密码错误。");

            return "redirect:/signin";

        }

    }
}
