package com.nowcoder.weibo;

import org.junit.Test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.mail.internet.MimeMessage;
import java.io.InputStream;


/**
 * Created by lenovo on 2017/7/29.
 */



public class mailTest {


    @Autowired
    private JavaMailSender mailSender;

    /**
     * 修改application.properties的用户，才能发送。
     */

    public void sendSimpleEmail() {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom("1326263565@qq.com");//发送者.
        message.setTo("last_romeo@163.com");//接收者.
        message.setSubject("测试邮件（邮件主题）");//邮件主题.
        message.setText("这是邮件内容");//邮件内容.

        mailSender.send(message);//发送邮件
    }
}

