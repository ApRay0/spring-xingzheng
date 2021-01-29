package anjtech.demo.service;


import anjtech.demo.po.Task;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TaskService {

    Page<Task> listTask(Long id, Pageable pageable);

    List<Task> listTask();

    Page<Task> listTask(Pageable pageable);

    Task saveTask(Task task);


    Task getTaskByName(String name);

    Task updateTask(Long id, Task task);




    Task getTask(Long id);

    void deleteTask(Long id);
}
