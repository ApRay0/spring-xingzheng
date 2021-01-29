package anjtech.demo.web.admin;

import anjtech.demo.po.Role;
import anjtech.demo.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @GetMapping("/role")
    public String role(@PageableDefault(size = 5, sort = {"id"}) Pageable pageable, Model model) {
        model.addAttribute("page", roleService.listRole(pageable));
        return "admin/role";
    }

    @GetMapping("/role/input")
    public String input(Model model) {
        model.addAttribute("role", new Role());
        return "admin/role_input";
    }

    @PostMapping("/role")
    public String create(Role role, RedirectAttributes attributes, BindingResult result) {
        Role role_t = roleService.findByName(role.getName());
        if (role_t != null) {
            result.rejectValue("role", "roleError", "权限已存在。");
        }
        if (result.hasErrors()) {
            return "admin/role_input";
        }

        Role r = roleService.saveRole(role);

        if (r == null) {
            attributes.addFlashAttribute("fail", "创建失败");
        } else {
            attributes.addFlashAttribute("message","创建成功");
        }
        return "redirect:/admin/role";

    }
}
