package com.mycompany.webapp.dto.mail;

import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MailTotalCnt {
	private int today_total;
	private int yesterday;
	private int yesterday_total;
	
	public MailTotalCnt(int today_total, int yesterday,int yesterday_total) {
		this.today_total = today_total;
		this.yesterday = yesterday;
		this.yesterday_total = yesterday_total;
	}
	
	public String getToday() {
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(date);
	}
	
	public String getMailText1() {
		String mailText1 = "현재 날짜까지 누적 회원 가입 횟수 : " + today_total;
		return mailText1;
	}
	
	public String getMailText2() {
		String mailText2 = "일일 회원가입 횟수 : " + (today_total - yesterday_total);
		return mailText2;
	}
	
	public String getMailText3() {
		String mailText3 = "";
		try {
			int percentage = (((today_total - yesterday_total) - yesterday)/yesterday)*100;
			mailText3 =  "전날 대비 회원가입 증가 비율 : " + percentage+"% 증가";
		} catch (Exception e) {
			// TODO: handle exception
		}
		return mailText3;
	}
	
	public String mailContent() {
		return getMailText1() + getMailText2() + getMailText3() ;
	}
}
