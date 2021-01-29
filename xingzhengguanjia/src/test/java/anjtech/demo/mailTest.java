package anjtech.demo;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.mail.SimpleMailMessage;

import org.springframework.mail.javamail.JavaMailSender;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;



@RestController

@RequestMapping("/mail")

public class mailTest {



    private final Logger logger = LoggerFactory.getLogger(this.getClass());



    @Autowired

    private JavaMailSender mailSender;



    @RequestMapping("/send")

    public void sendMail(){

        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom("tony19971118@126.com");

        message.setTo("409242440@qq.com");

        message.setSubject("it is a test for spring boot");

        message.setText("你好，我是小黄，我正在测试发送邮件。");



        try {

            mailSender.send(message);

            logger.info("小黄的测试邮件已发送。");

        } catch (Exception e) {

            logger.error("小黄发送邮件时发生异常了！", e);

        }

    }

}