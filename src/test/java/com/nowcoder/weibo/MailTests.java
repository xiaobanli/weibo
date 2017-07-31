package com.nowcoder.weibo;


import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;


public class MailTests {
	public static void main (String args[]) throws Exception {

		String host = "smtp.163.com";   //发件人使用发邮件的电子信箱服务器
		String from = "last_romeo@163.com";    //发邮件的出发地（发件人的信箱）
		String to = "last_romeo@163.com";   //发邮件的目的地（收件人信箱）

		// Get system properties
		Properties props = System.getProperties();

		// Setup mail server
		props.put("mail.smtp.host", host);

		// Get session
		props.put("mail.smtp.auth", "true"); //这样才能通过验证

		MyAuthenticator myauth = new MyAuthenticator("last_romeo@163.com", "shuangyu2141998");
		Session session = Session.getDefaultInstance(props, myauth);

//    session.setDebug(true);

		// Define message
		MimeMessage message = new MimeMessage(session);


		// Set the from address
		message.setFrom(new InternetAddress(from));

		// Set the to address
		message.addRecipient(Message.RecipientType.TO,
				new InternetAddress(to));

		// Set the subject
		message.setSubject("测试程序！");

		// Set the content
		message.setText("这是用java写的发送电子邮件的测试程序！");

		message.saveChanges();

		Transport.send(message);

	}
}

