package com.mycompany.webapp.dto.mail;

import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MailData {
	private String ip;
	
	public MailData(String ip) {
		this.ip = ip;
	}
	
	public String getToday() {
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(date);
	}
	
	public String getMailText1(String ip) {
		log.info("ip = " + ip);
		String mailText1 = "IP주소 " + ip + "에서 회원가입을 총 10번이상 시도하셨습니다.";
		return mailText1;
	}
	
	public String mailContent() {
		log.info("ip" + ip);
		return getMailText1(ip);
	}
}