package anjtech.demo.util;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.Date;

public class Schedule {
    private final long PERIOD =  24 * 60 * 60 * 1000;



    public void TimeManager() {


        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.HOUR_OF_DAY, 12);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        Date date = calendar.getTime();

        Timer timer = new Timer();


        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                EmailTask emailTask = new EmailTask();
                emailTask.GetMailTask();
            }
        };

        TimerTask task2 = new TimerTask() {
            @Override
            public void run() {
                DateTimeTask dateTimeTask = new DateTimeTask();
                dateTimeTask.DateTask();
            }
        };

//        timer.schedule(task, date, PERIOD);
        timer.schedule(task, 0);
        timer.schedule(task2, 0);
    }



//    public static void main(String args[]) {
//        Schedule schedule = new Schedule();
//        schedule.TimeManager();
//    }
}
