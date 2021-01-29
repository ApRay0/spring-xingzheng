package anjtech.demo.service;

import anjtech.demo.NotFoundException;
import anjtech.demo.dao.TaskRepository;
import anjtech.demo.po.Task;
import anjtech.demo.po.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService{
    @Autowired
    private TaskRepository taskRepository;

    @Override
    public Page<Task> listTask(Long id, Pageable pageable) {
        return taskRepository.findAll(new Specification<Task>() {
            @Override
            public Predicate toPredicate(Root<Task> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.<User>get("user").get("id"), id);
            }
        }, pageable);
    }

    @Transactional
    @Override
    public List<Task> listTask() {
        return taskRepository.findAll();
    }

    @Transactional
    @Override
    public Page<Task> listTask(Pageable pageable) {
        return taskRepository.findAll(pageable);
    }

    @Transactional
    @Override
    public Task getTask(Long id) {
        return taskRepository.getOne(id);
    }

    @Override
    public Task getTaskByName(String name) {
        return taskRepository.findByName(name);
    }

    @Transactional
    @Override
    public Task saveTask(Task task)
    {
        if (task.getId() == null) {
            task.setCreate_time(new Date());
        }

        if (task.getFinished() == null) {
            task.setFinished(0);
        }



        return taskRepository.save(task);
    }




    @Transactional
    @Override
    public Task updateTask(Long id, Task task) {
        Task t = taskRepository.getOne(id);
        if (t == null) {
            throw new NotFoundException("not found");
        }
        Date tmp = t.getCreate_time();
        BeanUtils.copyProperties(task, t);
        t.setCreate_time(tmp);
        t.setEnd_time(task.getEnd_time());
        return taskRepository.save(t);

    }

    @Transactional
    @Override
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }
}
