package anjtech.demo.web.admin;

import anjtech.demo.po.Role;
import anjtech.demo.po.Team;
import anjtech.demo.po.User;
import anjtech.demo.service.RoleService;
import anjtech.demo.service.TeamService;
import anjtech.demo.service.UserService;
import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private TeamService teamService;

    @Autowired
    private RoleService roleService;

    @GetMapping("/user/order_email")
    public String user_order_email(@PageableDefault(size = 5, sort = {"email"}) Pageable pageable, Model model) {
        model.addAttribute("page", userService.listAll(pageable));
        return "admin/user";
    }

    @GetMapping("/user/order_name")
    public String user_order_name(@PageableDefault(size = 5, sort = {"name"}) Pageable pageable, Model model) {
        model.addAttribute("page", userService.listAll(pageable));
        return "admin/user";
    }

    @GetMapping("/user")
    public String user(@PageableDefault(size = 5, sort = {"id"}) Pageable pageable, Model model) {
        model.addAttribute("page", userService.listAll(pageable));
        return "admin/user";
    }


    @GetMapping("/user/input")
    public String input(Model model) {
        model.addAttribute("user", new User());
        return "admin/user_input";
    }

    @GetMapping("/user/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes attributes) {
        userService.deleteUser(id);
        attributes.addFlashAttribute("message", "删除成功。");
        return "redirect:/admin/user";
    }

    @GetMapping("/user/add_role_team/{id}")
    public String addTeamOrRole(@PathVariable Long id, Model model) {

        model.addAttribute("user", userService.findById(id));
        model.addAttribute("roles", roleService.listAll());
        model.addAttribute("teams", teamService.listAll());
        return "admin/add_role_team";
    }



    @PostMapping("/user")
    private String create(User user, RedirectAttributes attributes, BindingResult result) {
        User exit_u = userService.findByEmail(user.getEmail());
        if (exit_u != null) {
            result.rejectValue("email", "emailError", "用户已存在。");
        }
        if (result.hasErrors()) {
            return "admin/user_input";
        }
        User u = userService.saveUser(user);
        if (u == null) {
            attributes.addFlashAttribute("fail", "创建失败。");
        } else {
            attributes.addFlashAttribute("message", "创建成功。");
        }

        return "redirect:/admin/user";

    }


    @PostMapping("/search")
    public String search(@PageableDefault(size = 5, sort = {"id"}) Pageable pageable, @RequestParam String query, Model model) {
        model.addAttribute("page", userService.listByQuery("%" + query + "%", pageable));
        return "admin/search";
    }

    @PostMapping("/user/add_role_team/{id}")
    public String postTeamOrRole(@PathVariable Long id, HttpServletRequest request,
                                 RedirectAttributes attributes, HttpSession session, User user) {
        String[] teams = request.getParameterValues("teamIds");
        String[] roles = request.getParameterValues("roleIds");
        List<Team> team_list = new ArrayList<>();
        for (int i = 0; i < teams.length; i++) {
            team_list.add(teamService.findByName(teams[i]));
        }
        List<Role> role_list = new ArrayList<>();
        for (int i = 0; i < roles.length; i++) {
            role_list.add(roleService.findByName(roles[i]));
        }
        user = userService.findById(id);
        user.setRoles(role_list);
        user.setTeams(team_list);

        User u = userService.saveUser(user);

        if (u == null) {
            attributes.addFlashAttribute("fail", "更改失败。");
        } else {
            attributes.addFlashAttribute("message", "更改成功。");
        }

        return "redirect:/admin/user";
    }



}
