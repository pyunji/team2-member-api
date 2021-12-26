package com.mycompany.webapp.service.job;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.mycompany.webapp.service.MailService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@EnableScheduling
public class JoinCountEmailJob {
	@Autowired
	StringRedisTemplate redisTemplate;
	
	@Autowired
	MailService mailService;
	
	/* 매일 회원 가입 누적 횟수 관리자 이메일로 전송*/
	//@Scheduled(cron="0 0 0 * * *")		//매일 자정
	@Scheduled(cron = "*/30 * * * * *") // 10초마다 (테스트용)
	public void sendJoinCountAdmin() throws MessagingException {
		ValueOperations<String,String> vops = redisTemplate.opsForValue();
		
		String totalNum = vops.get("total_cnt");
		String yesterdayJoin = vops.get("yesterday_join");
		String yesterdayTotalJoin = vops.get("yesterday_total_join");
		
		int total_num = 0;
		int yesterday_join = 0;
		int yesterday_total_join = 0;
		
		if(totalNum!=null)				total_num = Integer.parseInt(totalNum);
		if(yesterdayJoin!=null)			yesterday_join = Integer.parseInt(yesterdayJoin);
		if(yesterdayTotalJoin!=null)	yesterday_total_join = Integer.parseInt(yesterdayTotalJoin);
		
		vops.set("yesterday_join", Integer.toString(total_num - yesterday_total_join));
		vops.set("yesterday_total_join", totalNum);
		
		mailService.sendTotalNumMail(total_num,yesterday_join,yesterday_total_join);
	}
}
