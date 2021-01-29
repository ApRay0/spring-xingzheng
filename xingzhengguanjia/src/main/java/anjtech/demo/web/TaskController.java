package anjtech.demo.web;

import anjtech.demo.po.Task;
import anjtech.demo.po.User;
import anjtech.demo.service.TaskService;
import com.sun.source.tree.CatchTree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


@Controller

public class TaskController {
    @Autowired
    private TaskService taskService;



    @GetMapping("/task")
    public String task(@PageableDefault(size = 5, sort = {"id"}) Pageable pageable, Model model, HttpSession session) {
        Object o = session.getAttribute("user");
        User user = (User) o;

        model.addAttribute("page", taskService.listTask(user.getId() ,pageable));
        return "task";
    }

    @GetMapping("/task/input")
    public String input(Model model){
        model.addAttribute("task", new Task());
        return "task_input";
    }

    @GetMapping("/task/input/{id}")
    public String editInput(@PathVariable Long id, Model model){
        model.addAttribute("task", taskService.getTask(id));
        return "task_input";
    }

    @GetMapping("/task/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes attributes) {
        taskService.deleteTask(id);
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/task";
    }



    @PostMapping("/task")
    public String create (@RequestParam String end_time, Task task, BindingResult result,
                          RedirectAttributes attributes, HttpSession session) throws ParseException {
//        Task exited_task = taskService.getTaskByName(task.getName());
//        if (exited_task != null) {
//            result.rejectValue("name", "nameError", "任务已存在！");
//        }
//        if (result.hasErrors()) {
//            return "/task_input";
//        }
        Object o = session.getAttribute("user");
        User user = (User) o;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date end = sdf.parse(end_time);
        task.setEnd_time(end);
        task.setU_id(user.getId());
        task.setUser(user);
        Task t = taskService.saveTask(task);
        if (t == null) {
            attributes.addFlashAttribute("fail", "新建失败");
        } else {
            attributes.addFlashAttribute("message", "新建成功");
        }
        return "redirect:/task";
    }

    @PostMapping("/task/{id}")
    public String edit(@RequestParam String end_time, Task task, BindingResult result, @PathVariable Long id, RedirectAttributes attributes) throws ParseException {
//        Task exited_task = taskService.getTaskByName(task.getName());
//        if (exited_task == null) {
//            result.rejectValue("name", "NotFoundError", "任务不存在！");
//        }
//        if (result.hasErrors()) {
//            return "/task_input";
//        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date end = sdf.parse(end_time);
        task.setEnd_time(end);
        Task t = taskService.updateTask(id, task);
        if (t == null) {
            attributes.addFlashAttribute("fail", "编辑失败");
        } else {
            attributes.addFlashAttribute("message", "编辑成功");
        }
        return "redirect:/task";
    }

    @RequestMapping("/task/upload/{id}")
    public String upload(@RequestParam("file") MultipartFile file, RedirectAttributes attributes,@PathVariable Long id,HttpSession session) throws IOException {
        Object o = session.getAttribute("user");
        User user = (User) o;

        if (!file.isEmpty()) {
            String path = "src/main/resources/static/up_load/" +
                    user.getEmail() + "/";
            File dir = new File(path);
            if (!dir.exists()) {
                dir.mkdir();
            }
            path = path + id.toString() + "/";
            dir = new File(path);
            if (!dir.exists()) {
                dir.mkdir();
            }
            BufferedOutputStream out = new BufferedOutputStream(
                    new FileOutputStream(new File(
                             path + file.getOriginalFilename())));
            out.write(file.getBytes());
            out.flush();
            out.close();
            attributes.addFlashAttribute("message", "上传成功！");
        } else {
            attributes.addFlashAttribute("fail", "上传失败！");
        }
        return "redirect:/task";
    }

    @GetMapping("/task/download/{id}")
    public String downloadFile(HttpServletRequest request,RedirectAttributes attributes,  HttpSession session,HttpServletResponse response, @PathVariable Long id) {
        Object o = session.getAttribute("user");
        User user = (User) o;
        String path = "src/main/resources/static/up_load/"
                + user.getEmail() + "/" + id.toString() + "/";
        File dir = new File(path);
        if (!dir.exists()) {
            attributes.addFlashAttribute("fail", "文件不存在！");
            return "redirect:/task";
        }
        File[] files = dir.listFiles();

        for (File file : files) {
            //设置文件路径
            if (file.exists()) {
                response.setContentType("application/force-download");  // 设置强制下载不打开
                response.addHeader("Content-Disposition", "attachment;fileName=" + file.getName());// 设置文件名
                byte[] buffer = new byte[1024];
                FileInputStream fis = null;
                BufferedInputStream bis = null;
                try {
                    fis = new FileInputStream(file);
                    bis = new BufferedInputStream(fis);
                    OutputStream os = response.getOutputStream();
                    int i = bis.read(buffer);
                    while (i != -1) {
                        os.write(buffer, 0, i);
                        i = bis.read(buffer);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (bis != null) {
                        try {
                            bis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return "redirect:/task";
    }

}
