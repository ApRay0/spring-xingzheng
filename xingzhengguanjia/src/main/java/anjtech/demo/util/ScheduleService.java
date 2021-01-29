package anjtech.demo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.Date;

@Component
@EnableScheduling
public class ScheduleService {

    @Autowired
    EmailTask emailTask;

    @Autowired
    DateTimeTask dateTimeTask;

    @Scheduled(fixedRate = 1000 * 24 * 60 * 60)
    public void Task() {
        emailTask.GetMailTask();
        dateTimeTask.DateTask();

    }

}
