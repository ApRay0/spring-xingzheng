package anjtech.demo.web;


import anjtech.demo.dao.MailRepository;
import anjtech.demo.dao.TaskRepository;
import anjtech.demo.po.Mail;
import anjtech.demo.po.User;
import anjtech.demo.service.MailService;
import anjtech.demo.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class MailController {

    @Autowired
    private MailService mailService;

    @Autowired
    private TaskService taskService;

    @GetMapping("/mail/input/{id}")
    public String input(Model model, @PathVariable Long id) {
        Mail m = new Mail();
        m.setId(id);
        model.addAttribute("mail", m);
        return "mail_input";
    }

    @GetMapping("/history")
    public String history(@PageableDefault(size = 5, sort = {"id"}) Pageable pageable ,
                          Model model, HttpSession session) {
        model.addAttribute("page", mailService.listAll(pageable));
        return "mail";
    }

    @GetMapping("/mail/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes attributes) {
        attributes.addFlashAttribute("message", "删除成功。");
        return "redirect:/history";
    }

    @PostMapping("/mail/input/{id}")
    public String create(@RequestParam String send, Mail mail,@PathVariable Long id,
                         RedirectAttributes attributes, HttpSession session) throws ParseException {
        User user = (User) session.getAttribute("user");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date send_date = sdf.parse(send);



        mail.setSend_date(send_date);
        mail.setSend_from("tony19971118@126.com");
        mail.setSend_to(user.getEmail());
        mail.setHead(taskService.getTask(id).getName());
        mail.setContent(taskService.getTask(id).getContent());
        Mail m = mailService.saveMail(mail);

        if (m == null) {
            attributes.addFlashAttribute("fail", "创建失败");
        } else {
            attributes.addFlashAttribute("message", "创建成功");
        }

        return "redirect:/task";
    }

}
