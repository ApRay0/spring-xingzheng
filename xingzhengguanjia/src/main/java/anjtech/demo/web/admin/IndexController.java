package anjtech.demo.web.admin;

import anjtech.demo.service.RoleService;
import anjtech.demo.service.TeamService;
import anjtech.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/admin")
public class IndexController {
    @Autowired
    private UserService userService;


    @GetMapping("/")
    public String index(@PageableDefault(size = 5, sort = {"id"}) Pageable pageable, Model model) {
        model.addAttribute("page", userService.listAll(pageable));

        return "admin/index";
    }



}
