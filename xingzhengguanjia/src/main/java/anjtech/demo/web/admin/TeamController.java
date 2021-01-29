package anjtech.demo.web.admin;

import anjtech.demo.dao.TeamRepository;
import anjtech.demo.po.Team;
import anjtech.demo.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class TeamController {
    @Autowired
    private TeamService teamService;

    @GetMapping("/team")
    public String team(@PageableDefault(size = 5, sort = {"id"}) Pageable pageable, Model model) {
        model.addAttribute("page", teamService.listAll(pageable));
        return "/admin/team";
    }

    @GetMapping("/team/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes attributes) {
        teamService.deleteTeam(id);
        attributes.addFlashAttribute("message", "删除成功。");
        return "redirect:/admin/team";
    }


    @GetMapping("/team/input")
    public String input(Model model) {
        model.addAttribute("team", new Team());
        return "/admin/team_input";
    }

    @PostMapping("/team")
    public String create(Team team, RedirectAttributes redirectAttributes, BindingResult result) {
        Team exit_t = teamService.findByName(team.getName());
        if (exit_t != null) {
            result.rejectValue("team", "teamError", "群组已存在。");

        }
        if (result.hasErrors()) {
            return "admin/team_input";
        }

        Team t = teamService.saveTeam(team);
        if (t == null) {
            redirectAttributes.addFlashAttribute("fail", "创建失败");
        } else {
            redirectAttributes.addFlashAttribute("message","创建成功");
        }
        return "redirect:/admin/team";
    }



}
