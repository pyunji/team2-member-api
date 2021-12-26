package com.mycompany.webapp.service;

import java.util.Date;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.mycompany.webapp.dto.MailData;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("mailService")
public class MailService {
	@Autowired
	JavaMailSender javaMailSender;
	
	public void sendTextMail(String ip) throws MessagingException {
		String memail = "a2670624@naver.com";

		MimeMessage message = javaMailSender.createMimeMessage();
		MailData mailData = new MailData(ip);
		String subject = "[Admin]사이트에 의심스러운 접근 요청이 존재합니다.";
		message.setSubject(subject);
		message.setRecipient(Message.RecipientType.TO, new InternetAddress(memail));
		message.setText(mailData.mailContent());
		message.setSentDate(new Date());
		javaMailSender.send(message);
	}
}
