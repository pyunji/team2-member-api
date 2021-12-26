package com.mycompany.webapp.service;

import java.util.Date;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.mycompany.webapp.dto.mail.MailData;
import com.mycompany.webapp.dto.mail.MailTotalCnt;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("mailService")
public class MailService {
	@Autowired
	JavaMailSender javaMailSender;
	
	public void sendDupUserMail(String ip) throws MessagingException {
		//관리자 이메일
		String email = "a2670624@naver.com";

		MimeMessage message = javaMailSender.createMimeMessage();
		MailData mailData = new MailData(ip);
		String subject = "[Admin] 사이트에 의심스러운 접근 요청이 존재합니다.";
		message.setSubject(subject);
		message.setRecipient(Message.RecipientType.TO, new InternetAddress(email));
		//message.setText(mailData.mailContent(), "UTF-8", "html");	
		message.setText(mailData.mailContent());	
		message.setSentDate(new Date());
		
		javaMailSender.send(message);
	}
	
	public void sendTotalNumMail(int today_total,int yesterday,int yesterday_total) throws MessagingException {
		String email = "a2670624@naver.com";
		
		MimeMessage message = javaMailSender.createMimeMessage();
		MailTotalCnt mailData = new MailTotalCnt(today_total,yesterday,yesterday_total);
		String date = mailData.getToday();
		String subject = "[Admin] "+date+" 기준 회원가입 횟수 분석";
		message.setSubject(subject);
		message.setRecipient(Message.RecipientType.TO, new InternetAddress(email));
		//message.setText(mailData.mailContent(), "UTF-8", "html");	
		message.setText(mailData.mailContent());	
		message.setSentDate(new Date());
		
		javaMailSender.send(message);
	}
}
